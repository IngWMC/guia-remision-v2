# Paquete REST - Cliente SUNAT GRE

## Descripción

Este paquete implementa la conexión con la API REST de SUNAT para Guías de Remisión Electrónica (GRE) siguiendo los principios de **arquitectura hexagonal**, **patrones SOLID** y **programación funcional**.

## Estructura del Paquete

```
infrastructure/rest/
├── dto/sunat/                    # DTOs para request/response
│   ├── TokenRequest.java         # Solicitud de token
│   ├── TokenResponse.java        # Respuesta de token
│   ├── EnviarComprobanteRequest.java  # Solicitud de envío
│   ├── EnviarComprobanteResponse.java # Respuesta de envío
│   ├── ConsultarComprobanteResponse.java # Respuesta de consulta
│   └── RespuestaSunatDto.java    # DTO genérico (legacy)
├── impl/                         # Implementaciones
│   ├── SunatGreApiClientImpl.java # Implementación principal
│   └── SunatApiClientImpl.java   # Implementación legacy
├── example/                      # Ejemplos de uso
│   └── SunatGreApiClientExample.java
├── SunatGreApiClient.java        # Interfaz principal
└── SunatApiClient.java           # Interfaz legacy
```

## Características Principales

### ✅ Programación Funcional

- Uso de `Optional` para manejo de valores nulos
- Funciones de orden superior (`Function<T, R>`)
- Composición de operaciones
- Manejo funcional de errores

### ✅ Patrones SOLID

- **Single Responsibility**: Cada clase tiene una responsabilidad específica
- **Open/Closed**: Fácil extensión sin modificar código existente
- **Liskov Substitution**: Implementaciones intercambiables
- **Interface Segregation**: Interfaces específicas por funcionalidad
- **Dependency Inversion**: Dependencia de abstracciones

### ✅ Arquitectura Hexagonal

- **Adaptador de Infraestructura**: Conexión con sistema externo (SUNAT)
- **Separación de Responsabilidades**: DTOs, interfaces e implementaciones
- **Inversión de Dependencias**: El dominio no depende de la infraestructura

## Métodos Implementados

### 1. `obtenerToken(TokenRequest request)`

Obtiene un token de autenticación de SUNAT.

**URL**: `https://api-seguridad.sunat.gob.pe/v1/clientessol/{client_id}/oauth2/token/`

**Método**: POST

**Headers**:

```
Content-Type: application/x-www-form-urlencoded
```

**Body**:

```json
{
  "grant_type": "password",
  "scope": "https://api-cpe.sunat.gob.pe",
  "client_id": "{client_id}",
  "client_secret": "{client_secret}",
  "username": "{ruc}{usuario_sol}",
  "password": "{password_sol}"
}
```

### 2. `enviarComprobante(request, token, ruc, codCpe, serie, numero)`

Envía un comprobante GRE a SUNAT.

**URL**: `https://api-cpe.sunat.gob.pe/v1/contribuyente/gem/comprobantes/{ruc}-{codCpe}-{serie}-{numero}`

**Método**: POST

**Headers**:

```
Content-Type: application/json
Authorization: Bearer {token}
```

**Body**:

```json
{
  "archivo": {
    "nomArchivo": "{ruc}-{codCpe}-{serie}-{numero}.zip",
    "arcGreZip": "{base64_del_zip}",
    "hashZip": "{sha256_del_zip}"
  }
}
```

### 3. `consultarComprobante(ticket, token)`

Consulta el estado de un comprobante enviado.

**URL**: `https://api-cpe.sunat.gob.pe/v1/contribuyente/gem/comprobantes/envios/{ticket}`

**Método**: GET

**Headers**:

```
Authorization: Bearer {token}
```

## Uso con Programación Funcional

### Ejemplo Básico

```java
@Service
public class GreService {

    private final SunatGreApiClient sunatClient;

    public void enviarGre() {
        // 1. Crear request de token
        TokenRequest tokenRequest = TokenRequest.builder()
                .clientId("tu-client-id")
                .clientSecret("tu-client-secret")
                .username("12345678901USUARIO")
                .password("tu-password")
                .build();

        // 2. Obtener token usando programación funcional
        TokenResponse tokenResponse = sunatClient.obtenerToken(tokenRequest);

        // 3. Procesar token funcionalmente
        String resultado = sunatClient.procesarToken(
                tokenResponse,
                accessToken -> {
                    log.info("Token válido: {}", accessToken);
                    return "SUCCESS";
                },
                errorMessage -> {
                    log.error("Error token: {}", errorMessage);
                    return "ERROR";
                }
        );
    }
}
```

### Ejemplo Completo

```java
public void enviarGreCompleto() {
    // Crear requests
    EnviarComprobanteRequest request = crearRequestEnvio();
    TokenRequest tokenRequest = crearTokenRequest();

    try {
        // Ejecutar proceso completo
        GreProcessResult result = sunatClient.enviarGreCompleto(
                request, tokenRequest, "12345678901", "09", "T001", "00000001"
        );

        // Procesar resultado funcionalmente
        String resultado = result.procesarResultado(
                success -> {
                    log.info("GRE exitosa: {}", success.getEnvioResponse().getNumTicket());
                    return "EXITOSO";
                },
                error -> {
                    log.error("Error GRE: {}", error);
                    return "ERROR";
                }
        );

    } catch (Exception e) {
        log.error("Error en proceso: {}", e.getMessage());
    }
}
```

## Configuración

### application.yml

```yaml
sunat:
  api:
    security:
      url: https://api-seguridad.sunat.gob.pe/v1/clientessol
    cpe:
      url: https://api-cpe.sunat.gob.pe/v1/contribuyente/gem/comprobantes
    environment: development # development | production
    client:
      id: ${SUNAT_CLIENT_ID:tu-client-id}
      secret: ${SUNAT_CLIENT_SECRET:tu-client-secret}
    username: ${SUNAT_USERNAME:12345678901USUARIO}
    password: ${SUNAT_PASSWORD:tu-password}
```

### Variables de Entorno

```bash
export SUNAT_CLIENT_ID="tu-client-id"
export SUNAT_CLIENT_SECRET="tu-client-secret"
export SUNAT_USERNAME="12345678901USUARIO"
export SUNAT_PASSWORD="tu-password"
```

## Manejo de Errores

### Tipos de Error

1. **Error 500**: Error interno del servidor SUNAT
2. **Error 422**: Error de validación
3. **Error 4xx**: Errores de cliente
4. **Error inesperado**: Errores de red, timeout, etc.

### Ejemplo de Manejo

```java
// Usar programación funcional para manejo de errores
String resultado = sunatClient.procesarEnvio(
        envioResponse,
        ticket -> {
            log.info("Envío exitoso: {}", ticket);
            return "ENVIADO";
        },
        errorMessage -> {
            log.error("Error envío: {}", errorMessage);
            // Implementar lógica de reintento
            return "ERROR_ENVIO";
        }
);
```

## Validaciones

### Códigos de Comprobante

- `09`: Guía de Remisión Remitente – Electrónica
- `31`: Guía de Remisión Transportista – Electrónica

### Series de Comprobante

- Para código `09`: Formato `T###` (ej: T001, T002)
- Para código `31`: Formato `V###` (ej: V001, V002)

### Números de Comprobante

- De 1 a 8 dígitos numéricos

### Estructura de Archivo ZIP

```
RRRRRRRRRRR-TT-SSSS-NNNNNNNN.zip
```

- `RRRRRRRRRRR`: RUC (11 dígitos)
- `TT`: Tipo de comprobante (09 o 31)
- `SSSS`: Serie (T### o V###)
- `NNNNNNNN`: Número (1-8 dígitos)

## Códigos de Respuesta

### Consulta de Comprobante

- `0`: Envío OK
- `98`: En proceso
- `99`: Envío con error

### Indicador CDR

- `1`: Genera CDR
- `0`: No genera CDR

## Testing

### Ejemplo de Test

```java
@SpringBootTest
class SunatGreApiClientTest {

    @Autowired
    private SunatGreApiClient sunatClient;

    @Test
    void testObtenerToken() {
        TokenRequest request = TokenRequest.builder()
                .clientId("test-client")
                .clientSecret("test-secret")
                .username("12345678901TEST")
                .password("test-password")
                .build();

        TokenResponse response = sunatClient.obtenerToken(request);

        assertThat(response).isNotNull();
        // Más aserciones según el caso de prueba
    }
}
```

## Ventajas de la Implementación

### ✅ Sin CompletableFuture

- **Simplicidad**: Código más fácil de entender y mantener
- **Compatibilidad**: Funciona con Java 8 sin dependencias adicionales
- **Debugging**: Más fácil de debuggear operaciones síncronas

### ✅ Programación Funcional

- **Inmutabilidad**: Los objetos no se modifican después de su creación
- **Composición**: Fácil composición de operaciones
- **Manejo de errores**: Manejo elegante de errores sin excepciones
- **Testabilidad**: Funciones puras fáciles de testear

### ✅ Patrones SOLID

- **Mantenibilidad**: Código fácil de mantener y extender
- **Testabilidad**: Fácil de testear con mocks
- **Reutilización**: Componentes reutilizables
- **Flexibilidad**: Fácil cambio de implementaciones

## Migración desde CompletableFuture

### Antes (CompletableFuture)

```java
CompletableFuture<TokenResponse> future = sunatClient.obtenerToken(request);
future.thenCompose(token -> sunatClient.enviarComprobante(...))
      .thenAccept(result -> log.info("Completado"));
```

### Después (Programación Funcional)

```java
TokenResponse token = sunatClient.obtenerToken(request);
if (token.isSuccess()) {
    EnviarComprobanteResponse envio = sunatClient.enviarComprobante(...);
    log.info("Completado");
}
```

## Próximos Pasos

1. **Implementar cache de tokens** para evitar solicitudes innecesarias
2. **Agregar métricas** para monitoreo de performance
3. **Implementar circuit breaker** para manejo de fallos
4. **Agregar validaciones** más robustas
5. **Implementar retry logic** para reintentos automáticos

## Contribución

Para contribuir al proyecto:

1. Seguir los principios SOLID
2. Usar programación funcional cuando sea apropiado
3. Agregar tests unitarios
4. Documentar cambios
5. Seguir las convenciones de código establecidas
