package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.TiposServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TipoEntidadControllerTest {
    @Mock
    private  TiposServices tipoEntidadServices;

    @InjectMocks TipoEntidadController tipoEntidadController;

    @Test
    void listEntidad() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(tipoEntidadServices.listEntidad())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = tipoEntidadController.listEntidad();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }
}