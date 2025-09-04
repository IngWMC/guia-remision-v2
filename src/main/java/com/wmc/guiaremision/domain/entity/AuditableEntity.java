package com.wmc.guiaremision.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * Abstract base class for auditable entities
 * Provides common audit fields for all entities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public abstract class AuditableEntity {

    /**
     * Record status (A=Active, I=Inactive)
     */
    @Column(name = "estado", length = 1, nullable = false)
    private String status;

    /**
     * ID of the user who created the record
     */
    @Column(name = "usuarioCreacion", nullable = false)
    private Integer userCreate;

    /**
     * Date and time when the record was created
     */
    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime createdAt;

    /**
     * ID of the user who last modified the record
     */
    @Column(name = "usuarioModificacion")
    private Integer userModified;

    /**
     * Date and time of the last modification of the record
     */
    @Column(name = "fechaModificacion")
    private LocalDateTime modifiedAt;

    /**
     * Method to set audit values when creating
     */
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "A"; // Active by default
        }
    }

    /**
     * Method to set audit values when updating
     */
    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
