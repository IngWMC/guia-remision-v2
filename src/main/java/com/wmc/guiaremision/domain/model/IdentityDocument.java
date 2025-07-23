package com.wmc.guiaremision.domain.model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "identity_document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityDocument {
  @Id
  @Column(name = "identity_document_id")
  private Integer identityDocumentId;

  @Column(name = "description")
  private String description;

  // Otros campos si es necesario

  @OneToMany(mappedBy = "identityDocument")
  private Set<Company> companies;
}