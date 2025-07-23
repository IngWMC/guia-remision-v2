package com.wmc.guiaremision.domain.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "dispatch")
public class Dispatch {
    @Id
    @Column(name = "document_id", nullable = false, length = 50)
    private String documentId;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "issue_time")
    private LocalTime issueTime;

    @Column(name = "document_type", length = 10)
    private String documentType;

    @Column(name = "document_number", length = 20)
    private String documentNumber;

    @Column(name = "sender_ruc", length = 20)
    private String senderRuc;

    @Column(name = "recipient_ruc", length = 20)
    private String recipientRuc;

    @Column(name = "carrier_ruc", length = 20)
    private String carrierRuc;

    @Column(name = "carrier_name", length = 100)
    private String carrierName;

    @Column(name = "vehicle_plate_number", length = 20)
    private String vehiclePlateNumber;

    @Column(name = "driver_document_number", length = 20)
    private String driverDocumentNumber;

    @Column(name = "total_gross_weight")
    private BigDecimal totalGrossWeight;

    @Column(name = "transfer_mode", length = 10)
    private String transferMode;

    @Column(name = "transfer_start_date")
    private LocalDate transferStartDate;

    // Puedes agregar más campos clave según necesidad
} 