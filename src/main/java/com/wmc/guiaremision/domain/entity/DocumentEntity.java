package com.wmc.guiaremision.domain.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "document_id")
  private Integer documentId;

  // Otros campos si es necesario

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity companyEntity;
}