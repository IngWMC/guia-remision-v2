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
 * Entity that represents a country
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pais")
public class CountryEntity extends AuditableEntity {

    /**
     * Unique ID of the country
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paisId")
    private Integer countryId;

    /**
     * Country code
     */
    @Column(name = "codigo", nullable = false)
    private String code;

    /**
     * Country name
     */
    @Column(name = "nombre", nullable = false)
    private String name;

    /**
     * Departments that belong to this country
     */
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DepartmentEntity> departments = new HashSet<>();
}
