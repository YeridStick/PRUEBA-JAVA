package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.dto.Login.CuentaEditDTO;
import com.p_tecnica.crud.dto.Login.CuentaLoginDTO;
import com.p_tecnica.crud.model.EstadosCuentaEntity;
import com.p_tecnica.crud.repository.EstadoCuentaRepository;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.CuentasServices;
import com.p_tecnica.crud.services.TiposServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CuentaControllerTest {
    @Mock
    private CuentasServices cuentasServices;
    @Mock
    private TiposServices tipoCuentaServices;
    @Mock
    private TiposServices tiposServices;

    @InjectMocks
    private CuentaController cuentaController;


    @Mock
    private CuentasServices cuentasServicesMock;

    @Mock
    private TiposServices tipoCuentaServicesMock;

    @Mock
    private TiposServices estadosCuentasServicesMock;

    @Test
    void listCuenta() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(cuentasServices.listCuneta())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = cuentaController.listCuenta();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }

    @Test
    void createCuenta() {
        // Configuración del DTO de prueba
        CuentaLoginDTO cuentaLoginDTO = new CuentaLoginDTO();
        cuentaLoginDTO.setIdCliente("123456789");
        cuentaLoginDTO.setTipoCuenta(1L);

        // Configuración del servicio mock
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();
        Mockito.when(cuentasServices.createCuenta(cuentaLoginDTO)).thenReturn(mensajeResponseRest);

        // Llamada al método del controlador
        ResponseEntity<MensajeResponseRest> responseEntity = cuentaController.createCuenta(cuentaLoginDTO);

        // Verificación de resultados
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }


    @Test
    void editarCuenta() {
        CuentaEditDTO cuentaEditDTO = new CuentaEditDTO();
        cuentaEditDTO.setIdCuenta(1L);
        cuentaEditDTO.setIdCliente("123456789");

        // Respuesta esperada
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        // Configuración del comportamiento del servicio mock
        Mockito.when(cuentasServices.editarCuenta(Mockito.any(CuentaEditDTO.class)))
                .thenReturn(mensajeResponseRest);

        // Llamada al método del controlador
        ResponseEntity<MensajeResponseRest> responseEntity = cuentaController.editarCuenta(cuentaEditDTO);

        // Impresión de información de depuración
        System.out.println("Body de la respuesta: " + responseEntity.getBody());

        // Verificación de resultados
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        // Verificación de llamadas al servicio mock
        Mockito.verify(cuentasServices, Mockito.times(1)).editarCuenta(Mockito.any(CuentaEditDTO.class));
    }

    @Test
    void listCuentas() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(tipoCuentaServices.listCuenta())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = cuentaController.listCuentas();

        System.out.println("Body de la respuesta: " + responseEntity.getBody());

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(tipoCuentaServices, Mockito.times(1)).listCuenta();
    }

    @Test
    void listEstados() {

    }


}