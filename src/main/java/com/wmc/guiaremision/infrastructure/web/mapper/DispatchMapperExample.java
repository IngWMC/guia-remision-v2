package com.wmc.guiaremision.infrastructure.web.mapper;

import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.infrastructure.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Chofer;
import com.wmc.guiaremision.infrastructure.web.dto.shared.DetalleGuia;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Direccion;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Emisor;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Receptor;
import com.wmc.guiaremision.infrastructure.web.dto.shared.Vehiculo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Ejemplo de uso del DispatchMapper para demostrar cómo convertir
 * un CrearGuiaRemisionDto a un objeto Dispatch del dominio.
 * 
 * <p>
 * Este ejemplo muestra cómo utilizar el mapper en un contexto real
 * de la aplicación.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DispatchMapperExample {

  private final GuiaRemisionMapper guiaRemisionMapper;

  /**
   * Ejemplo de cómo usar el mapper para convertir un DTO a un objeto del dominio.
   */
  public void ejemploUsoMapper() {
    // Crear un DTO de ejemplo
    CrearGuiaRemisionDto dto = crearDtoEjemplo();

    // Convertir usando el mapper
    Dispatch dispatch = guiaRemisionMapper.mapperCrearGuiaRemisionDtotoDispatch(dto);

    log.info("DTO convertido exitosamente a Dispatch: {}", dispatch.getDocumentNumber());
  }

  /**
   * Crea un DTO de ejemplo con datos válidos para pruebas.
   */
  private CrearGuiaRemisionDto crearDtoEjemplo() {
    CrearGuiaRemisionDto dto = new CrearGuiaRemisionDto();

    // Datos básicos del documento
    dto.setSerieDocumento("T001");
    dto.setCorrelativoDocumento("00000001");
    dto.setFechaEmision("2024-01-15");
    dto.setHoraEmision("10:30:00");
    dto.setTipoDocumento("09"); // Guía de remisión remitente
    dto.setGlosa("Guía de remisión de mercancías");

    // Datos de transporte
    dto.setCodigoModalidadTransporte("01"); // Transporte público
    dto.setCodigoMotivoTraslado("01"); // Venta
    dto.setDescripcionMotivoTraslado("Venta de mercancías");
    dto.setFechaTraslado("2024-01-15");
    dto.setCodigoPuerto("");
    dto.setIndicaTransbordo(false);
    dto.setUnidadPesoTotal("KGM");
    dto.setPesoTotal(new BigDecimal("100.50"));
    dto.setNumeroBultos(5);
    dto.setNumeroContenedor(1);

    // Emisor
    Emisor emisor = new Emisor();
    emisor.setRuc("20123456789");
    emisor.setRazonSocial("EMPRESA EJEMPLO S.A.C.");
    emisor.setDireccion("Av. Principal 123");
    emisor.setDistrito("Lima");
    emisor.setProvincia("Lima");
    emisor.setDepartamento("Lima");
    dto.setEmisor(emisor);

    // Receptor
    Receptor receptor = new Receptor();
    receptor.setRuc("20123456790");
    receptor.setRazonSocial("CLIENTE EJEMPLO S.A.C.");
    receptor.setDireccion("Av. Secundaria 456");
    receptor.setDistrito("Miraflores");
    receptor.setProvincia("Lima");
    receptor.setDepartamento("Lima");
    dto.setReceptor(receptor);

    // Direcciones
    Direccion direccionPartida = new Direccion();
    direccionPartida.setDireccion("Av. Principal 123");
    direccionPartida.setDistrito("Lima");
    direccionPartida.setProvincia("Lima");
    direccionPartida.setDepartamento("Lima");
    direccionPartida.setCodigoUbigeo("150101");
    dto.setDireccionPartida(direccionPartida);

    Direccion direccionLlegada = new Direccion();
    direccionLlegada.setDireccion("Av. Secundaria 456");
    direccionLlegada.setDistrito("Miraflores");
    direccionLlegada.setProvincia("Lima");
    direccionLlegada.setDepartamento("Lima");
    direccionLlegada.setCodigoUbigeo("150122");
    dto.setDireccionLlegada(direccionLlegada);

    // Chofer
    Chofer chofer = new Chofer();
    chofer.setNumeroLicencia("A12345678");
    chofer.setNombre("Juan");
    chofer.setApellido("Pérez");
    chofer.setDni("12345678");
    dto.setChofer(chofer);

    // Vehículo
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setPlaca("ABC-123");
    vehiculo.setMarca("Toyota");
    vehiculo.setModelo("Hilux");
    vehiculo.setColor("Blanco");
    vehiculo.setAnio("2020");
    dto.setVehiculo(vehiculo);

    // Detalles de la guía
    DetalleGuia detalle1 = new DetalleGuia();
    detalle1.setDescripcion("Producto ejemplo 1");
    detalle1.setCantidad(10);
    detalle1.setUnidadMedida("UND");
    detalle1.setPeso(new BigDecimal("20.50"));
    detalle1.setCodigoProducto("PROD001");
    detalle1.setCodigoSunat("12345678");

    DetalleGuia detalle2 = new DetalleGuia();
    detalle2.setDescripcion("Producto ejemplo 2");
    detalle2.setCantidad(5);
    detalle2.setUnidadMedida("UND");
    detalle2.setPeso(new BigDecimal("15.25"));
    detalle2.setCodigoProducto("PROD002");
    detalle2.setCodigoSunat("87654321");

    dto.setDetalleGuias(Arrays.asList(detalle1, detalle2));

    return dto;
  }
}