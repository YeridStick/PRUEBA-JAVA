package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.dto.Login.CuentaEditDTO;
import com.p_tecnica.crud.dto.Login.CuentaLoginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.CuentasServices;
import com.p_tecnica.crud.services.TiposServices;
import org.junit.jupiter.api.Assertions;
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
    private TiposServices estadosCuentasServices;

    @InjectMocks
    private CuentaController cuentaController;

    @Test
    void listCuenta() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(cuentasServices.listCuneta())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = cuentaController.listCuenta();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(cuentasServices, Mockito.times(1)).listCuneta();
    }

    @Test
    void createCuenta() {
        CuentaLoginDTO cuentaLoginDTO = new CuentaLoginDTO();
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(cuentasServices.createCuenta(Mockito.any(CuentaLoginDTO.class)))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = cuentaController.createCuenta(cuentaLoginDTO);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(cuentasServices, Mockito.times(1)).createCuenta(Mockito.any(CuentaLoginDTO.class));
    }

    @Test
    void editarCuenta() {
        CuentaEditDTO cuentaEditDTO = new CuentaEditDTO();

        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();
        Mockito.when(cuentasServices.editarCuenta(Mockito.any(CuentaEditDTO.class)))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = cuentaController.editarCuenta(cuentaEditDTO);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
        Mockito.verify(cuentasServices, Mockito.times(1)).editarCuenta(Mockito.any(CuentaEditDTO.class));
    }

    @Test
    void listTipoCuentas() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(tipoCuentaServices.listCuenta())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = cuentaController.listTipoCuentas();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(tipoCuentaServices, Mockito.times(1)).listCuenta();
    }

    @Test
    void listEstados() {

    }
}