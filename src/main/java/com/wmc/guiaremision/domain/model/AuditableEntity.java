package com.wmc.guiaremision.domain.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class AuditableEntity {
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // Otros campos de auditoría si necesitas
}