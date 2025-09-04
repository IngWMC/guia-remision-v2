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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity that represents a department
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "departamento")
public class DepartmentEntity extends AuditableEntity {

    /**
     * Unique ID of the department
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departamentoId")
    private Integer departmentId;

    /**
     * Department code
     */
    @Column(name = "codigo", nullable = false)
    private String code;

    /**
     * Department name
     */
    @Column(name = "nombre")
    private String name;

    /**
     * ID of the country to which the department belongs
     */
    @Column(name = "paisId", nullable = false)
    private Integer countryId;

    // Relationships

    /**
     * Country to which the department belongs
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paisId", insertable = false, updatable = false)
    private CountryEntity country;

    /**
     * Provinces that belong to this department
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProvinceEntity> provinces = new HashSet<>();
}
