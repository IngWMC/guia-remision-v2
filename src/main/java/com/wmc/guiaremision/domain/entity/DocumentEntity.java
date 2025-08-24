package com.wmc.guiaremision.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Entity that represents an electronic document
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = { "createdAt", "modifiedAt" })
@Entity
@Table(name = "documento")
public class DocumentEntity extends AuditableEntity {

    /**
     * Unique ID of the document
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documentoId")
    private Integer documentId;

    /**
     * Company ID
     */
    @Column(name = "empresaId", nullable = false)
    private Integer companyId;

    /**
     * SUNAT status ID
     */
    @Column(name = "estadoSunatId")
    private Integer sunatStatusId;

    /**
     * Document code
     */
    @Column(name = "codigoDocumento")
    private String documentCode;

    /**
     * Issue date
     */
    @Column(name = "fechaEmision")
    private LocalDateTime issueDate;

    /**
     * Commercial document type ID
     */
    @Column(name = "tipoDocumento", nullable = false)
    private String DocumentType;

    /**
     * Document in XML format
     */
    @Column(name = "nombreArchivoXml")
    private String xmlFileName;

    /**
     * Document frame without signature
     */
    @Column(name = "nombreArchivoXmlSinFirma")
    private String unsignedXmlFileName;

    /**
     * Document frame without signature physical
     */
    @Column(name = "nombreArchivoFisicoXmlSinFirma")
    private String unsignedXmlPhysicalFileName;

    /**
     * Signed document frame
     */
    @Column(name = "nombreArchivoXmlFirmado")
    private String signedXmlFileName;

    /**
     * Signed document frame physical
     */
    @Column(name = "nombreArchivoFisicoXmlFirmado")
    private String signedXmlPhysicalFileName;

    /**
     * Real CDR document
     */
    @Column(name = "nombreArchivoCdr")
    private String cdrFileName;

    /**
     * Physical CDR document
     */
    @Column(name = "nombreArchivoFisicoCdr")
    private String cdrPhysicalFileName;

    /**
     * PDF document
     */
    @Column(name = "nombreArchivoPdf")
    private String pdfFileName;

    /**
     * Physical PDF document
     */
    @Column(name = "nombreArchivoFisicoPdf")
    private String pdfPhysicalFileName;

    /**
     * Document digest
     */
    @Column(name = "digest")
    private String digest;

    /**
     * Document signature
     */
    @Column(name = "signature")
    private String signature;

    /**
     * CDR ticket
     */
    @Column(name = "ticketSunat")
    private String ticketSunat;


    /**
     * Document in JSON format
     */
    @Column(name = "json", columnDefinition = "TEXT")
    private String json;

    /**
     * SUNAT status of the document
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadoSunatId", insertable = false, updatable = false)
    private SunatStatusEntity sunatStatus;

    /**
     * Company to which the document belongs
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaId", insertable = false, updatable = false)
    private CompanyEntity company;
}
