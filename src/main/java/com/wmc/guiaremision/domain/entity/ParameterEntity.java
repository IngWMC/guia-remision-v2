package com.wmc.guiaremision.domain.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "parameter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "parameter_id")
  private Integer parameterId;

  // Otros campos si es necesario

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;
}