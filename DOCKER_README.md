# Docker Compose para Guía de Remisión

Este documento describe cómo usar Docker Compose para ejecutar MySQL y phpMyAdmin en el proyecto de guías de remisión.

## 🐳 Servicios Incluidos

### MySQL 8.0
- **Puerto**: 3306
- **Base de datos**: `eteamfact`
- **Usuario root**: `root` / `password`
- **Usuario adicional**: `wmc_user` / `wmc_password`

### phpMyAdmin (Opcional)
- **Puerto**: 8081
- **URL**: http://localhost:8081
- **Usuario**: `root` / `password`

## 🚀 Inicio Rápido

### 1. Iniciar los servicios
```bash
# Iniciar todos los servicios
docker-compose up -d

# Ver logs en tiempo real
docker-compose up -d && docker-compose logs -f
```

### 2. Verificar que los servicios estén funcionando
```bash
# Ver estado de los contenedores
docker-compose ps

# Ver logs de MySQL
docker-compose logs mysql

# Ver logs de phpMyAdmin
docker-compose logs phpmyadmin
```

### 3. Conectar a la base de datos
```bash
# Conectar directamente a MySQL
docker-compose exec mysql mysql -u root -p

# O usar el usuario wmc_user
docker-compose exec mysql mysql -u wmc_user -p eteamfact
```

## 📊 Acceso a phpMyAdmin

1. Abrir el navegador en: http://localhost:8081
2. Usar las credenciales:
   - **Servidor**: `mysql`
   - **Usuario**: `root`
   - **Contraseña**: `password`

## ⚙️ Configuración

### Variables de Entorno
El archivo `docker.env` contiene todas las variables de entorno necesarias:

```bash
# MySQL
MYSQL_ROOT_PASSWORD=password
MYSQL_DATABASE=eteamfact
MYSQL_USER=wmc_user
MYSQL_PASSWORD=wmc_password

# phpMyAdmin
PMA_PORT=8081
```

### Volúmenes
- **mysql_data**: Persistencia de datos de MySQL
- **init-scripts**: Scripts de inicialización automática

### Redes
- **guia-remision-network**: Red interna para comunicación entre servicios

## 🔧 Comandos Útiles

### Gestión de Contenedores
```bash
# Iniciar servicios
docker-compose up -d

# Detener servicios
docker-compose down

# Reiniciar servicios
docker-compose restart

# Ver logs
docker-compose logs -f mysql
docker-compose logs -f phpmyadmin

# Ejecutar comandos en el contenedor
docker-compose exec mysql mysql -u root -p
docker-compose exec phpmyadmin sh
```

### Gestión de Base de Datos
```bash
# Crear backup
docker-compose exec mysql mysqldump -u root -p eteamfact > backup.sql

# Restaurar backup
docker-compose exec -T mysql mysql -u root -p eteamfact < backup.sql

# Ver tablas
docker-compose exec mysql mysql -u root -p -e "USE eteamfact; SHOW TABLES;"
```

### Limpieza
```bash
# Eliminar contenedores y volúmenes
docker-compose down -v

# Eliminar solo contenedores
docker-compose down

# Eliminar imágenes
docker-compose down --rmi all
```

## 📁 Estructura de Archivos

```
guia-remision-v2/
├── docker-compose.yml          # Configuración de servicios
├── docker.env                  # Variables de entorno
├── init-scripts/               # Scripts de inicialización
│   └── 01-init-database.sql   # Script principal de BD
├── src/main/resources/
│   └── application.yml         # Configuración de Spring Boot
└── DOCKER_README.md           # Este archivo
```

## 🔄 Integración con Spring Boot

### Configuración de application.yml
El archivo `application.yml` ya está configurado para conectarse a MySQL:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/eteamfact
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Ejecutar la Aplicación
```bash
# Con Docker Compose ejecutándose
./mvnw spring-boot:run

# O con Maven
mvn spring-boot:run
```

## 🛠️ Solución de Problemas

### Puerto 3306 ocupado
```bash
# Ver qué está usando el puerto
sudo lsof -i :3306

# Detener MySQL local si está ejecutándose
sudo service mysql stop
```

### Error de conexión
```bash
# Verificar que el contenedor esté ejecutándose
docker-compose ps

# Ver logs de MySQL
docker-compose logs mysql

# Reiniciar el servicio
docker-compose restart mysql
```

### Problemas de permisos
```bash
# Cambiar permisos de volúmenes
sudo chown -R $USER:$USER ./init-scripts
```

## 📈 Monitoreo

### Ver uso de recursos
```bash
# Ver estadísticas de contenedores
docker stats

# Ver uso de disco
docker system df
```

### Logs detallados
```bash
# Ver logs de MySQL con timestamps
docker-compose logs -f --timestamps mysql

# Ver logs de los últimos 100 líneas
docker-compose logs --tail=100 mysql
```

## 🔒 Seguridad

### Cambiar contraseñas por defecto
1. Editar `docker.env`
2. Cambiar las contraseñas
3. Reiniciar servicios: `docker-compose down && docker-compose up -d`

### Configurar red privada
El docker-compose ya incluye una red privada para comunicación entre servicios.

## 📝 Notas Importantes

- Los datos de MySQL se persisten en el volumen `mysql_data`
- Los scripts en `init-scripts/` se ejecutan automáticamente al crear el contenedor
- phpMyAdmin es opcional y puede ser removido del docker-compose.yml si no se necesita
- La aplicación Spring Boot debe ejecutarse fuera de Docker (por ahora) 