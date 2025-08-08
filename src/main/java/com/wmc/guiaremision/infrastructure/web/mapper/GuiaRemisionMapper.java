package com.wmc.guiaremision.infrastructure.web.mapper;

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
import com.wmc.guiaremision.infrastructure.common.Convert;
import com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant;
import com.wmc.guiaremision.infrastructure.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Chofer;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DetalleGuiaDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DireccionDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DocumentoRelacionadoDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.EmisorDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.ReceptorDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.TransportistaDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.VehiculoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
  @Mapping(target = "issueDate", source = "fechaEmision", qualifiedByName = "dateStringToLocalDate")
  @Mapping(target = "issueTime", source = "horaEmision", qualifiedByName = "timeStringToLocalTime")
  @Mapping(target = "documentType", source = "tipoDocumento", qualifiedByName = "tipoStringToTipoDocumentoEnum")
  @Mapping(target = "sender", source = "emisor")
  @Mapping(target = "receiver", source = "receptor")
  @Mapping(target = "note", source = "glosa")
  @Mapping(target = "transportModeCode", source = "codigoModalidadTransporte", qualifiedByName = "codigoStringToCodigoModalidadTransporteEnum")
  @Mapping(target = "reasonForTransferCode", source = "codigoMotivoTraslado", qualifiedByName = "codigoStringToCodigoMotivoTrasladoEnum")
  @Mapping(target = "reasonForTransferDescription", source = "descripcionMotivoTraslado")
  @Mapping(target = "transferDate", source = "fechaTraslado", qualifiedByName = "dateStringToLocalDate")
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
  @Mapping(target = "dispatchDetails", source = "bienesATransportar")
  @Mapping(target = "relatedDocument", source = "documentoRelacionado")
  @Mapping(target = "cancellationGuide", source = "guiaBaja")
  Dispatch mapperCrearGuiaRemisionDtotoDispatch(CrearGuiaRemisionDto dto);

  /**
   * Convierte un Emisor a un Contributor.
   */
  @Mapping(target = "documentType", constant = "6") // RUC
  @Mapping(target = "documentNumber", source = "ruc")
  @Mapping(target = "legalName", source = "razonSocial")
  @Mapping(target = "commercialName", source = "razonSocial")
  @Mapping(target = "address", source = ".")
  Contributor emisorToContributor(EmisorDto emisor);

  /**
   * Convierte un Receptor a un Contributor.
   */
  @Mapping(target = "documentType", constant = "6") // RUC
  @Mapping(target = "documentNumber", source = "ruc")
  @Mapping(target = "legalName", source = "razonSocial")
  @Mapping(target = "commercialName", source = "razonSocial")
  @Mapping(target = "address", source = ".")
  Contributor receptorToContributor(ReceptorDto receptor);

  /**
   * Convierte un Transportista a un Contributor.
   */
  @Mapping(target = "documentType", constant = "6") // RUC
  @Mapping(target = "documentNumber", source = "numeroDocumentoIdentidad")
  @Mapping(target = "legalName", source = "razonSocial")
  @Mapping(target = "commercialName", source = "razonSocial")
  @Mapping(target = "address", source = ".")
  Contributor transportistaToContributor(TransportistaDto transportista);

  /**
   * Convierte una Direccion a un Address.
   */
  Address direccionToAddress(DireccionDto direccionDto);

  /**
   * Convierte un DetalleGuia a un DispatchDetail.
   */
  @Mapping(target = "description", source = "descripcion")
  @Mapping(target = "quantity", source = "cantidad")
  @Mapping(target = "unitOfMeasure", source = "unidadMedida")
  @Mapping(target = "productCode", source = "codigoProducto")
  DispatchDetail detalleGuiaToDispatchDetail(DetalleGuiaDto detalle);

  /**
   * Convierte un Chofer a un Driver.
   */
  @Mapping(target = "licenseNumber", source = "numeroLicencia")
  @Mapping(target = "firstName", source = "nombres")
  @Mapping(target = "lastName", source = "apellido")
  @Mapping(target = "documentNumber", source = "numeroDocumentoIdentidad")
  @Mapping(target = "documentType", constant = "tipoDocumentoIdentidad") // DNI
  Driver choferToDriver(Chofer chofer);

  /**
   * Convierte un Vehiculo a un Vehicle.
   */
  @Mapping(target = "plate", source = "placa")
  @Mapping(target = "brand", source = "marca")
  @Mapping(target = "model", source = "modelo")
  @Mapping(target = "color", source = "color")
  @Mapping(target = "year", source = "anio")
  Vehicle vehiculoToVehicle(VehiculoDto vehiculo);

  /**
   * Convierte un DocumentoRelacionado a un RelatedDocument.
   */
  @Mapping(target = "documentType", source = "tipoDocumento")
  @Mapping(target = "documentNumber", source = "numeroDocumento")
  @Mapping(target = "issueDate", source = "fechaEmision")
  @Mapping(target = "series", source = "serie")
  @Mapping(target = "sequenceNumber", source = "correlativo")
  RelatedDocument documentoRelacionadoToRelatedDocument(DocumentoRelacionadoDto documento);

  @Named("dateStringToLocalDate")
  default LocalDate dateStringToLocalDate(String date) {
    return Convert.convertDateStringToLocalDate(date, FormatsConstant.DATE_FORMAT);
  }

  @Named("timeStringToLocalTime")
  default LocalTime timeStringToLocalTime(String time) {
    return Convert.convertTimeStringToLocalTime(time, FormatsConstant.HOUR_FORMAT);
  }

  @Named("tipoStringToTipoDocumentoEnum")
  default TipoDocumentoEnum tipoStringToTipoDocumentoEnum(String tipo) {
    return TipoDocumentoEnum.fromCode(tipo);
  }

  @Named("codigoStringToCodigoModalidadTransporteEnum")
  default CodigoModalidadTransporteEnum codigoStringToCodigoModalidadTransporteEnum(String codigo) {
    return CodigoModalidadTransporteEnum.fromCode(codigo);
  }

  @Named("codigoStringToCodigoMotivoTrasladoEnum")
  default CodigoMotivoTrasladoEnum codigoStringToCodigoMotivoTrasladoEnum(String codigo) {
    return CodigoMotivoTrasladoEnum.fromCode(codigo);
  }
}