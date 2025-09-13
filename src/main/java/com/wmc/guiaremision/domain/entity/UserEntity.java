package com.wmc.guiaremision.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "usuario")
public class UserEntity extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "usuarioId")
  private Long userId;

  @Column(name = "empresaId", nullable = false)
  private Integer companyId;

  @Column(name = "nombre", nullable = false)
  private String name;

  @Column(name = "nombreUsuario", nullable = false, unique = true)
  private String userName;

  @Column(name = "contrasena", nullable = false)
  private String password;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "empresaId", insertable = false, updatable = false)
  private CompanyEntity company;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "usuario_roles",
      joinColumns = @JoinColumn(name = "usuarioId"),
      inverseJoinColumns = @JoinColumn(name = "rolId"))
  private Set<RolEntity> roles = new HashSet<>();
}
