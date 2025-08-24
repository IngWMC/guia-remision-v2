package com.wmc.guiaremision.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity that represents a SUNAT status
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estado_sunat")
public class SunatStatusEntity extends AuditableEntity {

    /**
     * Unique ID of the SUNAT status
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estadoSunatId")
    private Integer sunatStatusId;

    /**
     * SUNAT status code
     */
    @Column(name = "nombreEstado")
    private String statusName;

    /**
     * SUNAT status description
     */
    @Column(name = "descripcion")
    private String description;

    /**
     * Documents that have this SUNAT status
     */
    @OneToMany(mappedBy = "sunatStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DocumentEntity> documents = new HashSet<>();
}
