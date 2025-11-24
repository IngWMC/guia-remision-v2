package com.wmc.guiaremision.application.service.impl;

import static com.wmc.guiaremision.shared.common.Constant.DASH;
import static com.wmc.guiaremision.shared.common.Constant.EMPTY;
import static com.wmc.guiaremision.shared.common.Constant.PDF_EXTENSION;
import static com.wmc.guiaremision.shared.common.Constant.XML_EXTENSION;
import static com.wmc.guiaremision.shared.common.Constant.ZIP_EXTENSION;

import com.wmc.guiaremision.application.dto.CdrDataResponse;
import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.dto.SignXmlDocumentRequest;
import com.wmc.guiaremision.application.service.CdrReadService;
import com.wmc.guiaremision.application.service.CompanyService;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.application.service.ParameterService;
import com.wmc.guiaremision.application.service.SignatureService;
import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.entity.DocumentEntity;
import com.wmc.guiaremision.domain.entity.ParameterEntity;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.enums.SunatStatusEnum;
import com.wmc.guiaremision.domain.repository.DocumentRepository;
import com.wmc.guiaremision.domain.spi.file.PdfGeneratorPort;
import com.wmc.guiaremision.domain.spi.file.XmlGeneratorPort;
import com.wmc.guiaremision.domain.spi.file.ZipFilePort;
import com.wmc.guiaremision.domain.spi.security.EncryptorSecurity;
import com.wmc.guiaremision.domain.spi.sunat.SunatGreApiPort;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FetchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.shared.common.Convert;
import com.wmc.guiaremision.infrastructure.adapter.file.StoragePortAdapter;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.shared.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService {

	private final EncryptorSecurity encryptorSecurity;
	private final DocumentRepository documentRepository;
	private final CompanyService companyService;
	private final ParameterService parameterService;
	private final SignatureService signatureService;
	private final CdrReadService cdrReadService;
	private final StoragePortAdapter storagePort;
	private final XmlGeneratorPort xmlGeneratorPort;
	private final SunatGreApiPort sunatGreApiPort;
	private final PdfGeneratorPort pdfGeneratorPort;
	private final ZipFilePort zipFilePort;

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public ServiceResponse generateDispatch(Dispatch dispatch) {
		log.info("Iniciando generación de guía de remisión para el documento: {}", dispatch.getDocumentCode());
		CompanyEntity company = this.companyService
				.findByIdentityDocumentNumber(dispatch.getSender().getIdentityDocumentNumber());

		// TODO: Setear datos de la empresa en la guía de remisión
		dispatch.getSender().setEmail(company.getEmail());
		dispatch.getSender().setPhone(company.getPhone());

		ParameterEntity parameter = this.parameterService.findByCompanyId(company.getCompanyId());

		// TODO: Generar el XML UBL de la guía de remisión
		String unsignedXmlContent = this.xmlGeneratorPort.generateDispatchXml(dispatch);

		// TODO: Guardar la GRE con el XML sin firmar
		DocumentEntity document = this.saveDispatch(dispatch, unsignedXmlContent, company.getCompanyId(),
				parameter.getUnsignedXmlFilePath());

		// TODO: Firmar el XML generado
		String signedXmlContent = this.signXmlDocument(dispatch, document, parameter, unsignedXmlContent);

		// TODO: Enviar el XML firmado a SUNAT
		TokenRequest tokenRequest = this.buildTokenRequest(company);
		SendDispatchRequest sendDispatchRequest = this.buildSendDispatchRequest(dispatch, signedXmlContent);
		FetchCdrResponse cdrResponse = this.sendDispatch(tokenRequest, sendDispatchRequest);

		// TODO: Guardar el CDR y actualizar el estado del documento
		this.processAndSaveCdr(dispatch, document, parameter, cdrResponse);

		// TODO: Generar y guardar PDF de la guía de remisión
		this.generateAndSavePdf(dispatch, document, parameter);

		// TODO: Generar los links de descarga de los archivos generados en la respuesta
		return ServiceResponse.builder().requestId(document.getRequestId()).build();
	}

	@Override
	public DocumentEntity saveDispatch(Dispatch document, String unsignedXml, Integer companyId,
			String unsignedXmlPath) {
		String unsignedXmlPhysicalFileName = this.buildPhysicalFileName(XML_EXTENSION);

		return Optional.of(document)
				.map(doc -> this.validateUnsignedXmlPath(unsignedXmlPath))
				.map(xmlPath -> this.storagePort.saveFile(xmlPath, unsignedXmlPhysicalFileName, unsignedXml))
				.map(isSave -> this.buildDocumentEntity(document, companyId, unsignedXmlPhysicalFileName))
				.map(this.documentRepository::save)
				.orElseThrow(() -> new BadRequestException("Error al guardar el dispatch"));
	}

	@Override
	public FetchCdrResponse sendDispatch(TokenRequest tokenRequest,
																			 SendDispatchRequest sendDispatchRequest) {
		FetchCdrResponse cdrResponse = sunatGreApiPort.sendGreAndFetchCdr(tokenRequest, sendDispatchRequest);
		return sunatGreApiPort.procesarCdr(cdrResponse,
				cdr -> {
					log.info("CDR recibido: {}", cdr);
					return cdr;
				},
				errorMessage -> {
					log.error("Error al procesar CDR: {}", errorMessage);
					throw new BadRequestException(errorMessage);
				});
	}

	private String signXmlDocument(Dispatch dispatch,
			DocumentEntity document,
			ParameterEntity parameter,
			String unsignedXmlContent) {
		SignXmlDocumentRequest signXmlDocumentRequest = SignXmlDocumentRequest.builder()
				.documentId(document.getDocumentId())
				.unsignedXmlContent(unsignedXmlContent)
				.signedXmlFilePath(parameter.getSignedXmlFilePath())
				.certificateName(parameter.getCertificateName())
				.certificatePassword(parameter.getCertificatePassword())
				.certificateFilePath(parameter.getCertificateFilePath())
				.identityDocumentNumber(dispatch.getSender().getIdentityDocumentNumber())
				.documentType(dispatch.getDocumentType().getCodigo())
				.documentCode(dispatch.getDocumentCode())
				.build();

		return this.signatureService.signXmlDocument(signXmlDocumentRequest);
	}

	private void processAndSaveCdr(Dispatch dispatch,
			DocumentEntity document,
			ParameterEntity parameter,
			FetchCdrResponse cdrResponse) {
		String cdrXmlContent = this.cdrReadService.getCdrXmlContent(cdrResponse.getArcCdr());
		CdrDataResponse cdrData = this.cdrReadService.getCdrData(cdrXmlContent);

		String cdrPhysicalFileName = this.buildPhysicalFileName(ZIP_EXTENSION);
		String cdrFileName = this.buildFileName(dispatch, ZIP_EXTENSION);

		this.storagePort.saveFile(parameter.getCdrFilePath(), cdrPhysicalFileName, cdrResponse.getArcCdr());
		this.documentRepository.updateCdrData(document.getDocumentId(), cdrFileName,
				cdrPhysicalFileName, cdrData.getTicketSunat(), SunatStatusEnum.ACEPTADO.getCode());
	}

	private void generateAndSavePdf(Dispatch dispatch,
			DocumentEntity document,
			ParameterEntity parameter) {
		String pdfFileContent = this.pdfGeneratorPort.generatePdf(dispatch,
				parameter.getLogoPath(), parameter.getLogoName());
		String pdfPhysicalFileName = this.buildPhysicalFileName(PDF_EXTENSION);
		String pdfFileName = this.buildFileName(dispatch, PDF_EXTENSION);

		this.storagePort.saveFile(parameter.getPdfFilePath(), pdfPhysicalFileName, pdfFileContent);
		this.documentRepository.updatePdfData(document.getDocumentId(), pdfFileName, pdfPhysicalFileName);
	}

	private TokenRequest buildTokenRequest(CompanyEntity company) {
		String solPassword = this.encryptorSecurity.decrypt(company.getSolPassword());

		return TokenRequest.builder()
				.clientId(company.getClientId())
				.clientSecret(company.getClientSecret())
				.username(company.getSolUser())
				.password(solPassword)
				.build();
	}

	private SendDispatchRequest buildSendDispatchRequest(Dispatch document, String signedXmlContent) {
		String fileName = this.buildFileName(document, EMPTY);
		String zippedXmlContent = this.zipFilePort.generateZip(signedXmlContent, fileName.concat(XML_EXTENSION));
		String hashZip = this.zipFilePort.calculateZipSha256Hash(zippedXmlContent);

		return SendDispatchRequest.builder()
				.numRucEmisor(document.getSender().getIdentityDocumentNumber())
				.codCpe(document.getDocumentType().getCodigo())
				.numSerie(document.getDocumentSeries())
				.numCpe(document.getDocumentNumber())
				.archivo(SendDispatchRequest.Archivo.builder()
						.nomArchivo(fileName.concat(ZIP_EXTENSION))
						.arcGreZip(zippedXmlContent)
						.hashZip(hashZip)
						.build())
				.build();
	}

	/**
	 * Valida y obtiene la ruta del XML sin firmar.
	 */
	private String validateUnsignedXmlPath(String unsignedXmlPath) {
		return Optional.ofNullable(unsignedXmlPath)
				.filter(path -> !path.isEmpty())
				.orElseThrow(() -> new BadRequestException("La ruta del XML sin firmar no puede estar vacía"));
	}

	/**
	 * Crea la entidad DocumentEntity con los datos del dispatch.
	 */
	private DocumentEntity buildDocumentEntity(Dispatch document, Integer companyId,
			String unsignedXmlPhysicalFileName) {
		String unsignedXmlFileName = document.getDocumentCode().concat(XML_EXTENSION);
		String json = Convert.convertObjectToJson(document);

		return DocumentEntity.builder()
				.companyId(companyId)
				.sunatStatus(SunatStatusEnum.PENDIENTE.getCode())
				.requestId(UUID.randomUUID().toString().replace(DASH, EMPTY))
				.documentType(document.getDocumentType().getCodigo())
				.documentCode(document.getDocumentCode())
				.issueDate(Util.getCurrentLocalDateTime())
				.unsignedXmlFileName(unsignedXmlFileName)
				.unsignedXmlPhysicalFileName(unsignedXmlPhysicalFileName)
				.json(json)
				.userCreate("wmamani")
				.build();
	}

	private String buildFileName(Dispatch document, String extension) {
		return document.getSender().getIdentityDocumentNumber()
				.concat(DASH).concat(document.getDocumentType().getCodigo())
				.concat(DASH).concat(document.getDocumentCode())
				.concat(extension);
	}

	private String buildPhysicalFileName(String extension) {
		return UUID.randomUUID().toString().replace(DASH, EMPTY).concat(extension);
	}
}