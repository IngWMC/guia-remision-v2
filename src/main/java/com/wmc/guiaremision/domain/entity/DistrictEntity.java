package com.wmc.guiaremision.domain.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "district")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictEntity {
  @Id
  @Column(name = "district_id")
  private Integer districtId;

  @Column(name = "name")
  private String name;

  // Otros campos si es necesario

  @OneToMany(mappedBy = "district")
  private Set<CompanyEntity> companies;
}