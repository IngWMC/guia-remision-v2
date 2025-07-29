package com.wmc.guiaremision.web.mapper;

import com.wmc.guiaremision.domain.model.Address;
import com.wmc.guiaremision.domain.model.Contributor;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.DispatchDetail;
import com.wmc.guiaremision.domain.model.Driver;
import com.wmc.guiaremision.domain.model.RelatedDocument;
import com.wmc.guiaremision.domain.model.Vehicle;
import com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum;
import com.wmc.guiaremision.domain.model.enums.CodigoMotivoTrasladoEnum;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.web.dto.shared.Chofer;
import com.wmc.guiaremision.web.dto.shared.DetalleGuia;
import com.wmc.guiaremision.web.dto.shared.Direccion;
import com.wmc.guiaremision.web.dto.shared.DocumentoRelacionado;
import com.wmc.guiaremision.web.dto.shared.Emisor;
import com.wmc.guiaremision.web.dto.shared.Receptor;
import com.wmc.guiaremision.web.dto.shared.Transportista;
import com.wmc.guiaremision.web.dto.shared.Vehiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Mapper para convertir objetos de la capa web (DTOs) a objetos del dominio.
 * 
 * <p>
 * Este mapper utiliza MapStruct para generar automáticamente el código de
 * conversión entre los DTOs de entrada y los modelos de dominio utilizados
 * en la lógica de negocio.
 * </p>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuiaRemisionMapper {

  /**
   * Convierte un CrearGuiaRemisionDto a un objeto Dispatch del dominio.
   *
   * @param dto El DTO de entrada con los datos de la guía de remisión
   * @return El objeto Dispatch del dominio
   */
  @Mapping(target = "documentNumber", source = "numeroDocumento")
  @Mapping(target = "issueDate", source = "fechaEmision", qualifiedByName = "stringToLocalDate")
  @Mapping(target = "issueTime", source = "horaEmision", qualifiedByName = "stringToLocalTime")
  @Mapping(target = "documentType", source = "tipoDocumento", qualifiedByName = "stringToTipoDocumentoEnum")
  @Mapping(target = "sender", source = "emisor")
  @Mapping(target = "receiver", source = "receptor")
  @Mapping(target = "note", source = "glosa")
  @Mapping(target = "transportModeCode", source = "codigoModalidadTransporte", qualifiedByName = "stringToCodigoModalidadTransporteEnum")
  @Mapping(target = "reasonForTransferCode", source = "codigoMotivoTraslado", qualifiedByName = "stringToCodigoMotivoTrasladoEnum")
  @Mapping(target = "reasonForTransferDescription", source = "descripcionMotivoTraslado")
  @Mapping(target = "transferDate", source = "fechaTraslado", qualifiedByName = "stringToLocalDate")
  @Mapping(target = "portCode", source = "codigoPuerto")
  @Mapping(target = "transshipmentIndicator", source = "indicaTransbordo")
  @Mapping(target = "totalGrossWeightUnit", source = "unidadPesoTotal")
  @Mapping(target = "totalGrossWeight", source = "pesoTotal")
  @Mapping(target = "packageQuantity", source = "numeroBultos")
  @Mapping(target = "containerNumber", source = "numeroContenedor")
  @Mapping(target = "departureAddress", source = "direccionPartida")
  @Mapping(target = "arrivalAddress", source = "direccionLlegada")
  @Mapping(target = "vehicle", source = "vehiculo")
  @Mapping(target = "driver", source = "chofer")
  @Mapping(target = "carrier", source = "transportista")
  @Mapping(target = "dispatchDetails", source = "detalleGuias")
  @Mapping(target = "relatedDocument", source = "documentoRelacionado")
  @Mapping(target = "cancellationGuide", source = "guiaBaja")
  Dispatch toDispatch(CrearGuiaRemisionDto dto);

  /**
   * Convierte un Emisor a un Contributor.
   */
  @Mapping(target = "documentType", constant = "6") // RUC
  @Mapping(target = "documentNumber", source = "ruc")
  @Mapping(target = "legalName", source = "razonSocial")
  @Mapping(target = "commercialName", source = "razonSocial")
  @Mapping(target = "address", source = ".")
  Contributor emisorToContributor(Emisor emisor);

  /**
   * Convierte un Receptor a un Contributor.
   */
  @Mapping(target = "documentType", constant = "6") // RUC
  @Mapping(target = "documentNumber", source = "ruc")
  @Mapping(target = "legalName", source = "razonSocial")
  @Mapping(target = "commercialName", source = "razonSocial")
  @Mapping(target = "address", source = ".")
  Contributor receptorToContributor(Receptor receptor);

  /**
   * Convierte un Transportista a un Contributor.
   */
  @Mapping(target = "documentType", constant = "6") // RUC
  @Mapping(target = "documentNumber", source = "ruc")
  @Mapping(target = "legalName", source = "razonSocial")
  @Mapping(target = "commercialName", source = "razonSocial")
  @Mapping(target = "address", source = ".")
  Contributor transportistaToContributor(Transportista transportista);

  /**
   * Convierte una Direccion a un Address.
   */
  Address direccionToAddress(Direccion direccion);

  /**
   * Convierte un DetalleGuia a un DispatchDetail.
   */
  @Mapping(target = "description", source = "descripcion")
  @Mapping(target = "quantity", source = "cantidad")
  @Mapping(target = "unitOfMeasure", source = "unidadMedida")
  @Mapping(target = "weight", expression = "java(detalle.getPeso() != null ? detalle.getPeso().toString() : null)")
  @Mapping(target = "productCode", source = "codigoProducto")
  @Mapping(target = "sunatCode", source = "codigoSunat")
  DispatchDetail detalleGuiaToDispatchDetail(DetalleGuia detalle);

  /**
   * Convierte un Chofer a un Driver.
   */
  @Mapping(target = "licenseNumber", source = "numeroLicencia")
  @Mapping(target = "firstName", source = "nombre")
  @Mapping(target = "lastName", source = "apellido")
  @Mapping(target = "documentNumber", source = "dni")
  @Mapping(target = "documentType", constant = "1") // DNI
  Driver choferToDriver(Chofer chofer);

  /**
   * Convierte un Vehiculo a un Vehicle.
   */
  @Mapping(target = "plate", source = "placa")
  @Mapping(target = "brand", source = "marca")
  @Mapping(target = "model", source = "modelo")
  @Mapping(target = "color", source = "color")
  @Mapping(target = "year", source = "anio")
  Vehicle vehiculoToVehicle(Vehiculo vehiculo);

  /**
   * Convierte un DocumentoRelacionado a un RelatedDocument.
   */
  @Mapping(target = "documentType", source = "tipoDocumento")
  @Mapping(target = "documentNumber", source = "numeroDocumento")
  @Mapping(target = "issueDate", source = "fechaEmision")
  @Mapping(target = "series", source = "serie")
  @Mapping(target = "sequenceNumber", source = "correlativo")
  RelatedDocument documentoRelacionadoToRelatedDocument(DocumentoRelacionado documento);

  /**
   * Convierte una fecha en formato String a LocalDate.
   */
  @Named("stringToLocalDate")
  default LocalDate stringToLocalDate(String date) {
    if (date == null || date.trim().isEmpty()) {
      return null;
    }
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  /**
   * Convierte una hora en formato String a LocalTime.
   */
  @Named("stringToLocalTime")
  default LocalTime stringToLocalTime(String time) {
    if (time == null || time.trim().isEmpty()) {
      return null;
    }
    return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
  }

  /**
   * Convierte un String a TipoDocumentoEnum.
   */
  @Named("stringToTipoDocumentoEnum")
  default TipoDocumentoEnum stringToTipoDocumentoEnum(String tipo) {
    if (tipo == null) {
      return null;
    }
    return TipoDocumentoEnum.valueOf(tipo);
  }

  /**
   * Convierte un String a CodigoModalidadTransporteEnum.
   */
  @Named("stringToCodigoModalidadTransporteEnum")
  default CodigoModalidadTransporteEnum stringToCodigoModalidadTransporteEnum(String codigo) {
    if (codigo == null) {
      return null;
    }
    return CodigoModalidadTransporteEnum.valueOf(codigo);
  }

  /**
   * Convierte un String a CodigoMotivoTrasladoEnum.
   */
  @Named("stringToCodigoMotivoTrasladoEnum")
  default CodigoMotivoTrasladoEnum stringToCodigoMotivoTrasladoEnum(String codigo) {
    if (codigo == null) {
      return null;
    }
    return CodigoMotivoTrasladoEnum.valueOf(codigo);
  }

  /**
   * Convierte un String a BigDecimal.
   */
  @Named("stringToBigDecimal")
  default BigDecimal stringToBigDecimal(String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    return new BigDecimal(value);
  }

  /**
   * Convierte un String a Integer.
   */
  @Named("stringToInteger")
  default Integer stringToInteger(String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    return Integer.valueOf(value);
  }

  /**
   * Convierte un String a Boolean.
   */
  @Named("stringToBoolean")
  default Boolean stringToBoolean(String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    return Boolean.valueOf(value);
  }
}