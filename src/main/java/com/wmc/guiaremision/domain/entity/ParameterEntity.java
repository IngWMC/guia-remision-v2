package com.wmc.guiaremision.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa los parámetros de configuración de una empresa.
 * <p>
 * Almacena rutas de archivos, certificados digitales y configuraciones
 * necesarias para la generación y firma de documentos electrónicos.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 * @see CompanyEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "createdAt", "modifiedAt" })
@Entity
@Table(name = "parametro")
public class ParameterEntity extends AuditableEntity {

    /**
     * Identificador único del parámetro.
     * <p>
     * Generado automáticamente mediante auto-incremento.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parametroId")
    private Integer parameterId;

    /**
     * Identificador de la empresa a la que pertenece este parámetro.
     * <p>
     * Establece la relación con {@link CompanyEntity}.
     * Campo obligatorio.
     * </p>
     */
    @Column(name = "empresaId", nullable = false)
    private Integer companyId;

    /**
     * Ruta del directorio donde se almacenan los archivos XML sin firmar.
     * <p>
     * Ejemplo: {@code "files/20123456789/xml/sin_firmar/"}
     * </p>
     */
    @Column(name = "rutaArchivoXmlSinFirmar")
    private String unsignedXmlFilePath;

    /**
     * Ruta del directorio donde se almacenan los archivos XML firmados
     * digitalmente.
     * <p>
     * Ejemplo: {@code "files/20123456789/xml/firmado/"}
     * </p>
     */
    @Column(name = "rutaArchivoXmlFirmado")
    private String signedXmlFilePath;

    /**
     * Ruta del directorio donde se almacenan los archivos PDF generados.
     * <p>
     * Contiene las representaciones imprimibles de los documentos electrónicos.
     * </p>
     * <p>
     * Ejemplo: {@code "files/20123456789/pdf/"}
     * </p>
     */
    @Column(name = "rutaArchivoPdf")
    private String pdfFilePath;

    /**
     * Ruta del directorio donde se almacenan los archivos CDR recibidos de SUNAT.
     * <p>
     * El CDR (Constancia de Recepción) es la respuesta de SUNAT tras el envío
     * exitoso de un documento electrónico.
     * </p>
     * <p>
     * Ejemplo: {@code "files/20123456789/cdr/"}
     * </p>
     */
    @Column(name = "rutaArchivoCdr")
    private String cdrFilePath;

    /**
     * Ruta del directorio donde se almacena el logo de la empresa.
     * <p>
     * El logo se usa en la generación de PDFs y representaciones visuales
     * de los documentos electrónicos.
     * </p>
     * <p>
     * Ejemplo: {@code "files/20123456789/logos/"}
     * </p>
     */
    @Column(name = "rutaLogo")
    private String logoPath;

    /**
     * Nombre del archivo del logo de la empresa.
     * <p>
     * Incluye la extensión del archivo.
     * </p>
     * <p>
     * Ejemplo: {@code "logo_empresa.png"}
     * </p>
     */
    @Column(name = "nombreLogo")
    private String logoName;

    /**
     * Nombre del archivo del certificado digital (.pfx o .p12).
     * <p>
     * El certificado se usa para firmar digitalmente los documentos
     * electrónicos antes de enviarlos a SUNAT.
     * </p>
     * <p>
     * Ejemplo: {@code "certificado_20123456789.pfx"}
     * </p>
     *
     * @see #certificatePassword
     * @see #certificateFilePath
     */
    @Column(name = "nombreCertificado")
    private String certificateName;

    /**
     * Contraseña del certificado digital.
     * <p>
     * <strong>Información sensible:</strong> Se recomienda cifrar este campo
     * antes de almacenarlo en la base de datos.
     * </p>
     *
     * @see #certificateName
     */
    @Column(name = "contrasenaCertificado")
    private String certificatePassword;

    /**
     * Ruta del directorio donde se almacena el certificado digital.
     * <p>
     * Ejemplo: {@code "files/20123456789/certificados/"}
     * </p>
     *
     * @see #certificateName
     */
    @Column(name = "rutaArchivoCertificado")
    private String certificateFilePath;

    /**
     * Empresa a la que pertenece este conjunto de parámetros.
     * <p>
     * Relación muchos a uno con {@link CompanyEntity}. Permite acceder
     * a toda la información de la empresa desde el parámetro.
     * </p>
     * <p>
     * Carga diferida ({@code LAZY}): Los datos de la empresa solo se cargan
     * cuando se accede explícitamente a esta propiedad.
     * </p>
     *
     * @see CompanyEntity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaId", insertable = false, updatable = false)
    private CompanyEntity company;
}
