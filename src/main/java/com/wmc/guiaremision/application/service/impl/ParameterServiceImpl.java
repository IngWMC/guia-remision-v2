package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.CompanyService;
import com.wmc.guiaremision.application.service.ParameterService;
import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.entity.ParameterEntity;
import com.wmc.guiaremision.domain.repository.ParameterRepository;
import com.wmc.guiaremision.domain.spi.security.EncryptorSecurity;
import com.wmc.guiaremision.infrastructure.config.property.StorageProperty;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.shared.common.enums.PathFileEnum;
import com.wmc.guiaremision.shared.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de gestión de parámetros de configuración.
 * <p>
 * Maneja la lógica de negocio relacionada con los parámetros de configuración
 * de las empresas, incluyendo la creación automática de estructura de
 * directorios y persistencia de datos.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

  private final EncryptorSecurity encryptorSecurity;
  private final ParameterRepository parameterRepository;
  private final CompanyService companyService;
  private final StorageProperty storageProperty;

  /**
   * Guarda los parámetros de configuración de una empresa.
   * <p>
   * Este método realiza las siguientes operaciones:
   * </p>
   * <ol>
   * <li>Busca la empresa por su RUC</li>
   * <li>Construye y crea la estructura de directorios necesaria</li>
   * <li>Configura las rutas y metadatos en la entidad de parámetros</li>
   * <li>Persiste los parámetros en la base de datos</li>
   * </ol>
   *
   * @param parameterEntity entidad con los datos del parámetro (nombre de logo,
   *                        certificado, contraseña, etc.)
   * @param rucCompany      RUC de la empresa de 11 dígitos
   * @return la entidad de parámetro guardada con el ID generado
   * @throws BadRequestException si no se encuentra la empresa con el RUC
   *                             especificado
   */
  @Override
  public ParameterEntity save(ParameterEntity parameterEntity, String rucCompany) {
    CompanyEntity company = this.companyService.findByIdentityDocumentNumber(rucCompany);

    boolean existsParameter = this.parameterRepository
        .existsByCompanyId(company.getCompanyId());
    if (existsParameter) {
      throw new BadRequestException("El parámetro para la empresa ya se encuentra registrado");
    }

    String[] paths = this.createDirectories(rucCompany);
    this.configureParameterEntity(parameterEntity, paths, company);

    return this.parameterRepository.save(parameterEntity);
  }

  /**
   * Busca los parámetros de configuración asociados a una empresa.
   * <p>
   * Retorna los parámetros configurados para la empresa especificada,
   * incluyendo rutas de archivos y datos del certificado digital.
   * </p>
   *
   * @param companyId identificador único de la empresa
   * @return la entidad de parámetro encontrada
   * @throws BadRequestException si no se encuentran parámetros para la empresa
   */
  @Override
  public ParameterEntity findByCompanyId(Integer companyId) {
    return this.parameterRepository
        .findByCompanyId(companyId)
        .orElseThrow(() -> new BadRequestException("No se encontró el parámetro para la empresa"));
  }

  /**
   * Crea la estructura completa de directorios para una empresa.
   * <p>
   * Construye las rutas necesarias usando el RUC de la empresa y crea
   * físicamente los directorios en el sistema de archivos para almacenar:
   * </p>
   * <ul>
   * <li>XML sin firmar</li>
   * <li>XML firmados</li>
   * <li>Archivos PDF</li>
   * <li>Archivos CDR de SUNAT</li>
   * <li>Certificados digitales</li>
   * <li>Logos de la empresa</li>
   * </ul>
   *
   * @param rucCompany RUC de la empresa de 11 dígitos
   * @return arreglo con las rutas construidas en orden: [0] XML sin firmar,
   *         [1] XML firmados, [2] PDF, [3] CDR, [4] Certificados, [5] Logos
   */
  private String[] createDirectories(String rucCompany) {
    String basePath = this.storageProperty.getBasePath();

    String[] paths = {
        Util.buildPath(PathFileEnum.PATH_XML_WITHOUT_SIGNATURE.getPath(), rucCompany),
        Util.buildPath(PathFileEnum.PATH_XML_WITH_SIGNATURE.getPath(), rucCompany),
        Util.buildPath(PathFileEnum.PATH_PDF.getPath(), rucCompany),
        Util.buildPath(PathFileEnum.PATH_CDR.getPath(), rucCompany),
        Util.buildPath(PathFileEnum.PATH_CERTIFICATE.getPath(), rucCompany),
        Util.buildPath(PathFileEnum.PATH_LOGO.getPath(), rucCompany)
    };

    Util.createDirectories(basePath, paths);

    return paths;
  }

  /**
   * Configura la entidad de parámetros con las rutas de directorios y
   * metadatos.
   * <p>
   * Establece todas las rutas de almacenamiento en la entidad de parámetros
   * y configura los campos de auditoría (usuario creador) y la relación con
   * la empresa.
   * </p>
   *
   * @param parameterEntity entidad de parámetros a configurar
   * @param paths           arreglo con las rutas de directorios creados
   * @param company         entidad de la empresa asociada
   */
  private void configureParameterEntity(ParameterEntity parameterEntity, String[] paths,
      CompanyEntity company) {
    String encryptCertificatePassword = this.encryptorSecurity
        .encrypt(parameterEntity.getCertificatePassword());

    parameterEntity.setUnsignedXmlFilePath(paths[0]);
    parameterEntity.setSignedXmlFilePath(paths[1]);
    parameterEntity.setPdfFilePath(paths[2]);
    parameterEntity.setCdrFilePath(paths[3]);
    parameterEntity.setCertificateFilePath(paths[4]);
    parameterEntity.setLogoPath(paths[5]);

    parameterEntity.setUserCreate(Util.getCurrentUsername());
    parameterEntity.setCompanyId(company.getCompanyId());
    parameterEntity.setCertificatePassword(encryptCertificatePassword);
  }

}
