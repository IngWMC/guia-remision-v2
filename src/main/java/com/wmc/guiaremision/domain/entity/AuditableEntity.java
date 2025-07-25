package com.wmc.guiaremision.domain.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AuditableEntity {
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // Otros campos de auditoría si necesitas
}