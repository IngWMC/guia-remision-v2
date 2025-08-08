package com.wmc.guiaremision.infrastructure.web.dto.request;

import com.wmc.guiaremision.infrastructure.web.dto.shared.ChoferDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DetalleGuiaDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DireccionDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DocumentoRelacionadoDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.EmisorDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.ReceptorDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.TransportistaDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.VehiculoDto;
import com.wmc.guiaremision.infrastructure.web.validation.CodigoModalidadTransporteValid;
import com.wmc.guiaremision.infrastructure.web.validation.CodigoMotivoTrasladoValid;
import com.wmc.guiaremision.infrastructure.web.validation.DateValid;
import com.wmc.guiaremision.infrastructure.web.validation.DescripcionMotivoTrasladoValid;
import com.wmc.guiaremision.infrastructure.web.validation.EnumValid;
import com.wmc.guiaremision.infrastructure.web.validation.FechaTrasladoValid;
import com.wmc.guiaremision.infrastructure.web.validation.SerieDocumentoValid;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum;
import com.wmc.guiaremision.domain.model.enums.CodigoMotivoTrasladoEnum;

import com.wmc.guiaremision.infrastructure.web.validation.VehiculoValid;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para la creación de guías de remisión electrónicas según estándares
 * SUNAT.
 * <p>
 * Esta clase representa la estructura de datos requerida para generar guías de
 * remisión
 * electrónicas, incluyendo validaciones de campos obligatorios, formatos
 * específicos
 * y reglas de negocio según la normativa peruana.
 * <p>
 * El DTO incluye validaciones para:
 * <ul>
 * <li>Serie y correlativo del documento</li>
 * <li>Fechas y horas de emisión</li>
 * <li>Tipos de documento y códigos de modalidad de transporte</li>
 * <li>Información del emisor, receptor y transportista</li>
 * <li>Direcciones de partida y llegada</li>
 * <li>Detalles de bienes a transportar</li>
 * </ul>
 * <p>
 * Las validaciones se aplican tanto a nivel de campo individual como a nivel de
 * clase
 * para reglas de negocio complejas que involucran múltiples campos.
 *
 * @author Sistema de Guías de Remisión
 * @version 2.0
 * @since 1.0
 */
@Getter
@Setter
@SerieDocumentoValid
@CodigoModalidadTransporteValid
@CodigoMotivoTrasladoValid
@DescripcionMotivoTrasladoValid
@FechaTrasladoValid
@VehiculoValid
public class CrearGuiaRemisionDto {
    /**
     * Serie del documento de guía de remisión.
     * <p>
     * Debe tener exactamente 4 caracteres y comenzar con 'T' o 'V' según el tipo de
     * documento.
     * Se valida en conjunto con el tipo de documento mediante
     * {@link SerieDocumentoValid}.
     */
    @NotBlank
    @Size(min = 4, max = 4, message = "La serie del documento debe tener exactamente 4 caracteres.")
    @Pattern(regexp = "[TV].*", message = "La serie del documento debe comenzar con la letra 'T' o 'V'.")
    private String serieDocumento;

    /**
     * Correlativo del documento de guía de remisión.
     * <p>
     * Número secuencial que identifica únicamente el documento dentro de la serie.
     */
    @NotBlank
    private String correlativoDocumento;

    /**
     * Número completo del documento (serie-correlativo).
     * <p>
     * Se genera automáticamente al establecer serieDocumento y
     * correlativoDocumento.
     */
    private String numeroDocumento;

    /**
     * Fecha de emisión del documento en formato YYYY-MM-DD.
     * <p>
     * Debe ser una fecha válida y no puede ser posterior a la fecha actual.
     */
    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe tener el formato YYYY-MM-DD.")
    @DateValid
    private String fechaEmision;

    /**
     * Hora de emisión del documento en formato HH:MM:SS.
     * <p>
     * Hora en que se genera la guía de remisión.
     */
    @NotBlank
    private String horaEmision;

    /**
     * Tipo de documento según catálogo SUNAT.
     * <p>
     * Valores permitidos: "09" (Guía de remisión remitente), "31" (Guía de remisión
     * transportista).
     */
    @NotNull
    @EnumValid(enumClass = TipoDocumentoEnum.class)
    private String tipoDocumento;

    /**
     * Información del emisor de la guía de remisión.
     * <p>
     * Contiene datos del contribuyente que emite el documento.
     */
    @NotNull
    private EmisorDto emisor;

    /**
     * Información del receptor de la guía de remisión.
     * <p>
     * Contiene datos del contribuyente que recibe los bienes.
     */
    @NotNull
    private ReceptorDto receptor;

    /**
     * Descripción o motivo del traslado de mercancías.
     * <p>
     * Texto explicativo del propósito del traslado.
     */
    @NotNull
    private String glosa;

    /**
     * Código de modalidad de transporte según catálogo SUNAT.
     * <p>
     * Valores permitidos: "01" (Transporte público), "02" (Transporte privado).
     */
    @EnumValid(enumClass = CodigoModalidadTransporteEnum.class)
    private String codigoModalidadTransporte;

    /**
     * Código de motivo de traslado según catálogo SUNAT.
     * <p>
     * Define el motivo específico del traslado de mercancías.
     */
    @EnumValid(enumClass = CodigoMotivoTrasladoEnum.class)
    private String codigoMotivoTraslado;

    /**
     * Descripción detallada del motivo de traslado.
     * <p>
     * Texto explicativo que complementa el código de motivo.
     */
    private String descripcionMotivoTraslado;

    /**
     * Fecha de traslado de mercancías en formato YYYY-MM-DD.
     * <p>
     * Fecha en que se realiza el traslado físico de los bienes.
     * TODO: Validar cuando la ModalidadTransporte sea 01 y 02, cuando el tipo
     * documento es 09 y validar formato
     */
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe tener el formato YYYY-MM-DD.")
    private String fechaTraslado;

    /**
     * Código del puerto para operaciones de comercio exterior.
     * <p>
     * TODO: solo para tipo documento 09 (Remitente) y usa el catalogo 63
     */
    private String codigoPuerto; // TODO: solo para tipo documento 09 (Remitente) y usa el catalogo 63

    /**
     * Indica si el traslado incluye transbordo de mercancías.
     * <p>
     * TODO: Validar si es necesario
     */
    private Boolean indicaTransbordo; // TODO: Validar si es necesario

    /**
     * Unidad de medida del peso total de las mercancías.
     * <p>
     * Ejemplo: "KGM" (kilogramos), "TNE" (toneladas).
     */
    @NotBlank
    private String unidadPesoTotal;

    /**
     * Peso total de las mercancías a transportar.
     * <p>
     * Valor numérico del peso total en la unidad especificada.
     */
    @NotNull
    private BigDecimal pesoTotal;

    /**
     * Número total de bultos a transportar.
     * <p>
     * TODO: Si el 'Motivo de traslado' es '08' o '09'
     */
    private Integer numeroBultos; // TODO: Si el 'Motivo de traslado' es '08' o '09'

    /**
     * Número de contenedores para el traslado.
     * <p>
     * TODO: Si el 'Motivo de traslado' es '08' o '09'
     */
    private Integer numeroContenedor; // TODO: Si el 'Motivo de traslado' es '08' o '09'

    /**
     * Dirección de partida de las mercancías.
     * <p>
     * Ubicación desde donde se inicia el traslado.
     */
    @NotNull
    @Valid
    private DireccionDto direccionPartida;

    /**
     * Dirección de llegada de las mercancías.
     * <p>
     * Ubicación donde finaliza el traslado.
     */
    @NotNull
    @Valid
    private DireccionDto direccionLlegada;

    /**
     * Información del vehículo utilizado para el transporte.
     * <p>
     * Validar cuando el tipo de documento sea '09' (Guía de remisión remitente) y
     * la modalidad de transporte sea '02' (Transporte privado). También valida cuando
     * el tipo de documento sea '31' (Guía de remisión transportista)
     */
    @Valid
    private VehiculoDto vehiculo;

    /**
     * Información del conductor del vehículo.
     * <p>
     * Datos del chofer responsable del transporte.
     * TODO: Validar segun la modalidad de transporte
     */
    @NotNull
    @Valid
    private ChoferDto chofer; // TODO: Validar segun la modalidad de transporte

    /**
     * Información del transportista contratado.
     * <p>
     * Datos de la empresa o persona que realiza el transporte.
     * Validar cuando el tipo de documento sea '09' (Guía de remisión remitente) y
     * la modalidad de transporte sea '01' (Transporte público)
     */
    @Valid
    private TransportistaDto transportista;

    /**
     * Lista de bienes o mercancías a transportar.
     * <p>
     * Detalle de cada producto o mercancía incluida en el traslado.
     */
    @NotNull
    @Valid
    private List<DetalleGuiaDto> bienesATransportar;

    /**
     * Documento relacionado con la guía de remisión.
     * <p>
     * Referencia a otro documento que justifica o complementa el traslado.
     */
    private DocumentoRelacionadoDto documentoRelacionado;

    /**
     * Guía de baja asociada al traslado.
     * <p>
     * Documento que autoriza la baja de mercancías.
     */
    private DocumentoRelacionadoDto guiaBaja;

    /**
     * Identificador único del envío.
     * <p>
     * Valor por defecto "1", utilizado para identificación interna del sistema.
     */
    @Getter
    private String shipmentId = "1";

    public void setSerieDocumento(String serieDocumento) {
        this.serieDocumento = serieDocumento;
        actualizarNumeroDocumento();
    }

    public void setCorrelativoDocumento(String correlativoDocumento) {
        this.correlativoDocumento = correlativoDocumento;
        actualizarNumeroDocumento();
    }

    private void actualizarNumeroDocumento() {
        this.numeroDocumento = this.serieDocumento + "-" + this.correlativoDocumento;
    }
}