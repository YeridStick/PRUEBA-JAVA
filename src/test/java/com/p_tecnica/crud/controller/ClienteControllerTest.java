package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.dto.Login.ClienteEntityloginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.ClienteService;
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
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void crateCliente() {
        ClienteEntityloginDTO clienteEntityloginDTO = new ClienteEntityloginDTO();

        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();
        Mockito.when(clienteService.crateCliente(Mockito.any(ClienteEntityloginDTO.class)))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.crateCliente(clienteEntityloginDTO);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(clienteService, Mockito.times(1)).crateCliente(Mockito.any(ClienteEntityloginDTO.class));
    }

    @Test
    void listCliente() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(clienteService.listCliente())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.listCliente();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(clienteService, Mockito.times(1)).listCliente();
    }

    @Test
    void buscarCliente() {
        String numIdent = "123456";
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(clienteService.buscarCliente(numIdent))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.buscarCliente(numIdent);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(clienteService, Mockito.times(1)).buscarCliente(numIdent);
    }

    @Test
    void actualizarCliente() {
        ClienteEntityloginDTO clienteEntityloginDTO = new ClienteEntityloginDTO();

        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(clienteService.actualizarCliente(Mockito.any(ClienteEntityloginDTO.class)))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.actualizarCliente(clienteEntityloginDTO);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(clienteService, Mockito.times(1)).actualizarCliente(Mockito.any(ClienteEntityloginDTO.class));
    }

    @Test
    void eliminarCliente() {
        String numIdent = "123456";
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(clienteService.eliminarCliente(numIdent))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.eliminarCliente(numIdent);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        Mockito.verify(clienteService, Mockito.times(1)).eliminarCliente(numIdent);
    }
}