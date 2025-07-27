package com.wmc.guiaremision.infrastructure.filesystem;

import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.DATE_FORMAT;
import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.HOUR_FORMAT;

import com.wmc.guiaremision.domain.dto.XmlDocumentResponse;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.DispatchDetail;
import com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum;
import com.wmc.guiaremision.domain.spi.XmlGeneratorPort;
import com.wmc.guiaremision.infrastructure.common.Convert;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.DespatchLine;
import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.DespatchAdviceTypeCode;
import com.wmc.guiaremision.infrastructure.ubl.document.DespatchAdvice;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class XmlGeneratorPortImpl implements XmlGeneratorPort {
    @Override
    public XmlDocumentResponse generateDispatchXml(Dispatch dispatch) {
        try {
            // Mapear Dispatch a UblDespatchAdvice
            DespatchAdvice ublDespatchAdvice = this.mapDispatchToUbl(dispatch);

            // Serializar UblDespatchAdvice a XML
            JAXBContext context = JAXBContext.newInstance(DespatchAdvice.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            StringWriter sw = new StringWriter();
            marshaller.marshal(ublDespatchAdvice, sw);

            return XmlDocumentResponse.builder()
                    .unsignedXml(sw.toString()).build();
        } catch (JAXBException e) {
            throw new RuntimeException("Error al generar el XML de la guía de remisión", e);
        }
    }

    private DespatchAdvice mapDispatchToUbl(Dispatch dispatch) {
        DespatchAdvice ubl = new DespatchAdvice();

        // Datos básicos
        ubl.setId(dispatch.getDocumentNumber());
        ubl.setIssueDate(Convert.convertLocalDateToDateString(dispatch.getIssueDate(), DATE_FORMAT));
        ubl.setIssueTime(Convert.convertLocalTimeToTimeString(dispatch.getIssueTime(), HOUR_FORMAT));
        ubl.setDespatchAdviceTypeCode(new DespatchAdviceTypeCode(dispatch.getDocumentType().getCodigo()));
        ubl.setNote(dispatch.getNote());

        // Firma
        ubl.getSignature().setId(dispatch.getDocumentNumber());
        ubl.getSignature().setNote("WWWW.WMC.COM.PE");
        ubl.getSignature().getSignatoryParty().getPartyIdentification().setId(dispatch.getSender().getDocumentNumber());
        ubl.getSignature().getSignatoryParty().getPartyIdentification()
                .setSchemeID(dispatch.getSender().getDocumentType());
        ubl.getSignature().getSignatoryParty().getPartyName().setName(dispatch.getSender().getLegalName());
        ubl.getSignature().getDigitalSignatureAttachment().getExternalReference().setUri("#signatureWMC");

        // Remitente
        ubl.getDespatchSupplierParty().getParty().getPartyIdentification()
                .setId(dispatch.getSender().getDocumentNumber());
        ubl.getDespatchSupplierParty().getParty().getPartyIdentification()
                .setSchemeID(dispatch.getSender().getDocumentNumber());
        ubl.getDespatchSupplierParty().getParty().getPartyLegalEntity()
                .setCompanyId(dispatch.getSender().getDocumentNumber());

        // Destinatario
        ubl.getDeliveryCustomerParty().getParty().getPartyIdentification()
                .setId(dispatch.getReceiver().getDocumentNumber());
        ubl.getDeliveryCustomerParty().getParty().getPartyIdentification()
                .setSchemeID(dispatch.getReceiver().getDocumentType());
        ubl.getDeliveryCustomerParty().getParty().getPartyLegalEntity()
                .setRegistrationName(dispatch.getReceiver().getLegalName());

        // Información de envío
        ubl.getShipment().setId("1");
        ubl.getShipment().getHandlingCode().setValue(dispatch.getReasonForTransferCode().getCodigo());
        ubl.getShipment().setHandlingInstructions(dispatch.getReasonForTransferDescription());
        ubl.getShipment().getGrossWeightMeasure().setValue(dispatch.getTotalGrossWeight());
        ubl.getShipment().getGrossWeightMeasure().setUnitCode(dispatch.getTotalGrossWeightUnit()); // TODO: "KGM"

        // Etapa de envío
        ubl.getShipment().getShipmentStage().getTransportModeCode()
                .setValue(dispatch.getTransportModeCode().getCodigo());
        ubl.getShipment().getShipmentStage().getTransitPeriod()
                .setStartDate(Convert.convertLocalDateToDateString(dispatch.getTransferDate(), DATE_FORMAT));
        if (dispatch.getTransportModeCode().equals(CodigoModalidadTransporteEnum.TRANSPORTE_PUBLICO)) {
            ubl.getShipment().getShipmentStage().getCarrierParty().getPartyIdentification()
                    .setId(dispatch.getCarrier().getDocumentNumber());
            ubl.getShipment().getShipmentStage().getCarrierParty().getPartyIdentification()
                    .setSchemeID(dispatch.getCarrier().getDocumentType());
            ubl.getShipment().getShipmentStage().getCarrierParty().getPartyLegalEntity()
                    .setRegistrationName(dispatch.getCarrier().getLegalName());
            ubl.getShipment().getShipmentStage().getCarrierParty().getPartyLegalEntity()
                    .setCompanyId(dispatch.getCarrier().getMtcNumber());
        }
        if (dispatch.getTransportModeCode().equals(CodigoModalidadTransporteEnum.TRANSPORTE_PRIVADO)) {
            ubl.getShipment().getShipmentStage().getDriverPerson().getId()
                    .setId(dispatch.getDriver().getDocumentNumber());
            ubl.getShipment().getShipmentStage().getDriverPerson().getId()
                    .setSchemeID(dispatch.getDriver().getDocumentType());
            ubl.getShipment().getShipmentStage().getDriverPerson().setFirstName(dispatch.getDriver().getFirstName());
            ubl.getShipment().getShipmentStage().getDriverPerson().setFamilyName(dispatch.getDriver().getLastName());
            ubl.getShipment().getShipmentStage().getDriverPerson().setJobTitle("Principal");
            ubl.getShipment().getShipmentStage().getDriverPerson().getIdentityDocumentReference()
                    .setId(dispatch.getDriver().getLicenseNumber());
        }

        // Direcciones
        // Punto de llegada
        ubl.getShipment().getDelivery().getDeliveryAddress().getId()
                .setId(dispatch.getArrivalAddress().getCodigoUbigeo());
        if (dispatch.getReceiver().getDocumentType().equals("6")) {
            ubl.getShipment().getDelivery().getDeliveryAddress().getAddressTypeCode().setValue("0000");
            ubl.getShipment().getDelivery().getDeliveryAddress().getAddressTypeCode()
                    .setListID(dispatch.getReceiver().getDocumentNumber());
        }
        ubl.getShipment().getDelivery().getDeliveryAddress().getAddressLine()
                .setLine(dispatch.getArrivalAddress().getDireccion());

        // Punto de partida
        ubl.getShipment().getDelivery().getDespatch().getDespatchAddress().getId()
                .setId(dispatch.getDepartureAddress().getCodigoUbigeo());
        ubl.getShipment().getDelivery().getDespatch().getDespatchAddress().getAddressTypeCode().setValue("0000");
        ubl.getShipment().getDelivery().getDespatch().getDespatchAddress().getAddressTypeCode()
                .setListID(dispatch.getSender().getDocumentNumber());
        ubl.getShipment().getDelivery().getDespatch().getDespatchAddress().getAddressLine()
                .setLine(dispatch.getDepartureAddress().getDireccion());

        // Placa del vehículo principal
        if (dispatch.getTransportModeCode().equals(CodigoModalidadTransporteEnum.TRANSPORTE_PRIVADO)) {
            ubl.getShipment().getTransportHandlingUnit().getTransportEquipment()
                    .setId(dispatch.getVehicle().getPlate());
        }

        // Líneas de despacho
        AtomicInteger counter = new AtomicInteger(1);
        ubl.setDespatchLines(dispatch.getDispatchDetails().stream()
            .map(detail -> mapDispatchDetailToDespatchLine(detail, counter.getAndIncrement()))
            .collect(Collectors.toList()));

        return ubl;
    }

    private DespatchLine mapDispatchDetailToDespatchLine(DispatchDetail dispatchDetail, int sequence) {
        DespatchLine line = new DespatchLine();
        line.setId(String.valueOf(sequence));
        line.getDeliveredQuantity().setValue(dispatchDetail.getQuantity().toString());
        line.getDeliveredQuantity().setUnitCode(dispatchDetail.getUnitOfMeasure());
        line.getOrderLineReference().setLineID(String.valueOf(sequence));
        line.getItem().setDescription(dispatchDetail.getDescription());
        line.getItem().getSellersItemIdentification().setId(dispatchDetail.getProductCode());
        return line;
    }
}