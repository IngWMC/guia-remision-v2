package com.wmc.guiaremision.domain.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "document_id")
  private Integer documentId;

  // Otros campos si es necesario

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;
}