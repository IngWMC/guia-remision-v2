package com.wmc.guiaremision.domain.entity;

import jakarta.persistence.OneToOne;
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
 * Entidad que representa una empresa emisora de comprobantes electrónicos.
 * <p>
 * Esta entidad almacena la información completa de las empresas que emiten
 * Guías de Remisión Electrónicas (GRE) y otros comprobantes de pago
 * electrónicos,
 * incluyendo datos de identificación, ubicación, contacto y credenciales de
 * integración con SUNAT.
 * </p>
 * <p>
 * La empresa puede tener una estructura jerárquica mediante la relación
 * empresa padre-hija, permitiendo gestionar sucursales o filiales.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 * @see EmailConfigurationEntity
 * @see ParameterEntity
 * @see DocumentEntity
 * @see UserEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "createdAt", "modifiedAt" })
@Entity
@Table(name = "empresa")
public class CompanyEntity extends AuditableEntity {

    /**
     * Identificador único de la empresa.
     * <p>
     * Generado automáticamente por la base de datos mediante auto-incremento.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresaId")
    private Integer companyId;

    /**
     * Identificador del distrito donde se ubica la empresa.
     * <p>
     * Este campo es obligatorio y establece la ubicación geográfica de la empresa
     * a nivel de distrito, necesaria para la emisión de comprobantes electrónicos.
     * </p>
     *
     * @see DistrictEntity
     */
    @Column(name = "distritoId", nullable = false)
    private Integer districtId;

    /**
     * Identificador de la empresa matriz o principal.
     * <p>
     * Este campo permite establecer una jerarquía de empresas, útil para gestionar
     * sucursales o empresas subsidiarias. Si es {@code null}, indica que la empresa
     * es una empresa principal (no es sucursal de otra).
     * </p>
     * <p>
     * Ejemplo de uso: Una empresa con múltiples sucursales donde la casa matriz
     * tiene {@code parentCompanyId = null} y las sucursales referencian a la
     * matriz.
     * </p>
     */
    @Column(name = "empresaPadreId")
    private Integer parentCompanyId;

    /**
     * Identificador del tipo de documento de identidad de la empresa.
     * <p>
     * Referencia al catálogo de tipos de documento (ej: RUC, DNI, CE, etc.).
     * Este campo es obligatorio para la correcta identificación tributaria de la
     * empresa.
     * </p>
     */
    @Column(name = "tipoDocumentoIdentidad", nullable = false)
    private Integer identityDocumentType;

    /**
     * Número del documento de identidad de la empresa.
     * <p>
     * Contiene el número del documento de identificación tributaria. En Perú,
     * generalmente es el RUC (Registro Único de Contribuyentes) de 11 dígitos.
     * </p>
     * <p>
     * Ejemplos:
     * <ul>
     * <li>RUC: {@code 20123456789} (11 dígitos)</li>
     * <li>DNI: {@code 12345678} (8 dígitos para personas naturales)</li>
     * </ul>
     * </p>
     * <p>
     * Este campo es único por empresa y es usado para la emisión y firma de
     * comprobantes electrónicos.
     * </p>
     */
    @Column(name = "numeroDocumentoIdentidad", nullable = false, unique = true)
    private String identityDocumentNumber;

    /**
     * Razón social de la empresa.
     * <p>
     * Nombre legal o denominación social de la empresa tal como aparece en el
     * registro tributario (SUNAT). Este es el nombre oficial que aparecerá en
     * todos los comprobantes electrónicos emitidos.
     * </p>
     * <p>
     * Ejemplo: {@code "EMPRESA DE TRANSPORTES S.A.C."}
     * </p>
     */
    @Column(name = "razonSocial", nullable = false)
    private String legalName;

    /**
     * Nombre comercial de la empresa.
     * <p>
     * Nombre con el que la empresa es conocida comercialmente. Puede ser diferente
     * a la razón social y es usado para identificación en interfaces de usuario
     * y reportes.
     * </p>
     * <p>
     * Ejemplo: Si la razón social es {@code "TRANSPORTES RÁPIDOS S.A.C."},
     * el nombre comercial podría ser {@code "TransRápidos"}
     * </p>
     */
    @Column(name = "nombreComercial", nullable = false)
    private String tradeName;

    @Column(name = "departamentoId")
    private Integer departmentId;

    @Column(name = "provinciaId")
    private Integer provinceId;

    /**
     * Dirección fiscal o domicilio legal de la empresa.
     * <p>
     * Dirección completa donde está ubicada la empresa. Este dato aparece en
     * los comprobantes electrónicos emitidos y debe coincidir con el registro
     * en SUNAT.
     * </p>
     * <p>
     * Ejemplo: {@code "Av. Los Olivos 123, Urb. San Antonio"}
     * </p>
     */
    @Column(name = "direccion")
    private String address;

    /**
     * Número telefónico de contacto de la empresa.
     * <p>
     * Teléfono principal para comunicación con la empresa. Puede incluir código
     * de país y extensiones.
     * </p>
     * <p>
     * Ejemplos: {@code "+51 987654321"}, {@code "(01) 123-4567"}
     * </p>
     */
    @Column(name = "telefono")
    private String phone;

    /**
     * Dirección de correo electrónico de contacto general de la empresa.
     * <p>
     * Email principal de contacto de la empresa usado para comunicaciones
     * generales.
     * Este campo es diferente del correo remitente usado para enviar comprobantes
     * electrónicos (configurado en {@link EmailConfigurationEntity}).
     * </p>
     * <p>
     * Ejemplo: {@code "contacto@empresa.com"}
     * </p>
     */
    @Column(name = "correo")
    private String email;

    /**
     * Usuario SOL (Sistema de Operaciones en Línea) de SUNAT.
     * <p>
     * Credencial de usuario para autenticación en los servicios web de SUNAT.
     * Se compone del RUC de la empresa seguido del código de usuario SOL.
     * </p>
     * <p>
     * Formato: {@code RUC + Usuario SOL}
     * <br>
     * Ejemplo: Si el RUC es {@code 20123456789} y el usuario SOL es
     * {@code ADMIN01},
     * este campo contendrá: {@code "20123456789ADMIN01"}
     * </p>
     * <p>
     * <strong>Seguridad:</strong> Este campo contiene información sensible.
     * Se recomienda implementar cifrado para proteger estas credenciales.
     * </p>
     */
    @Column(name = "usuarioSol")
    private String solUser;

    /**
     * Contraseña del usuario SOL de SUNAT.
     * <p>
     * Contraseña asociada al usuario SOL para autenticación en los servicios
     * de SUNAT (emisión de comprobantes electrónicos, consultas, etc.).
     * </p>
     * <p>
     * <strong>Seguridad crítica:</strong> Este campo almacena información altamente
     * sensible. Se debe implementar cifrado robusto (AES-256 o similar) antes de
     * almacenar en la base de datos. Nunca exponer este valor en logs o respuestas
     * API.
     * </p>
     */
    @Column(name = "claveSol")
    private String solPassword;

    /**
     * Client ID para la API de SUNAT.
     * <p>
     * Identificador único del cliente generado en el menú SOL de SUNAT para
     * autenticación OAuth2 en los servicios de facturación electrónica.
     * </p>
     * <p>
     * Este identificador se genera en: Portal SUNAT → SOL → Sistema de Emisión
     * Electrónica → Generación de Credenciales OAuth.
     * </p>
     * <p>
     * Ejemplo: {@code "85e5b0ae-255c-4891-a595-0b98c65c9854"}
     * </p>
     *
     * @see #clientSecret
     */
    @Column(name = "clientId")
    private String clientId;

    /**
     * Client Secret para la API de SUNAT.
     * <p>
     * Client Secret asociada al Client ID, usada para autenticación OAuth2 en
     * los servicios web de SUNAT. Se genera junto con el Client ID en el portal
     * SOL de SUNAT.
     * </p>
     * <p>
     * Ejemplo (formato): {@code "Hty/M6QshYvPgItX2P0+Kw=="}
     * </p>
     *
     * @see #clientId
     */
    @Column(name = "clientSecret")
    private String clientSecret;

    // Relaciones

    /**
     * Configuración de correo electrónico asociada a esta empresa.
     * <p>
     * Relación uno a uno con {@link EmailConfigurationEntity}. Contiene toda
     * la configuración necesaria para el envío de correos electrónicos con
     * los comprobantes generados (servidor SMTP, credenciales, etc.).
     * </p>
     * <p>
     * La relación es de tipo {@code LAZY}, lo que significa que los datos de
     * configuración solo se cargarán cuando se acceda explícitamente a esta
     * propiedad.
     * </p>
     *
     * @see EmailConfigurationEntity
     */
    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmailConfigurationEntity emailConfiguration;

    /**
     * Distrito donde se encuentra ubicada la empresa.
     * <p>
     * Relación muchos a uno con {@link DistrictEntity}. Proporciona acceso a
     * la información completa del distrito, incluyendo provincia, departamento
     * y código de ubigeo.
     * </p>
     * <p>
     * Carga diferida ({@code LAZY}): los datos del distrito solo se recuperan
     * cuando se accede a esta propiedad.
     * </p>
     *
     * @see DistrictEntity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distritoId", insertable = false, updatable = false)
    private DistrictEntity district;

    /**
     * Empresa matriz o principal de la que depende esta empresa.
     * <p>
     * Relación muchos a uno autoreferencial con {@link CompanyEntity}. Permite
     * establecer una jerarquía de empresas donde una empresa puede ser sucursal
     * o filial de otra.
     * </p>
     * <p>
     * Si este campo es {@code null}, indica que la empresa es independiente
     * (no es sucursal de otra empresa).
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaPadreId", insertable = false, updatable = false)
    private CompanyEntity parentCompany;

    /**
     * Conjunto de empresas subsidiarias o sucursales dependientes de esta empresa.
     * <p>
     * Relación uno a muchos autoreferencial con {@link CompanyEntity}. Contiene
     * todas las empresas que tienen a esta empresa como su empresa matriz.
     * </p>
     * <p>
     * Útil para gestionar estructuras empresariales con múltiples sucursales.
     * </p>
     */
    @OneToMany(mappedBy = "parentCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CompanyEntity> subsidiaryCompanies = new HashSet<>();

    /**
     * Conjunto de parámetros de configuración de la empresa.
     * <p>
     * Relación uno a muchos con {@link ParameterEntity}. Contiene configuraciones
     * específicas de la empresa como rutas de archivos (XML, PDF, CDR),
     * certificados digitales, logos, etc.
     * </p>
     *
     * @see ParameterEntity
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ParameterEntity> parameters = new HashSet<>();

    /**
     * Conjunto de documentos electrónicos emitidos por la empresa.
     * <p>
     * Relación uno a muchos con {@link DocumentEntity}. Contiene todos los
     * comprobantes electrónicos (Guías de Remisión Electrónicas, facturas, etc.)
     * generados por esta empresa.
     * </p>
     *
     * @see DocumentEntity
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DocumentEntity> documents = new HashSet<>();

    /**
     * Conjunto de usuarios asociados a esta empresa.
     * <p>
     * Relación uno a muchos con {@link UserEntity}. Contiene todos los usuarios
     * del sistema que tienen acceso a operar con esta empresa.
     * </p>
     *
     * @see UserEntity
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();
}
