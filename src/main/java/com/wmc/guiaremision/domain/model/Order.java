package com.wmc.guiaremision.domain.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Integer orderId;

  // Otros campos si es necesario

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;
}