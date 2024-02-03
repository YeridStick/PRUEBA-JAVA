package com.p_tecnica.crud.services.impl;

import com.p_tecnica.crud.constantes.Constantes;
import com.p_tecnica.crud.dto.Login.ClienteEntityloginDTO;
import com.p_tecnica.crud.model.ClienteEntity;
import com.p_tecnica.crud.model.TipoEntidadEntity;
import com.p_tecnica.crud.repository.ClienteRepository;
import com.p_tecnica.crud.repository.Cuentasrepository;
import com.p_tecnica.crud.repository.TipoEntidadEntityRepository;
import com.p_tecnica.crud.response.MensajeResponseRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {
    @Mock
    private ClienteRepository cliente;
    @Mock
    private Cuentasrepository cuenta;
    @Mock
    private TipoEntidadEntityRepository tipoEntidad;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void crateCliente() {

        Optional<ClienteEntityloginDTO> clienteEntityloginDTO = Optional.of(new ClienteEntityloginDTO());

        ClienteEntityloginDTO clienteEntityloginDTOObjeto = new ClienteEntityloginDTO();
        clienteEntityloginDTOObjeto = clienteEntityloginDTO.get();

        clienteEntityloginDTO.get().setFnacimiento("09-12-2000");
        clienteEntityloginDTOObjeto.setNumIdent("55423698");
        clienteEntityloginDTOObjeto.setTipoEntidad("CC");

        Optional<ClienteEntity> ClienteEntity = Optional.of(new ClienteEntity());

        TipoEntidadEntity tipoEntidadEntity = new TipoEntidadEntity();

        ClienteEntity.get().setNumIdent("123456789");

        Mockito.doReturn(Optional.ofNullable(null)).when(cliente).
                findById(Mockito.anyString());

        Mockito.doReturn(Optional.of(tipoEntidadEntity)).when(tipoEntidad)
                .findBySigla(Mockito.anyString());

        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(ClienteEntity.class)))
                .thenAnswer(invocation -> {
                    ClienteEntityloginDTO dto = invocation.getArgument(0);
                    ClienteEntity clienteEntity = new ClienteEntity();
                    return clienteEntity;
                });

        var result = clienteService.crateCliente(clienteEntityloginDTOObjeto);
        Assertions.assertNotNull(result);
    }

    @Test
    void listCliente() {
        ClienteEntity cliente1 = new ClienteEntity();
        ClienteEntity cliente2 = new ClienteEntity();
        List<ClienteEntity> listaClientes = Arrays.asList(cliente1, cliente2);

        Mockito.when(cliente.findAll()).thenReturn(listaClientes);

        var result = clienteService.listCliente();
        Assertions.assertNotNull(result);
    }

    @Test
    void buscarCliente() {

        Optional<ClienteEntity> buscarCliente = Optional.of(new ClienteEntity());

        Mockito.doReturn(buscarCliente).when(cliente).findById("123456789");

        var result = clienteService.buscarCliente("123456789");
        Assertions.assertNotNull(result);
    }

    @Test
    void actualizarCliente() {
        ClienteEntityloginDTO clienteEntityloginDTO = new ClienteEntityloginDTO();
        clienteEntityloginDTO.setNumIdent("123456789");
        clienteEntityloginDTO.setTipoEntidad("CC");
        clienteEntityloginDTO.setFnacimiento("09-12-2000");

        // Crear un objeto de prueba para el ClienteEntity
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNumIdent("123456789");

        Mockito.when(cliente.findById(Mockito.anyString())).thenReturn(Optional.of(clienteEntity));

        TipoEntidadEntity tipoEntidadEntity = new TipoEntidadEntity();
        Mockito.when(tipoEntidad.findBySigla(Mockito.anyString())).thenReturn(Optional.of(tipoEntidadEntity));

        ClienteEntity editarCliente = new ClienteEntity();
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(ClienteEntity.class))).thenReturn(editarCliente);

        var result = clienteService.actualizarCliente(clienteEntityloginDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void eliminarCliente() {
        String numeroIdent = "123456789";

        ClienteEntity clienteEntity = new ClienteEntity();

        Mockito.when(cliente.findById(Mockito.eq(numeroIdent))).thenReturn(Optional.of(clienteEntity));
        Mockito.when(cuenta.findByClienteId(Mockito.eq(numeroIdent))).thenReturn(new ArrayList<>());

        var result = clienteService.eliminarCliente(numeroIdent);
        Assertions.assertNotNull(result);
    }
}