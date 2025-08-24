package com.wmc.guiaremision.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity that represents a configuration parameter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "createdAt", "modifiedAt" })
@Entity
@Table(name = "parametro")
public class ParameterEntity extends AuditableEntity {

    /**
     * Unique ID of the parameter
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parametroId")
    private Integer parameterId;

    /**
     * ID of the company to which the parameter belongs
     */
    @Column(name = "empresaId", nullable = false)
    private Integer companyId;

    /**
     * CDR (Reception Certificate) path
     */
    @Column(name = "rutaArchivoCdr")
    private String cdrFilePath;

    /**
     * Frame path
     */
    @Column(name = "rutaArchivoXmlSinFirmar")
    private String unsignedXmlFilePath;

    /**
     * Signed frame path
     */
    @Column(name = "rutaArchivoXmlFirmado")
    private String signedXmlFilePath;

    /**
     * PDF document path
     */
    @Column(name = "rutaArchivoPdf")
    private String pdfFilePath;

    /**
     * Logo path
     */
    @Column(name = "rutaLogo")
    private String logoPath;

    /**
     * Logo file
     */
    @Column(name = "nombreLogo")
    private String logoName;

    /**
     * Certificate file
     */
    @Column(name = "nombreCertificado")
    private String certificateName;

    @Column(name = "contrasenaCertificado")
    private String certificatePassword;

    @Column(name = "rutaArchivoCertificado")
    private String certificateFilePath;

    /**
     * Indicates if it's in online mode
     */
    @Column(name = "online")
    private Boolean online;

    /**
     * Company to which the parameter belongs
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaId", insertable = false, updatable = false)
    private CompanyEntity company;
}
