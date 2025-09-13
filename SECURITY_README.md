# Implementación de Seguridad JWT - Arquitectura Hexagonal

## Funcionalidades Implementadas

### ✅ 1. Generación de Token

- **Servicio**: `AuthenticateService` con método `authenticate()`
- **Adaptador**: `TokenProviderAdapter` implementa `TokenProvider`
- **Endpoint**: `POST /api/auth/login`
- **Request Body**:
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```
- **Response**:
  ```json
  {
    "requestId": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "data": {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "type": "Bearer",
      "expiresIn": 86400000
    },
    "success": true
  }
  ```

### ✅ 2. Validación de Token

- **Adaptador**: `TokenProviderAdapter` con métodos `validateToken()`, `getUsernameFromToken()`, `getUserFromToken()`
- **Filtro**: `JwtAuthenticationFilter` valida tokens automáticamente
- **Endpoint**: `POST /api/auth/validate`
- **Headers**: `Authorization: Bearer <token>`
- **Response**: `true/false`

### ✅ 3. Protección de Endpoints por Roles

#### Endpoints Públicos (Sin autenticación)

- `/api/auth/**` - Endpoints de autenticación

#### Endpoints Protegidos

- `/api/guias-remision/v2/documento/**` - Requiere roles: `ADMIN` o `USER`
- `/api/admin/**` - Requiere rol: `ADMIN`
- Cualquier otro endpoint - Requiere autenticación

### ✅ 4. Respuestas de Error Personalizadas

#### Error 401 - No Autenticado

```json
{
  "fecha": "2024-01-15 10:30:45",
  "mensaje": "Error de autenticación: Credenciales inválidas",
  "detalle": "Token JWT inválido o expirado"
}
```

#### Error 403 - Sin Permisos

```json
{
  "fecha": "2024-01-15 10:30:45",
  "mensaje": "Acceso denegado",
  "detalle": "No tiene permisos para acceder a este recurso"
}
```

## Arquitectura Hexagonal

### Capa de Dominio

- **`TokenProvider`** - Puerto (interfaz) para gestión de tokens
- **`JwtToken`** - DTO del dominio para tokens
- **`UserEntity`** - Entidad de usuario con roles
- **`RolEntity`** - Entidad de roles

### Capa de Aplicación

- **`AuthenticateService`** - Caso de uso de autenticación
- **`AuthenticateServiceImpl`** - Implementación del servicio

### Capa de Infraestructura

- **`TokenProviderAdapter`** - Adaptador que implementa `TokenProvider` (en `infrastructure/security/`)
- **`JwtAuthenticationFilter`** - Interceptor de validación JWT (en `infrastructure/web/interceptor/`)
- **`SecurityConfig`** - Configuración de Spring Security
- **`AuthController`** - Controlador REST

## Configuración

### Variables de Entorno

```bash
export JWT_SECRET="mi-clave-secreta-muy-segura-123456789"
export JWT_EXPIRATION="86400000"  # 24 horas en milisegundos
```

### Usuarios de Prueba

- **Admin**: `admin` / `admin123` (rol: ADMIN)
- **User**: `user` / `user123` (rol: USER)

## Uso de la API

### 1. Autenticarse

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### 2. Usar el Token

```bash
curl -X POST http://localhost:8080/api/guias-remision/v2/documento \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <tu-token>" \
  -d '{
    "codigoDocumento": "T001-00000001",
    "tipoDocumento": "09",
    "serie": "T001",
    "numero": "00000001"
  }'
```

### 3. Validar Token

```bash
curl -X POST http://localhost:8080/api/auth/validate \
  -H "Authorization: Bearer <tu-token>"
```

## Flujo de Autenticación

1. Cliente envía credenciales a `/api/auth/login`
2. `AuthenticateService` valida credenciales contra la base de datos
3. Si son válidas, `TokenProviderAdapter` genera un JWT token
4. Cliente incluye el token en el header `Authorization: Bearer <token>`
5. `JwtAuthenticationFilter` valida el token en cada request
6. Spring Security autoriza según los roles configurados

## Seguridad

- **BCrypt** para encriptación de contraseñas (fuerza 12)
- **JWT** con algoritmo HS256
- **Tokens con expiración** configurable
- **Validación de roles** en endpoints
- **Manejo seguro de errores** sin exposición de información sensible
- **Arquitectura hexagonal** para separación de responsabilidades
