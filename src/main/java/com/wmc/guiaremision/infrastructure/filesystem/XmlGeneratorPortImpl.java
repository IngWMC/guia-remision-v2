package com.wmc.guiaremision.infrastructure.filesystem;

import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.DATE_FORMAT;
import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.HOUR_FORMAT;

import com.wmc.guiaremision.domain.dto.XmlDocumentResponse;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.DispatchDetail;
import com.wmc.guiaremision.domain.model.Vehicle;
import com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum;
import com.wmc.guiaremision.domain.model.enums.CodigoMotivoTrasladoEnum;
import com.wmc.guiaremision.domain.spi.file.XmlGeneratorPort;
import com.wmc.guiaremision.infrastructure.common.Convert;
import com.wmc.guiaremision.infrastructure.common.Util;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.CarrierParty;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.DeliveryAddress;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.DespatchAddress;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.DespatchLine;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.DriverPerson;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.Party;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.Shipment;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.ShipmentStage;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.SignatoryParty;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.Signature;
import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.DespatchAdviceTypeCode;
import com.wmc.guiaremision.infrastructure.ubl.document.DespatchAdvice;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.wmc.guiaremision.domain.model.Contributor;
import com.wmc.guiaremision.domain.model.Driver;

/**
 * Implementación del puerto para generar XML de guías de remisión electrónica
 * (GRE).
 *
 * <p>
 * Esta clase es responsable de transformar objetos de dominio {@link Dispatch}
 * en documentos XML siguiendo el estándar UBL (Universal Business Language)
 * requerido por SUNAT.
 * </p>
 *
 * <p>
 * La generación del XML incluye:
 * </p>
 * <ul>
 * <li>Datos básicos del documento (número, fecha, tipo)</li>
 * <li>Información de firma digital</li>
 * <li>Datos del emisor y receptor</li>
 * <li>Información del envío y transporte</li>
 * <li>Direcciones de origen y destino</li>
 * <li>Detalles de los bienes transportados</li>
 * </ul>
 *
 * <p>
 * Soporta diferentes modalidades de transporte:
 * </p>
 * <ul>
 * <li>Transporte público: Incluye datos del transportista</li>
 * <li>Transporte privado: Incluye datos del conductor y vehículo</li>
 * </ul>
 *
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 * @see XmlGeneratorPort
 * @see Dispatch
 * @see DespatchAdvice
 */
@Service
public class XmlGeneratorPortImpl implements XmlGeneratorPort {

  /**
   * Genera el XML de una guía de remisión electrónica a partir de un objeto
   * Dispatch.
   *
   * <p>
   * Este método aplica un enfoque funcional para transformar el objeto de dominio
   * en XML siguiendo la siguiente pipeline:
   * </p>
   * <ol>
   * <li>Valida los datos de entrada</li>
   * <li>Transforma Dispatch a UBL DespatchAdvice</li>
   * <li>Serializa el objeto UBL a XML</li>
   * <li>Envuelve el XML en un objeto de respuesta</li>
   * </ol>
   *
   * @param dispatch Objeto de dominio con los datos de la guía de remisión.
   *                 No puede ser null y debe contener emisor y receptor válidos.
   * @return {@link XmlDocumentResponse} conteniendo el XML generado sin firmar
   * @throws IllegalArgumentException si el dispatch es null o no tiene datos
   *                                  obligatorios
   * @throws RuntimeException         si ocurre un error durante la generación del
   *                                  XML
   * @since 1.0
   */
  @Override
  public XmlDocumentResponse generateDispatchXml(Dispatch dispatch) {
    return Optional.ofNullable(dispatch)
        .filter(this::isValidDispatch)
        .map(this::mapDispatchToUbl)
        .map(ubl -> Util.generateXml(DespatchAdvice.class, ubl))
        .map(xml -> XmlDocumentResponse.builder().unsignedXml(xml).build())
        .orElseThrow(() -> new RuntimeException(
            "Error al generar el XML de la guía de remisión"));
  }

  /**
   * Valida que un objeto Dispatch contenga los datos mínimos requeridos.
   *
   * <p>
   * Este método utiliza un enfoque funcional para verificar que:
   * </p>
   * <ul>
   * <li>El dispatch no sea null</li>
   * <li>Tenga un emisor válido</li>
   * <li>Tenga un receptor válido</li>
   * </ul>
   *
   * @param dispatch Objeto a validar
   * @return true si el dispatch es válido, false en caso contrario
   * @since 1.0
   */
  private boolean isValidDispatch(Dispatch dispatch) {
    return Optional.ofNullable(dispatch)
        .filter(d -> d.getSender() != null)
        .filter(d -> d.getReceiver() != null)
        .isPresent();
  }

  /**
   * Transforma un objeto Dispatch del dominio a un objeto UBL DespatchAdvice.
   *
   * <p>
   * Este método aplica un patrón de construcción secuencial donde cada
   * método especializado configura una sección específica del documento UBL:
   * </p>
   *
   * @param dispatch Objeto de dominio con los datos de la guía de remisión
   * @return {@link DespatchAdvice} Objeto UBL completamente configurado
   * @since 1.0
   */
  private DespatchAdvice mapDispatchToUbl(Dispatch dispatch) {
    DespatchAdvice ubl = new DespatchAdvice();

    // Configuración secuencial usando métodos especializados
    setBasicData(ubl, dispatch);
    setSignatureData(ubl, dispatch);
    setSenderData(ubl, dispatch);
    setReceiverData(ubl, dispatch);
    setShipmentData(ubl, dispatch);
    setTransportModeData(ubl, dispatch);
    setAddressData(ubl, dispatch);
    setVehicleData(ubl, dispatch);
    setDespatchLines(ubl, dispatch);

    return ubl;
  }

  /**
   * Configura los datos básicos del documento UBL.
   *
   * <p>
   * Establece información fundamental como:
   * </p>
   * <ul>
   * <li>Número de documento</li>
   * <li>Fecha y hora de emisión</li>
   * <li>Tipo de documento</li>
   * <li>Glosa o notas</li>
   * </ul>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setBasicData(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getDocumentNumber()).ifPresent(ubl::setId);
    Optional.ofNullable(dispatch.getIssueDate())
        .map(date -> Convert.convertLocalDateToDateString(date, DATE_FORMAT))
        .ifPresent(ubl::setIssueDate);
    Optional.ofNullable(dispatch.getIssueTime())
        .map(time -> Convert.convertLocalTimeToTimeString(time, HOUR_FORMAT))
        .ifPresent(ubl::setIssueTime);
    Optional.ofNullable(dispatch.getDocumentType())
        .map(type -> new DespatchAdviceTypeCode(type.getCodigo()))
        .ifPresent(ubl::setDespatchAdviceTypeCode);
    Optional.ofNullable(dispatch.getNote()).ifPresent(ubl::setNote);
  }

  /**
   * Configura los datos de firma digital del documento UBL.
   *
   * <p>
   * Establece la información necesaria para la firma digital incluyendo:
   * </p>
   * <ul>
   * <li>Identificador de la firma</li>
   * <li>Datos del firmante (emisor)</li>
   * <li>Referencia externa de la firma</li>
   * </ul>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setSignatureData(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getSender()).ifPresent(sender -> {
      Signature signature = ubl.getSignature();

      signature.setId(dispatch.getDocumentNumber());
      signature.setNote("WWWW.WMC.COM.PE");

      SignatoryParty signatoryParty = signature.getSignatoryParty();
      signatoryParty.getPartyIdentification().setId(sender.getDocumentNumber());
      signatoryParty.getPartyIdentification().setSchemeID(sender.getDocumentType());
      signatoryParty.getPartyName().setName(sender.getLegalName());

      signature.getDigitalSignatureAttachment().getExternalReference().setUri("#signatureWMC");
    });
  }

  /**
   * Configura los datos del emisor/remitente en el documento UBL.
   *
   * <p>
   * Establece la información del emisor como proveedor del despacho:
   * </p>
   * <ul>
   * <li>Identificación del emisor</li>
   * <li>Tipo de documento</li>
   * <li>Entidad legal</li>
   * </ul>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setSenderData(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getSender()).ifPresent(sender -> {
      Party supplierParty = ubl.getDespatchSupplierParty().getParty();

      supplierParty.getPartyIdentification().setId(sender.getDocumentNumber());
      supplierParty.getPartyIdentification().setSchemeID(sender.getDocumentType());
      supplierParty.getPartyLegalEntity().setCompanyId(sender.getDocumentNumber());
    });
  }

  /**
   * Configura los datos del receptor/destinatario en el documento UBL.
   *
   * <p>
   * Establece la información del receptor como cliente del despacho:
   * </p>
   * <ul>
   * <li>Identificación del receptor</li>
   * <li>Tipo de documento</li>
   * <li>Razón social</li>
   * </ul>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setReceiverData(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getReceiver()).ifPresent(receiver -> {
      Party customerParty = ubl.getDeliveryCustomerParty().getParty();

      customerParty.getPartyIdentification().setId(receiver.getDocumentNumber());
      customerParty.getPartyIdentification().setSchemeID(receiver.getDocumentType());
      customerParty.getPartyLegalEntity().setRegistrationName(receiver.getLegalName());
    });
  }

  /**
   * Configura los datos del envío en el documento UBL.
   *
   * <p>
   * Establece información del envío incluyendo:
   * </p>
   * <ul>
   * <li>Motivo del traslado</li>
   * <li>Peso total y unidad de medida</li>
   * <li>Modalidad de transporte</li>
   * <li>Fecha de traslado</li>
   * </ul>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setShipmentData(DespatchAdvice ubl, Dispatch dispatch) {
    Shipment shipment = ubl.getShipment();

    shipment.setId("1");

    Optional.ofNullable(dispatch.getReasonForTransferCode())
        .map(CodigoMotivoTrasladoEnum::getCodigo)
        .ifPresent(code -> shipment.getHandlingCode().setValue(code));

    Optional.ofNullable(dispatch.getReasonForTransferDescription())
        .ifPresent(shipment::setHandlingInstructions);

    Optional.ofNullable(dispatch.getTotalGrossWeight())
        .ifPresent(weight -> shipment.getGrossWeightMeasure().setValue(weight));

    Optional.ofNullable(dispatch.getTotalGrossWeightUnit())
        .ifPresent(unit -> shipment.getGrossWeightMeasure().setUnitCode(unit));

    ShipmentStage shipmentStage = shipment.getShipmentStage();

    Optional.ofNullable(dispatch.getTransportModeCode())
        .map(CodigoModalidadTransporteEnum::getCodigo)
        .ifPresent(code -> shipmentStage.getTransportModeCode().setValue(code));

    Optional.ofNullable(dispatch.getTransferDate())
        .map(date -> Convert.convertLocalDateToDateString(date, DATE_FORMAT))
        .ifPresent(date -> shipmentStage.getTransitPeriod().setStartDate(date));
  }

  /**
   * Configura los datos específicos según la modalidad de transporte.
   *
   * <p>
   * Aplica configuración condicional basada en la modalidad:
   * </p>
   * <ul>
   * <li>Transporte público: Datos del transportista</li>
   * <li>Transporte privado: Datos del conductor</li>
   * </ul>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setTransportModeData(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getTransportModeCode()).ifPresent(transportMode -> {
      ShipmentStage shipmentStage = ubl.getShipment().getShipmentStage();

      switch (transportMode) {
        case TRANSPORTE_PUBLICO:
          Optional.ofNullable(dispatch.getCarrier())
              .ifPresent(carrier -> setPublicTransportData(shipmentStage,
                  carrier));
          break;
        case TRANSPORTE_PRIVADO:
          Optional.ofNullable(dispatch.getDriver())
              .ifPresent(driver -> setPrivateTransportData(shipmentStage,
                  driver));
          break;
        default:
          // No action needed for other transport modes
          break;
      }
    });
  }

  /**
   * Configura los datos del transportista para transporte público.
   *
   * <p>
   * Establece información del transportista incluyendo:
   * </p>
   * <ul>
   * <li>Identificación y tipo de documento</li>
   * <li>Razón social</li>
   * <li>Número MTC</li>
   * </ul>
   *
   * @param shipmentStage Etapa del envío UBL
   * @param carrier       Datos del transportista
   * @since 1.0
   */
  private void setPublicTransportData(ShipmentStage shipmentStage, Contributor carrier) {
    Optional.ofNullable(carrier).ifPresent(c -> {
      CarrierParty carrierParty = shipmentStage.getCarrierParty();

      Optional.ofNullable(c.getDocumentNumber())
          .ifPresent(docNum -> carrierParty.getPartyIdentification().setId(docNum));
      Optional.ofNullable(c.getDocumentType())
          .ifPresent(docType -> carrierParty.getPartyIdentification()
              .setSchemeID(docType));
      Optional.ofNullable(c.getLegalName())
          .ifPresent(name -> carrierParty.getPartyLegalEntity()
              .setRegistrationName(name));
      Optional.ofNullable(c.getMtcNumber())
          .ifPresent(mtc -> carrierParty.getPartyLegalEntity().setCompanyId(mtc));
    });
  }

  /**
   * Configura los datos del conductor para transporte privado.
   *
   * <p>
   * Establece información del conductor incluyendo:
   * </p>
   * <ul>
   * <li>Identificación y tipo de documento</li>
   * <li>Nombres y apellidos</li>
   * <li>Número de licencia</li>
   * </ul>
   *
   * @param shipmentStage Etapa del envío UBL
   * @param driver        Datos del conductor
   * @since 1.0
   */
  private void setPrivateTransportData(ShipmentStage shipmentStage, Driver driver) {
    Optional.ofNullable(driver).ifPresent(d -> {
      DriverPerson driverPerson = shipmentStage.getDriverPerson();

      Optional.ofNullable(d.getDocumentNumber())
          .ifPresent(docNum -> driverPerson.getId().setId(docNum));
      Optional.ofNullable(d.getDocumentType())
          .ifPresent(docType -> driverPerson.getId().setSchemeID(docType));
      Optional.ofNullable(d.getFirstName())
          .ifPresent(driverPerson::setFirstName);
      Optional.ofNullable(d.getLastName())
          .ifPresent(driverPerson::setFamilyName);

      driverPerson.setJobTitle("Principal");

      Optional.ofNullable(d.getLicenseNumber())
          .ifPresent(license -> driverPerson.getIdentityDocumentReference()
              .setId(license));
    });
  }

  /**
   * Configura las direcciones de origen y destino del envío.
   *
   * <p>
   * Establece tanto la dirección de partida como la de llegada
   * utilizando un enfoque funcional para mayor claridad.
   * </p>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setAddressData(DespatchAdvice ubl, Dispatch dispatch) {
    setArrivalAddress(ubl, dispatch);
    setDepartureAddress(ubl, dispatch);
  }

  /**
   * Configura la dirección de llegada/destino del envío.
   *
   * <p>
   * Establece los datos de la dirección de destino incluyendo
   * configuración especial para empresas (RUC).
   * </p>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setArrivalAddress(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getArrivalAddress()).ifPresent(arrivalAddress -> {
      DeliveryAddress deliveryAddress = ubl.getShipment().getDelivery().getDeliveryAddress();

      Optional.ofNullable(arrivalAddress.getUbigeo())
          .ifPresent(ubigeo -> deliveryAddress.getId().setId(ubigeo));
      Optional.ofNullable(arrivalAddress.getAddress())
          .ifPresent(address -> deliveryAddress.getAddressLine().setLine(address));

      // Configuración especial para empresas (RUC = "6")
      Optional.ofNullable(dispatch.getReceiver())
          .filter(receiver -> "6".equals(receiver.getDocumentType()))
          .ifPresent(receiver -> {
            deliveryAddress.getAddressTypeCode().setValue("0000");
            deliveryAddress.getAddressTypeCode()
                .setListID(receiver.getDocumentNumber());
          });
    });
  }

  /**
   * Configura la dirección de partida/origen del envío.
   *
   * <p>
   * Establece los datos de la dirección de origen vinculada
   * al documento del emisor.
   * </p>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setDepartureAddress(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getDepartureAddress()).ifPresent(departureAddress -> {
      DespatchAddress despatchAddress = ubl.getShipment().getDelivery().getDespatch()
          .getDespatchAddress();

      Optional.ofNullable(departureAddress.getUbigeo())
          .ifPresent(ubigeo -> despatchAddress.getId().setId(ubigeo));
      Optional.ofNullable(departureAddress.getAddress())
          .ifPresent(address -> despatchAddress.getAddressLine().setLine(address));

      despatchAddress.getAddressTypeCode().setValue("0000");

      Optional.ofNullable(dispatch.getSender())
          .map(Contributor::getDocumentNumber)
          .ifPresent(docNum -> despatchAddress.getAddressTypeCode().setListID(docNum));
    });
  }

  /**
   * Configura los datos del vehículo para transporte privado.
   *
   * <p>
   * Solo se aplica cuando la modalidad es transporte privado
   * y existe información del vehículo.
   * </p>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setVehicleData(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getTransportModeCode())
        .filter(mode -> mode == CodigoModalidadTransporteEnum.TRANSPORTE_PRIVADO)
        .flatMap(mode -> Optional.ofNullable(dispatch.getVehicle()))
        .map(Vehicle::getPlate)
        .ifPresent(plate -> ubl.getShipment().getTransportHandlingUnit().getTransportEquipment()
            .setId(plate));
  }

  /**
   * Configura las líneas de despacho con los detalles de los bienes.
   *
   * <p>
   * Transforma cada detalle del dispatch en una línea UBL,
   * asignando secuencialmente números de línea.
   * </p>
   *
   * @param ubl      Objeto UBL a configurar
   * @param dispatch Datos de origen
   * @since 1.0
   */
  private void setDespatchLines(DespatchAdvice ubl, Dispatch dispatch) {
    Optional.ofNullable(dispatch.getDispatchDetails())
        .filter(details -> !details.isEmpty())
        .map(details -> {
          AtomicInteger counter = new AtomicInteger(1);
          return details.stream()
              .map(detail -> mapDispatchDetailToDespatchLine(detail,
                  counter.getAndIncrement()))
              .collect(Collectors.toList());
        })
        .ifPresent(ubl::setDespatchLines);
  }

  /**
   * Transforma un detalle de dispatch en una línea UBL.
   *
   * <p>
   * Aplica programación funcional para configurar todos los
   * campos de la línea de despacho de forma segura.
   * </p>
   *
   * @param dispatchDetail Detalle del producto/bien a transportar
   * @param sequence       Número de secuencia de la línea
   * @return {@link DespatchLine} Línea UBL configurada
   * @since 1.0
   */
  private DespatchLine mapDispatchDetailToDespatchLine(DispatchDetail dispatchDetail, int sequence) {
    DespatchLine line = new DespatchLine();

    // Configuración funcional de la línea
    Optional.of(sequence)
        .map(String::valueOf)
        .ifPresent(seq -> {
          line.setId(seq);
          line.getOrderLineReference().setLineID(seq);
        });

    Optional.ofNullable(dispatchDetail.getQuantity())
        .map(Object::toString)
        .ifPresent(qty -> line.getDeliveredQuantity().setValue(qty));

    Optional.ofNullable(dispatchDetail.getUnitOfMeasure())
        .ifPresent(unit -> line.getDeliveredQuantity().setUnitCode(unit));

    Optional.ofNullable(dispatchDetail.getDescription())
        .ifPresent(desc -> line.getItem().setDescription(desc));

    Optional.ofNullable(dispatchDetail.getProductCode())
        .ifPresent(code -> line.getItem().getSellersItemIdentification().setId(code));

    return line;
  }
}