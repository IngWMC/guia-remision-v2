package com.wmc.guiaremision.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
}
