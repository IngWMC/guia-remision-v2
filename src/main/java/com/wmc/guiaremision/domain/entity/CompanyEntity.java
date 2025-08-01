package com.wmc.guiaremision.domain.entity;

import com.wmc.guiaremision.domain.model.Series;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
@EqualsAndHashCode(callSuper = true)
public class CompanyEntity extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "company_id")
  private Integer companyId;

  @Column(name = "district_id", nullable = false)
  private Integer districtId;

  @Column(name = "identity_document_id", nullable = false)
  private Integer identityDocumentId;

  @Column(name = "parent_company_id")
  private Integer parentCompanyId;

  @Column(name = "business_name", nullable = false)
  private String businessName;

  @Column(name = "ruc", nullable = false)
  private String ruc;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "phone")
  private String phone;

  @Column(name = "alias_name")
  private String aliasName;

  @Column(name = "sender_email")
  private String senderEmail;

  @Column(name = "smtp_server")
  private String smtpServer;

  @Column(name = "smtp_port")
  private String smtpPort;

  @Column(name = "ssl_security")
  private Boolean sslSecurity;

  @Column(name = "server_user")
  private String serverUser;

  @Column(name = "server_password")
  private String serverPassword;

  @Column(name = "email")
  private String email;

  @Column(name = "user")
  private String user;

  @Column(name = "password")
  private String password;

  @Column(name = "sunat_user")
  private String sunatUser;

  @Column(name = "sunat_password")
  private String sunatPassword;

  @Column(name = "signature_password")
  private String signaturePassword;

  @Column(name = "online_mode")
  private Boolean onlineMode;

  @ManyToOne
  @JoinColumn(name = "district_id", insertable = false, updatable = false)
  private DistrictEntity district;

  @ManyToOne
  @JoinColumn(name = "identity_document_id", insertable = false, updatable = false)
  private IdentityDocumentEntity identityDocument;

  @OneToMany(mappedBy = "company")
  private Set<ParameterEntity> parameterEntities;

  @OneToMany(mappedBy = "company")
  private Set<DocumentEntity> documentEntities;

  @OneToMany(mappedBy = "company")
  private Set<Series> series;

  @OneToMany(mappedBy = "company")
  private Set<OrderEntity> orderEntities;

  @OneToMany(mappedBy = "company")
  private Set<CompanyPeriodEntity> companyPeriodEntities;
}