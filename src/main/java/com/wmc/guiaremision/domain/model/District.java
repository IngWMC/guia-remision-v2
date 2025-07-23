package com.wmc.guiaremision.domain.model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "district")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
  @Id
  @Column(name = "district_id")
  private Integer districtId;

  @Column(name = "name")
  private String name;

  // Otros campos si es necesario

  @OneToMany(mappedBy = "district")
  private Set<Company> companies;
}