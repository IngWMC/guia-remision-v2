package com.wmc.guiaremision.domain.model;

import com.wmc.guiaremision.domain.entity.CompanyEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Series {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "series_id")
  private Integer seriesId;

  // Otros campos si es necesario

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;
}