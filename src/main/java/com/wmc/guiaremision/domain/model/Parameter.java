package com.wmc.guiaremision.domain.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "parameter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "parameter_id")
  private Integer parameterId;

  // Otros campos si es necesario

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;
}