package com.wmc.guiaremision.infrastructure.web.dto.request;

import com.wmc.guiaremision.infrastructure.web.dto.shared.Chofer;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DetalleGuia;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Direccion;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DocumentoRelacionado;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Emisor;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Receptor;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Transportista;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Vehiculo;
import com.wmc.guiaremision.infrastructure.web.validation.DateValid;
import com.wmc.guiaremision.infrastructure.web.validation.EnumValid;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum;
import com.wmc.guiaremision.domain.model.enums.CodigoMotivoTrasladoEnum;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CrearGuiaRemisionDto {
    @NotBlank
    @Size(min = 4, max = 4, message = "La serie del documento debe tener exactamente 4 caracteres.")
    @Pattern(regexp = "T.*", message = "La serie del documento debe comenzar con la letra 'T'.")
    private String serieDocumento;

    @NotBlank
    private String correlativoDocumento;

    private String numeroDocumento;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe tener el formato YYYY-MM-DD.")
    @DateValid
    private String fechaEmision;

    @NotBlank
    private String horaEmision;

    @NotNull
    @EnumValid(enumClass = TipoDocumentoEnum.class)
    private String tipoDocumento;

    @NotNull
    private Emisor emisor;

    @NotNull
    private Receptor receptor;

    @NotNull
    private String glosa;

    @NotNull
    @EnumValid(enumClass = CodigoModalidadTransporteEnum.class)
    private String codigoModalidadTransporte;

    @NotNull
    @EnumValid(enumClass = CodigoMotivoTrasladoEnum.class)
    private String codigoMotivoTraslado;

    private String descripcionMotivoTraslado; // TODO: Validar cuando el MotivoTraslado esa 13

    @NotNull
    private String fechaTraslado; // TODO: Validar cuando el MotivoTraslado sea 01 y 02 y validar formato

    @NotNull
    private String codigoPuerto;

    private Boolean indicaTransbordo; // TODO: Validar si es necesario

    @NotBlank
    private String unidadPesoTotal;

    @NotNull
    private BigDecimal pesoTotal; // TODO: Validar cuando el MotivoTraslado sea 08 y 09

    @NotNull
    private Integer numeroBultos;

    @NotNull
    private Integer numeroContenedor;

    @NotNull
    private Direccion direccionPartida;

    @NotNull
    private Direccion direccionLlegada;

    private Vehiculo vehiculo; // TODO: Validar segun la modalidad de transporte

    @NotNull
    private Chofer chofer; // TODO: Validar segun la modalidad de transporte

    private Transportista transportista; // TODO: Validar segun la modalidad de transporte

    @NotNull
    private List<DetalleGuia> detalleGuias;

    private DocumentoRelacionado documentoRelacionado;

    private DocumentoRelacionado guiaBaja;

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