package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.dto.Login.TransaccionLoginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.TiposServices;
import com.p_tecnica.crud.services.TransaccionesServices;
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
class TransaccionesControllerTest {
    @Mock
    private TiposServices tipoTransaccionesServices;
    @Mock
    private TransaccionesServices transaccionesServices;
    @InjectMocks TransaccionesController transaccionesController;

    @Test
    void crearTransaccion() {
        // Crear un objeto de prueba para transaccionLoginDTO
        TransaccionLoginDTO transaccionLoginDTO = new TransaccionLoginDTO();
        transaccionLoginDTO.setTipoTransccionEntity(1L);
        transaccionLoginDTO.setNumeroCuenta("123456789");
        transaccionLoginDTO.setNumeroCuentaDestino("12599986358");
        transaccionLoginDTO.setValorTransaccion(400L);

        // Crear un objeto de prueba para MensajeResponseRest
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(transaccionesServices.createTransaccion(Mockito.any(TransaccionLoginDTO.class)))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = transaccionesController.crearTransaccion(transaccionLoginDTO);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }

    @Test
    void listTransaccion() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(tipoTransaccionesServices.lisTipoTransacciones())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = transaccionesController.listTransaccion();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }
}