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
@Table(name = "rol")
public class RolEntity extends AuditableEntity  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rolId")
  private Long rolId;

  @Column(name = "nombre", nullable = false, unique = true)
  private String nombre;

  @Column(name = "descripcion")
  private String descripcion;

  @ManyToMany(mappedBy = "roles")
  private Set<UserEntity> usuarios = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(
      name = "rol_menus",
      joinColumns = @JoinColumn(name = "rolId"),
      inverseJoinColumns = @JoinColumn(name = "menuId")
  )
  private Set<MenuEntity> menus = new HashSet<>();
}
