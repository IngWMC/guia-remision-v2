package com.wmc.guiaremision.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la configuración de correo electrónico de una empresa.
 * <p>
 * Esta entidad almacena todos los parámetros necesarios para el envío de
 * correos
 * electrónicos a través de un servidor SMTP, incluyendo credenciales de
 * autenticación
 * y configuraciones de seguridad.
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
@Table(name = "configuracion_correo")
public class EmailConfigurationEntity extends AuditableEntity {

  /**
   * Identificador único de la configuración de correo.
   * <p>
   * Generado automáticamente por la base de datos mediante auto-incremento.
   * </p>
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "configuracionCorreoId")
  private Long emailConfigId;

  /**
   * Identificador de la empresa a la que pertenece esta configuración.
   * <p>
   * Este campo establece la relación uno a uno con la empresa. El constraint
   * {@code unique = true} garantiza que cada empresa solo pueda tener una
   * configuración de correo.
   * </p>
   */
  @Column(name = "empresaId", nullable = false, unique = true)
  private Integer companyId;

  /**
   * Dirección de correo electrónico del remitente.
   * <p>
   * Esta es la dirección que aparecerá en el campo "De" (From) de los correos
   * enviados. Debe ser una dirección válida y autorizada por el servidor SMTP.
   * </p>
   * <p>
   * Ejemplo: {@code notificaciones@miempresa.com}
   * </p>
   */
  @Column(name = "correoRemitente", nullable = false)
  private String senderEmail;

  /**
   * Nombre del host del servidor SMTP.
   * <p>
   * Dirección del servidor que procesará el envío de correos electrónicos.
   * </p>
   * <p>
   * Ejemplos comunes:
   * <ul>
   * <li>Gmail: {@code smtp.gmail.com}</li>
   * <li>Outlook: {@code smtp.office365.com}</li>
   * <li>SendGrid: {@code smtp.sendgrid.net}</li>
   * <li>Amazon SES: {@code email-smtp.us-east-1.amazonaws.com}</li>
   * </ul>
   * </p>
   */
  @Column(name = "servidorSmtp", nullable = false)
  private String smtpServer;

  /**
   * Puerto del servidor SMTP.
   * <p>
   * Puerto de comunicación utilizado para conectarse al servidor SMTP.
   * </p>
   * <p>
   * Puertos comunes:
   * <ul>
   * <li>25: Puerto estándar SMTP (generalmente bloqueado por ISPs)</li>
   * <li>587: Puerto SMTP con STARTTLS (recomendado)</li>
   * <li>465: Puerto SMTP con SSL/TLS implícito</li>
   * </ul>
   * </p>
   */
  @Column(name = "puertoSmtp", nullable = false)
  private Integer smtpPort;

  /**
   * Usuario para autenticación en el servidor SMTP.
   * <p>
   * Credencial de usuario requerida para autenticarse en el servidor de correo.
   * Generalmente es la dirección de correo completa, aunque algunos proveedores
   * pueden usar un nombre de usuario diferente.
   * </p>
   * <p>
   * Ejemplos:
   * <ul>
   * <li>Gmail: {@code usuario@gmail.com}</li>
   * <li>SendGrid: {@code apikey}</li>
   * <li>Amazon SES: {@code AKIAIOSFODNN7EXAMPLE}</li>
   * </ul>
   * </p>
   */
  @Column(name = "usuarioSmtp", nullable = false)
  private String smtpUsername;

  /**
   * Contraseña para autenticación en el servidor SMTP.
   * <p>
   * Credencial de contraseña o token de acceso para el servidor SMTP.
   * </p>
   * <p>
   * <strong>Seguridad:</strong> Este campo debería almacenar la contraseña
   * cifrada
   * para proteger las credenciales sensibles. Se recomienda usar algoritmos de
   * cifrado como AES o herramientas como Jasypt.
   * </p>
   * <p>
   * Notas importantes:
   * <ul>
   * <li>Gmail: Usar "Contraseña de aplicación" de 16 caracteres, no la contraseña
   * personal</li>
   * <li>SendGrid: Usar API Key generado en el panel</li>
   * <li>Amazon SES: Usar Secret Access Key de AWS</li>
   * </ul>
   * </p>
   */
  @Column(name = "contrasenaSmtp", nullable = false)
  private String smtpPassword;

  /**
   * Indica si se debe usar el protocolo SSL para la conexión SMTP.
   * <p>
   * SSL (Secure Sockets Layer) proporciona cifrado de extremo a extremo desde
   * el inicio de la conexión. Generalmente usado con el puerto 465.
   * </p>
   * <p>
   * Valores:
   * <ul>
   * <li>{@code true}: Habilita SSL (conexión segura implícita)</li>
   * <li>{@code false}: Deshabilita SSL</li>
   * </ul>
   * </p>
   * <p>
   * <strong>Nota:</strong> No usar SSL y TLS simultáneamente. Elegir uno según
   * los requisitos del servidor SMTP.
   * </p>
   */
  @Column(name = "usarSsl")
  private Boolean useSsl;

  /**
   * Indica si se debe usar el protocolo TLS (STARTTLS) para la conexión SMTP.
   * <p>
   * TLS (Transport Layer Security) es el protocolo moderno y recomendado para
   * cifrar conexiones SMTP. Inicia como conexión no cifrada y luego actualiza
   * a conexión cifrada mediante el comando STARTTLS. Generalmente usado con el
   * puerto 587.
   * </p>
   * <p>
   * Valores:
   * <ul>
   * <li>{@code true}: Habilita STARTTLS (recomendado para puerto 587)</li>
   * <li>{@code false}: Deshabilita STARTTLS</li>
   * </ul>
   * </p>
   * <p>
   * <strong>Recomendación:</strong> Usar TLS (puerto 587) en lugar de SSL (puerto
   * 465)
   * para nuevas implementaciones, ya que es el estándar actual.
   * </p>
   */
  @Column(name = "usarTls")
  private Boolean useTls;

  // Relaciones

  /**
   * Empresa a la que pertenece esta configuración de correo.
   * <p>
   * Relación uno a uno con {@link CompanyEntity}. Esta relación es de tipo
   * {@code LAZY}, lo que significa que los datos de la empresa solo se cargarán
   * cuando se acceda explícitamente a esta propiedad.
   * </p>
   */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "empresaId", insertable = false, updatable = false)
  private CompanyEntity company;
}
