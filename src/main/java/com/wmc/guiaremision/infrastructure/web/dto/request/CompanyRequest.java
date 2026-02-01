package com.wmc.guiaremision.infrastructure.web.dto.request;

import com.wmc.guiaremision.domain.model.enums.TipoDocumentoIdentidadEnum;
import com.wmc.guiaremision.infrastructure.web.validation.EnumValid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la creación y actualización de empresas.
 * <p>
 * Contiene todos los datos necesarios para registrar una empresa en el sistema,
 * incluyendo credenciales de integración con SUNAT.
 * </p>
 */
@Getter
@Setter
public class CompanyRequest {

  @NotNull(message = "Debe seleccionar un distrito para la ubicación de la empresa")
  @Min(value = 1, message = "El distrito seleccionado no es válido")
  private int codigoDistrito;

  private Integer codigoEmpresaPadre;

  @NotNull(message = "Debe seleccionar el tipo de documento de identidad (RUC, DNI, CE, etc.)")
  @EnumValid(enumClass = TipoDocumentoIdentidadEnum.class, message = "El tipo de documento seleccionado no es válido")
  private String tipoDocumentoIdentidad;

  @NotBlank(message = "Debe ingresar el número de documento de identidad (RUC de 11 dígitos)")
  @Pattern(regexp = "^[0-9]{8,11}$", message = "El número de documento debe tener entre 8 y 11 dígitos numéricos")
  private String numeroDocumentoIdentidad;

  @NotBlank(message = "Debe ingresar la razón social o denominación legal de la empresa")
  private String razonSocial;

  @NotBlank(message = "Debe ingresar el nombre comercial de la empresa")
  private String nombreComercial;

  @NotBlank(message = "Debe ingresar la dirección fiscal de la empresa")
  private String direccion;

  @NotNull(message = "Debe seleccionar un departamento para la ubicación de la empresa")
  @Min(value = 1, message = "El departamento seleccionado no es válido")
  private int codigoDepartamento;

  @NotNull(message = "Debe seleccionar una provincia para la ubicación de la empresa")
  @Min(value = 1, message = "La provincia seleccionada no es válido")
  private int codigoProvincia;

  @NotBlank(message = "Debe ingresar un número de teléfono de contacto")
  @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", message = "El formato del teléfono no es válido. Ejemplos: +51987654321, (01)123-4567")
  private String telefono;

  @NotBlank(message = "Debe ingresar un correo electrónico de contacto")
  @Email(message = "El formato del correo electrónico no es válido. Ejemplo: contacto@empresa.com")
  private String correo;

  @NotBlank(message = "Debe ingresar el usuario SOL (formato: RUC + Usuario SOL, ej: 20123456789ADMIN01)")
  @Pattern(regexp = "^[0-9]{11}[A-Za-z0-9]+$", message = "El usuario SOL debe iniciar con el RUC de 11 dígitos seguido del código de usuario")
  private String usuarioSol;

  @NotBlank(message = "Debe ingresar la contraseña del usuario SOL de SUNAT")
  private String claveSol;

  @NotBlank(message = "Debe ingresar el Client ID generado en el portal SOL de SUNAT")
  @Pattern(regexp = "^[a-zA-Z0-9-]{30,}$", message = "El Client ID debe tener al menos 30 caracteres alfanuméricos")
  private String clientId;

  @NotBlank(message = "Debe ingresar el Client Secret generado en el portal SOL de SUNAT")
  private String clientSecret;

}