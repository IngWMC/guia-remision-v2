package com.wmc.guiaremision.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "menu")
public class MenuEntity extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menuId")
  private Long menuId;

  //@Column(name = "menuPadreId")
  //private Long parentMenuId;

  @Column(name = "nombre", nullable = false)
  private String name;

  @Column(name = "ruta", nullable = false)
  private String path;

  @Column(name = "icono")
  private String icon;

  @Column(name = "orden")
  private Integer order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menuPadreId")
  private MenuEntity parentMenu;

  @OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY)
  private Set<MenuEntity> subMenus = new HashSet<>();

  @ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY)
  private Set<RolEntity> roles = new HashSet<>();
}
