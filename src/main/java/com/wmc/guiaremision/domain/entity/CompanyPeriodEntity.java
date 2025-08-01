package com.wmc.guiaremision.domain.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "company_period")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPeriodEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "company_period_id")
  private Integer companyPeriodId;

  // Otros campos si es necesario

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;
}