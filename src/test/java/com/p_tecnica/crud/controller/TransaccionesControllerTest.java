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

    @InjectMocks
    private TransaccionesController transaccionesController;

    @Test
    void crearTransaccion() {
        TransaccionLoginDTO transaccionLoginDTO = new TransaccionLoginDTO();
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(transaccionesServices.createTransaccion(Mockito.any(TransaccionLoginDTO.class)))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = transaccionesController.crearTransaccion(transaccionLoginDTO);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(transaccionesServices, Mockito.times(1)).createTransaccion(Mockito.any(TransaccionLoginDTO.class));
    }

    @Test
    void listTransaccion() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(tipoTransaccionesServices.lisTipoTransacciones())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = transaccionesController.listTransaccion();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(tipoTransaccionesServices, Mockito.times(1)).lisTipoTransacciones();
    }
}