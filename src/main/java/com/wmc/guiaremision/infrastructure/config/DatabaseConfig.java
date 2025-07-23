package com.wmc.guiaremision.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuración de base de datos en la capa de infraestructura
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.wmc.guiaremision.infrastructure.repository")
@EnableTransactionManagement
public class DatabaseConfig {

  // Configuración adicional de base de datos si es necesaria
  // Por ejemplo: DataSource, EntityManagerFactory, etc.
}