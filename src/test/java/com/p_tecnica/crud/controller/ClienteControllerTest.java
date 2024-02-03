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
    void createCliente() {
        // Crear un objeto de prueba para ClienteEntityloginDTO
        ClienteEntityloginDTO clienteEntityloginDTO = new ClienteEntityloginDTO();
        // Establecer valores en clienteEntityloginDTO según sea necesario

        // Crear un objeto de prueba para MensajeResponseRest
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();
        // Establecer valores en mensajeResponseRest según sea necesario

        // Configurar el comportamiento del servicio mock
        Mockito.when(clienteService.crateCliente(Mockito.any(ClienteEntityloginDTO.class)))
                .thenReturn(mensajeResponseRest);

        // Llamar al método del controlador
        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.crateCliente(clienteEntityloginDTO);

        // Verificar resultados
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }

    @Test
    void listCliente() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();

        Mockito.when(clienteService.listCliente())
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.listCliente();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }

    @Test
    void buscarCliente() {
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();
        Mockito.when(clienteService.buscarCliente(Mockito.anyString()))
                .thenReturn(mensajeResponseRest);

        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.buscarCliente("123456789");

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }

    @Test
    void actualizarCliente() {
        // Crear un objeto de prueba para ClienteEntityloginDTO
        ClienteEntityloginDTO clienteEntityloginDTO = new ClienteEntityloginDTO();
        // Establecer valores en clienteEntityloginDTO según sea necesario

        // Crear un objeto de prueba para MensajeResponseRest
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();
        // Establecer valores en mensajeResponseRest según sea necesario

        // Configurar el comportamiento del servicio mock
        Mockito.when(clienteService.actualizarCliente(Mockito.any(ClienteEntityloginDTO.class)))
                .thenReturn(mensajeResponseRest);

        // Llamar al método del controlador
        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.actualizarCliente(clienteEntityloginDTO);

        // Verificar resultados
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());
    }

    @Test
    void eliminarCliente() {
        // Establecer un número de identificación de cliente para el caso de prueba
        String numIdent = "123456789";

        // Crear un objeto de prueba para MensajeResponseRest
        MensajeResponseRest mensajeResponseRest = new MensajeResponseRest();
        // Establecer valores en mensajeResponseRest según sea necesario

        // Configurar el comportamiento del servicio mock
        Mockito.when(clienteService.eliminarCliente(Mockito.eq(numIdent)))
                .thenReturn(mensajeResponseRest);

        // Llamar al método del controlador
        ResponseEntity<MensajeResponseRest> responseEntity = clienteController.eliminarCliente(numIdent);

        // Verificar resultados
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(mensajeResponseRest, responseEntity.getBody());

        // Verificar que el método eliminarCliente del servicio mock se llamó exactamente una vez
        Mockito.verify(clienteService, Mockito.times(1)).eliminarCliente(numIdent);
    }
}