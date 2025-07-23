package com.wmc.guiaremision.domain.model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends AuditableEntity {
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
  private District district;

  @ManyToOne
  @JoinColumn(name = "identity_document_id", insertable = false, updatable = false)
  private IdentityDocument identityDocument;

  @OneToMany(mappedBy = "company")
  private Set<Parameter> parameters;

  @OneToMany(mappedBy = "company")
  private Set<Document> documents;

  @OneToMany(mappedBy = "company")
  private Set<Series> series;

  @OneToMany(mappedBy = "company")
  private Set<Order> orders;

  @OneToMany(mappedBy = "company")
  private Set<CompanyPeriod> companyPeriods;
}