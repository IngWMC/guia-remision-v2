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
 * Entity that represents a district
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "distrito")
public class DistrictEntity extends AuditableEntity {

    /**
     * Unique ID of the district
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distritoId")
    private Integer districtId;

    /**
     * District code
     */
    @Column(name = "codigo", nullable = false)
    private String code;

    /**
     * District name
     */
    @Column(name = "nombre", nullable = false)
    private String name;

    /**
     * ID of the province to which the district belongs
     */
    @Column(name = "provinciaId", nullable = false)
    private Integer provinceId;

    // Relationships

    /**
     * Province to which the district belongs
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provinciaId", insertable = false, updatable = false)
    private ProvinceEntity province;

    /**
     * Companies located in this district
     */
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CompanyEntity> companies = new HashSet<>();
}
