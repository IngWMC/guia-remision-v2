package com.wmc.guiaremision.infrastructure.filesystem;

import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.service.PdfGeneratorService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {
    private static final String BASE_DIR = "pdfs";

    @Override
    public Dispatch generatePdf(Dispatch dispatch) {
        String rucEmisor = dispatch.getSenderRuc();
        String documentId = dispatch.getDocumentId();
        String pdfFileName = documentId + ".pdf";
        String clientDir = BASE_DIR + File.separator + rucEmisor;
        File dir = new File(clientDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String pdfPath = clientDir + File.separator + pdfFileName;

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

            // Título y datos principales
            document.add(new Paragraph("Guía de Remisión Electrónica"));
            document.add(new Paragraph("N°: " + dispatch.getDocumentNumber()));
            document.add(new Paragraph("Fecha de emisión: " + dispatch.getIssueDate()));
            document.add(new Paragraph("Emisor (RUC): " + dispatch.getSenderRuc()));
            document.add(new Paragraph("Receptor (RUC): " + dispatch.getRecipientRuc()));
            document.add(new Paragraph("Transportista: " + dispatch.getCarrierName()));
            document.add(new Paragraph("Placa: " + dispatch.getVehiclePlateNumber()));
            document.add(new Paragraph("Peso bruto total: " + dispatch.getTotalGrossWeight()));
            document.add(new Paragraph("\n"));

            // Generar QR
            byte[] qrBytes = generateQrCode(getQrContent(dispatch), 200, 200);
            Image qrImage = Image.getInstance(qrBytes);
            qrImage.scaleToFit(120, 120);
            document.add(qrImage);

            document.close();

            dispatch.setPdfPath(pdfPath);
            return dispatch;
        } catch (IOException | DocumentException | WriterException e) {
            throw new RuntimeException("Error al generar el PDF de la guía de remisión", e);
        }
    }

    private String getQrContent(Dispatch dispatch) {
        // Contenido QR según SUNAT: RUC|TipoDoc|Serie|Correlativo|IGV|Total|Fecha|TipoDocReceptor|NumDocReceptor|Hash
        // Aquí un ejemplo simple, ajusta según tus reglas SUNAT
        return String.join("|",
                dispatch.getSenderRuc(),
                dispatch.getDocumentType(),
                dispatch.getDocumentNumber(),
                "0.00", // IGV (ajustar si tienes el dato)
                "0.00", // Total (ajustar si tienes el dato)
                dispatch.getIssueDate() != null ? dispatch.getIssueDate().toString() : "",
                "6", // TipoDocReceptor (6 = RUC)
                dispatch.getRecipientRuc(),
                "HASH" // Hash (puedes calcular el hash real si lo tienes)
        );
    }

    private byte[] generateQrCode(String content, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
} 