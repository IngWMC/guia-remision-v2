package com.wmc.guiaremision.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity that represents a company
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "createdAt", "modifiedAt" })
@Entity
@Table(name = "empresa")
public class CompanyEntity extends AuditableEntity {

    /**
     * Unique ID of the company
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresaId")
    private Integer companyId;

    /**
     * ID of the district where the company is located
     */
    @Column(name = "distritoId", nullable = false)
    private Integer districtId;

    /**
     * ID of the parent company (for subsidiary companies)
     */
    @Column(name = "empresaPadreId")
    private Integer parentCompanyId;

    /**
     * ID of the company's identity document type
     */
    @Column(name = "tipoDocumentoIdentidadId", nullable = false)
    private Integer identityDocumentTypeId;

    /**
     * Company's RUC number
     */
    @Column(name = "numeroDocumentoIdentidad", nullable = false)
    private String identityDocumentNumber;

    /**
     * Company's business name
     */
    @Column(name = "razonSocial", nullable = false)
    private String legalName;

    /**
     * Company name
     */
    @Column(name = "nombreComercial", nullable = false)
    private String tradeName;

    /**
     * Company address
     */
    @Column(name = "direccion")
    private String address;

    /**
     * Company phone
     */
    @Column(name = "telefono")
    private String phone;

    /**
     * Email address
     */
    @Column(name = "correo")
    private String email;

    /**
     * Sender email
     */
    @Column(name = "correoRmitente")
    private String senderEmail;

    /**
     * SMTP server
     */
    @Column(name = "serverSmtp")
    private String smtpServer;

    /**
     * SMTP port
     */
    @Column(name = "puertoSmtp")
    private String smtpPort;

    /**
     * SSL security
     */
    @Column(name = "seguridadSsl")
    private Boolean sslSecurity;

    /**
     * Server user
     */
    @Column(name = "usuarioServidor")
    private String serverUser;

    /**
     * Server password
     */
    @Column(name = "contrasenaServidor")
    private String serverPassword;

    /**
     * Username
     */
    @Column(name = "usuarioSol")
    private String solUser;

    /**
     * Password
     */
    @Column(name = "claveSol")
    private String solPassword;

    /**
     * SUNAT user
     */
    @Column(name = "codigoCliente")
    private String clientId;

    /**
     * SUNAT SOL key
     */
    @Column(name = "secretoCliente")
    private String clientSecret;

    /**
     * Online mode
     */
    @Column(name = "modoOnline")
    private Boolean modeOnline;

    // Relationships

    /**
     * District where the company is located
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distritoId", insertable = false, updatable = false)
    private DistrictEntity district;

    /**
     * Company's identity document type
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoDocumentoIdentidadId", insertable = false, updatable = false)
    private IdentityDocumentTypeEntity identityDocumentType;

    /**
     * Parent company (for subsidiary companies)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaPadreId", insertable = false, updatable = false)
    private CompanyEntity parentCompany;

    /**
     * Subsidiary companies
     */
    @OneToMany(mappedBy = "parentCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CompanyEntity> subsidiaryCompanies = new HashSet<>();

    /**
     * Company parameters
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ParameterEntity> parameters = new HashSet<>();

    /**
     * Company documents
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DocumentEntity> documents = new HashSet<>();
}
