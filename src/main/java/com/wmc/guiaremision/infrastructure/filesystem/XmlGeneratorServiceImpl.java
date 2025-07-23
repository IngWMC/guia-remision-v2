package com.wmc.guiaremision.infrastructure.filesystem;

import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.DispatchDetail;
import com.wmc.guiaremision.domain.service.XmlGeneratorService;
import com.wmc.guiaremision.infrastructure.*;
import com.wmc.guiaremision.ubl.document.DespatchAdvice;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.util.stream.Collectors;

@Service
public class XmlGeneratorServiceImpl implements XmlGeneratorService {
    @Override
    public Dispatch generateXml(Dispatch dispatch) {
        try {
            // Mapear Dispatch a UblDespatchAdvice
            DespatchAdvice ublDespatchAdvice = mapDispatchToUbl(dispatch);
            
            // Serializar UblDespatchAdvice a XML
            JAXBContext context = JAXBContext.newInstance(DespatchAdvice.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            StringWriter sw = new StringWriter();
            marshaller.marshal(ublDespatchAdvice, sw);
            
            dispatch.setXmlData(sw.toString());
            return dispatch;
        } catch (JAXBException e) {
            throw new RuntimeException("Error al generar el XML de la guía de remisión", e);
        }
    }
    
    private DespatchAdvice mapDispatchToUbl(Dispatch dispatch) {
        DespatchAdvice ubl = new DespatchAdvice();
        
        // Datos básicos
        ubl.setId(dispatch.getDocumentId());
        ubl.setIssueDate(dispatch.getIssueDate());
        ubl.setIssueTime(dispatch.getIssueTime());
        ubl.setDespatchAdviceTypeCode(dispatch.getDocumentType());
        ubl.setNote(dispatch.getNote());
        
        // Firma
        ubl.getSignature().setId(dispatch.getDocumentId());
        ubl.getSignature().getSignatoryParty().getPartyIdentification().getId().setValue(dispatch.getSender().getDocumentNumber());
        ubl.getSignature().getSignatoryParty().getPartyName().setName(dispatch.getSender().getLegalName());
        ubl.getSignature().getDigitalSignatureAttachment().getExternalReference().setUri(
            dispatch.getSender().getDocumentNumber() + "-" + dispatch.getDocumentId()
        );
        
        // Remitente
        ubl.getDespatchSupplierParty().getCustomerAssignedAccountId().setValue(dispatch.getSender().getDocumentNumber());
        ubl.getDespatchSupplierParty().getCustomerAssignedAccountId().setSchemeId(dispatch.getSender().getDocumentType());
        ubl.getDespatchSupplierParty().getParty().getPartyLegalEntity().setRegistrationName(dispatch.getSender().getLegalName());
        
        // Destinatario
        ubl.getDeliveryCustomerParty().getCustomerAssignedAccountId().setValue(dispatch.getRecipient().getDocumentNumber());
        ubl.getDeliveryCustomerParty().getCustomerAssignedAccountId().setSchemeId(dispatch.getRecipient().getDocumentType());
        ubl.getDeliveryCustomerParty().getParty().getPartyLegalEntity().setRegistrationName(dispatch.getRecipient().getLegalName());
        
        // Tercero (si existe)
        if (dispatch.getThirdParty() != null) {
            ubl.getSellerSupplierParty().getCustomerAssignedAccountId().setValue(dispatch.getThirdParty().getDocumentNumber());
            ubl.getSellerSupplierParty().getCustomerAssignedAccountId().setSchemeId(dispatch.getThirdParty().getDocumentType());
            ubl.getSellerSupplierParty().getParty().getPartyLegalEntity().setRegistrationName(dispatch.getThirdParty().getLegalName());
        }
        
        // Documento relacionado
        if (dispatch.getRelatedDocument() != null) {
            ubl.getAdditionalDocumentReference().setId(dispatch.getRelatedDocument().getDocumentNumber());
            ubl.getAdditionalDocumentReference().setDocumentTypeCode(dispatch.getRelatedDocument().getDocumentType());
        }
        
        // Guía de baja
        if (dispatch.getCancellationGuide() != null) {
            ubl.getOrderReference().setId(dispatch.getCancellationGuide().getDocumentNumber());
            ubl.getOrderReference().getOrderTypeCode().setName("Guia de Remision");
            ubl.getOrderReference().getOrderTypeCode().setValue(dispatch.getCancellationGuide().getDocumentType());
        }
        
        // Información de envío
        ubl.getShipment().setId(dispatch.getShipmentId());
        ubl.getShipment().setHandlingCode(dispatch.getTransferReasonCode());
        ubl.getShipment().setInformation(dispatch.getTransferDescription());
        ubl.getShipment().getGrossWeightMeasure().setValue(dispatch.getTotalGrossWeight());
        ubl.getShipment().getGrossWeightMeasure().setUnitCode("KGM");
        ubl.getShipment().setSplitConsignmentIndicator(dispatch.isTransshipment());
        
        // Direcciones
        ubl.getShipment().getOriginAddress().setId(dispatch.getDepartureAddress().getUbigeo());
        ubl.getShipment().getOriginAddress().setStreetName(dispatch.getDepartureAddress().getFullAddress());
        
        ubl.getShipment().getDelivery().getDeliveryAddress().setId(dispatch.getArrivalAddress().getUbigeo());
        ubl.getShipment().getDelivery().getDeliveryAddress().setStreetName(dispatch.getArrivalAddress().getFullAddress());
        
        // Etapa de envío
        if (!ubl.getShipment().getShipmentStages().isEmpty()) {
            var stage = ubl.getShipment().getShipmentStages().get(0);
            stage.setTransportModeCode(dispatch.getTransferMode());
            stage.getTransitPeriod().setStartDate(dispatch.getTransferStartDate());
            
            if (dispatch.getCarrierRuc() != null) {
                stage.getCarrierParty().getPartyIdentification().getId().setValue(dispatch.getCarrierRuc());
                stage.getCarrierParty().getPartyName().setName(dispatch.getCarrierName());
            }
            
            stage.getTransportMeans().getRoadTransport().setLicensePlateId(dispatch.getVehiclePlateNumber());
            stage.getDriverPerson().getId().setValue(dispatch.getDriverDocumentNumber());
            stage.getDriverPerson().getId().setSchemeId("1");
        }
        
        // Líneas de despacho
        ubl.setDespatchLines(dispatch.getGoodsToTransport().stream()
            .map(this::mapShippingDetailToDespatchLine)
            .collect(Collectors.toList()));
        
        return ubl;
    }
    
    private DespatchLine mapShippingDetailToDespatchLine(DispatchDetail detail) {
        DespatchLine line = new DespatchLine();
        line.setId(detail.getCode());
        line.getDeliveredQuantity().setValue(detail.getQuantity());
        line.getDeliveredQuantity().setUnitCode(detail.getUnitOfMeasure());
        line.getItem().setName(detail.getDescription());
        line.getItem().getSellersItemIdentification().setId(detail.getCode());
        return line;
    }
} 