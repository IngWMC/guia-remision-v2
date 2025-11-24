package com.wmc.guiaremision.domain.model;

import com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum;
import com.wmc.guiaremision.domain.model.enums.CodigoMotivoTrasladoEnum;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO que representa una Guía de Remisión Electrónica (GRE) para el envío a
 * SUNAT.
 * Contiene toda la información relevante sobre el documento, emisor, receptor,
 * transporte y detalles de la carga.
 *
 * <p>
 * Este DTO se utiliza en la capa de aplicación y dominio para orquestar el caso
 * de uso de generación y envío de la GRE.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dispatch {
    /** Serie del documento (por ejemplo, "T001"). */
    private String documentSeries;

    /** Número correlativo del documento (por ejemplo, "00001234"). */
    private String documentNumber;

    /** Número completo del documento (serie + correlativo). */
    private String documentCode;

    /** Fecha de emisión de la guía de remisión (formato yyyy-MM-dd). */
    private LocalDate issueDate;

    /** Hora de emisión de la guía de remisión (formato HH:mm:ss). */
    private LocalTime issueTime;

    /** Tipo de documento (por ejemplo, "09" para guía de remisión remitente). */
    private TipoDocumentoEnum documentType;

    /** Información del remitente de la guía de remisión. */
    private Contributor sender;

    /** Información del destinatario de la guía de remisión. */
    private Contributor receiver;

    /** Glosa o descripción adicional de la guía de remisión. */
    private String note;

    /**
     * Código de modalidad de transporte (por ejemplo, "01" para transporte
     * público).
     */
    private CodigoModalidadTransporteEnum transportModeCode;

    /** Código del motivo de traslado (por ejemplo, "01" para venta). */
    private CodigoMotivoTrasladoEnum reasonForTransferCode;

    /** Descripción del motivo de traslado. */
    private String reasonForTransferDescription;

    /** Fecha de traslado de la mercancía (formato yyyy-MM-dd). */
    private LocalDate transferDate;

    /** Código de puerto de embarque/desembarque, si aplica. */
    private String portCode;

    /** Indica si hay transbordo en el traslado. */
    private Boolean transshipmentIndicator;

    /** Unidad de medida del peso total (por ejemplo, "KGM"). */
    private String totalGrossWeightUnit;

    /** Peso total de la mercancía trasladada. */
    private BigDecimal totalGrossWeight;

    /** Número de bultos transportados. */
    private Integer packageQuantity;

    /** Número de contenedor, si aplica. */
    private Integer containerNumber;

    /** Dirección de partida de la mercancía. */
    private Address departureAddress;

    /** Dirección de llegada de la mercancía. */
    private Address arrivalAddress;

    /** Información del vehículo utilizado para el traslado. */
    private Vehicle vehicle;

    /** Información del chofer responsable del traslado. */
    private Driver driver;

    /** Información del transportista encargado del traslado. */
    private Contributor carrier;

    /** Lista de detalles de la guía (productos o bienes transportados). */
    private List<DispatchDetail> dispatchDetails;

    /** Documento relacionado (por ejemplo, factura o boleta asociada). */
    private RelatedDocument relatedDocument;

    /** Guía de baja asociada, si corresponde. */
    private RelatedDocument cancellationGuide;

    /** Estado de SUNAT tras el envío de la guía. Solo para el Response */
    private String sunatStatus;
    /** Identificador de la solicitud de envío a SUNAT. Solo para el Response */
    private String requestId;
}