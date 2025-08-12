package com.wmc.guiaremision.infrastructure.file;

import com.wmc.guiaremision.domain.spi.file.SignaturePort;
import com.wmc.guiaremision.domain.spi.file.dto.SignXmlRequest;
import com.wmc.guiaremision.infrastructure.common.Util;
import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

/**
 * Implementation of SignaturePort for XML digital signature operations.
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Service
public class SignaturePortImpl implements SignaturePort {

  @Override
  public String signXml(SignXmlRequest signRequest) {
    try {
      // Load certificate and private key
      CertificateData certificateData = loadCertificate(signRequest);

      // Parse XML document
      Document document = parseXmlDocument(signRequest.getUnsignedXmlContent());

      // Prepare extension node for signature
      Node extensionNode = prepareExtensionNode(document, signRequest.getSingleExtensionNode());

      // Create and apply digital signature
      applyDigitalSignature(document, extensionNode, certificateData);

      // Serialize signed XML to base64
      return serializeToBase64(document);

    } catch (Exception ex) {
      throw new RuntimeException("Error signing XML document", ex);
    }
  }

  /**
   * Loads digital certificate and extracts private key and certificate.
   */
  private CertificateData loadCertificate(SignXmlRequest signRequest) throws Exception {
    byte[] certBytes = Base64.getDecoder().decode(signRequest.getDigitalCertificate());
    char[] password = signRequest.getCertificatePassword().toCharArray();

    KeyStore keyStore = KeyStore.getInstance("PKCS12");
    keyStore.load(new ByteArrayInputStream(certBytes), password);

    String alias = keyStore.aliases().nextElement();
    PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password);
    X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

    return new CertificateData(privateKey, certificate);
  }

  /**
   * Parses XML content from base64 string to Document.
   */
  private Document parseXmlDocument(String base64Xml) throws Exception {
    byte[] xmlBytes = Base64.getDecoder().decode(base64Xml);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder builder = factory.newDocumentBuilder();

    return builder.parse(new ByteArrayInputStream(xmlBytes));
  }

  /**
   * Prepares extension node for signature placement.
   */
  private Node prepareExtensionNode(Document document, Boolean singleExtensionNode) {
    NodeList extensionNodes = document.getElementsByTagNameNS(UblNamespacesConstant.EXT, "ExtensionContent");
    int nodeIndex = Optional.ofNullable(singleExtensionNode).orElse(true) ? 0 : 1;

    Node extensionNode = extensionNodes.item(nodeIndex);
    if (extensionNode == null) {
      throw new IllegalStateException("ExtensionContent node not found in XML");
    }

    // Clear existing content
    clearNodeContent(extensionNode);

    return extensionNode;
  }

  /**
   * Clears all child nodes from the given node.
   */
  private void clearNodeContent(Node node) {
    while (node.hasChildNodes()) {
      node.removeChild(node.getFirstChild());
    }
  }

  /**
   * Creates and applies digital signature to the XML document.
   */
  private void applyDigitalSignature(Document document, Node extensionNode, CertificateData certificateData)
      throws Exception {
    XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");

    // Create reference for signature
    Reference reference = createSignatureReference(factory);

    // Create signed info
    SignedInfo signedInfo = createSignedInfo(factory, reference);

    // Create key info
    KeyInfo keyInfo = createKeyInfo(factory, certificateData.certificate);

    // Create and apply signature
    DOMSignContext signContext = new DOMSignContext(certificateData.privateKey, extensionNode);
    signContext.setDefaultNamespacePrefix("ds");

    XMLSignature signature = factory.newXMLSignature(signedInfo, keyInfo, null, "TeamFact", null);
    signature.sign(signContext);
  }

  /**
   * Creates signature reference with envelope transform.
   */
  private Reference createSignatureReference(XMLSignatureFactory factory) throws Exception {
    return factory.newReference(
        "",
        factory.newDigestMethod(DigestMethod.SHA1, null),
        Collections.singletonList(factory.newTransform(Transform.ENVELOPED, (C14NMethodParameterSpec) null)),
        null,
        null);
  }

  /**
   * Creates signed info with canonicalization and signature methods.
   */
  private SignedInfo createSignedInfo(XMLSignatureFactory factory, Reference reference) throws Exception {
    return factory.newSignedInfo(
        factory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
        factory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
        Collections.singletonList(reference));
  }

  /**
   * Creates key info with X509 certificate data.
   */
  private KeyInfo createKeyInfo(XMLSignatureFactory factory, X509Certificate certificate) throws Exception {
    KeyInfoFactory keyInfoFactory = factory.getKeyInfoFactory();
    X509Data x509Data = keyInfoFactory.newX509Data(Collections.singletonList(certificate));
    return keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
  }

  /**
   * Serializes XML document to base64 string.
   */
  private String serializeToBase64(Document document) throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.transform(new DOMSource(document), new StreamResult(outputStream));

    String xmlBase64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
    Util.saveXmlInDirectory(xmlBase64);

    return xmlBase64;
  }

  /**
   * Data class to hold certificate information.
   */
  private static class CertificateData {
    final PrivateKey privateKey;
    final X509Certificate certificate;

    CertificateData(PrivateKey privateKey, X509Certificate certificate) {
      this.privateKey = privateKey;
      this.certificate = certificate;
    }
  }
}
