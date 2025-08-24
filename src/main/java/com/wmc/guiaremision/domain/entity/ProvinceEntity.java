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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity that represents a province
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "provincia")
public class ProvinceEntity extends AuditableEntity {

    /**
     * Unique ID of the province
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provinciaId")
    private Integer provinceId;

    /**
     * Province code
     */
    @Column(name = "codigo", nullable = false)
    private String code;

    /**
     * Province name
     */
    @Column(name = "nombre", nullable = false)
    private String name;

    /**
     * ID of the department to which the province belongs
     */
    @Column(name = "departamentoId", nullable = false)
    private Integer departmentId;

    // Relationships

    /**
     * Department to which the province belongs
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamentoId", insertable = false, updatable = false)
    private DepartmentEntity department;

    /**
     * Districts that belong to this province
     */
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DistrictEntity> districts = new HashSet<>();
}
