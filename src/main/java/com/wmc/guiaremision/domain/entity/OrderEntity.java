package com.wmc.guiaremision.domain.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Integer orderId;

  // Otros campos si es necesario

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity companyEntity;
}