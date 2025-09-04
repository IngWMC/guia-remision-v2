package com.wmc.guiaremision.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity that represents an identity document type
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "createdAt", "modifiedAt" })
@Entity
@Table(name = "tipoDocumentoIdentidad")
public class IdentityDocumentTypeEntity extends AuditableEntity {

    /**
     * Unique ID of the identity document type
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipoDocumentoIdentidadId")
    private Integer identityDocumentTypeId;

    /**
     * Identity document type code
     */
    @Column(name = "codigo", nullable = false)
    private String code;

    /**
     * Identity document type name
     */
    @Column(name = "nombre", nullable = false)
    private String name;

    /**
     * Companies that use this type of identity document
     */
    @OneToMany(mappedBy = "identityDocumentType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CompanyEntity> companies = new HashSet<>();
}