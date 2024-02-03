package com.p_tecnica.crud.services.impl;

import com.p_tecnica.crud.dto.Login.ClienteEntityloginDTO;
import com.p_tecnica.crud.dto.Login.CuentaEditDTO;
import com.p_tecnica.crud.dto.Login.CuentaLoginDTO;
import com.p_tecnica.crud.model.*;
import com.p_tecnica.crud.repository.ClienteRepository;
import com.p_tecnica.crud.repository.Cuentasrepository;
import com.p_tecnica.crud.repository.EstadoCuentaRepository;
import com.p_tecnica.crud.repository.TipoCuentaRepository;
import com.p_tecnica.crud.response.MensajeResponseRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CuentasServicesImplTest {
    @Mock
    private Cuentasrepository cuentasrepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private TipoCuentaRepository tipoCuentaRepository;
    @Mock
    private EstadoCuentaRepository estadoCuentaRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks CuentasServicesImpl cuentasServices;

    @Test
    void createCuenta() {
        // Crear un objeto de prueba para el CuentaLoginDTO
        CuentaLoginDTO cuentaLoginDTO = new CuentaLoginDTO();
        cuentaLoginDTO.setIdCliente("123456789");
        cuentaLoginDTO.setTipoCuenta(1L);
        cuentaLoginDTO.setIdEstadoCuenta(1L);

        // Crear objetos de prueba para los repositorios
        Optional<ClienteEntity> clienteEntity = Optional.of(new ClienteEntity());
        clienteEntity.get().setNumIdent("123456789");
        Optional<TipoCuentaEntity> tipoCuenta = Optional.of(new TipoCuentaEntity());
        tipoCuenta.get().setIdCuenta(1L);
        Optional<EstadosCuentaEntity> estadosCuenta = Optional.of(new EstadosCuentaEntity());
        estadosCuenta.get().setIdCuenta(1L);
        // Configurar mocks para los repositorios usando Mockito.mock
        Mockito.when(clienteRepository.findById(Mockito.eq(cuentaLoginDTO.getIdCliente())))
                .thenReturn(clienteEntity);
        Mockito.when(tipoCuentaRepository.findById(Mockito.eq(cuentaLoginDTO.getTipoCuenta())))
                .thenReturn(tipoCuenta);
        Mockito.when(estadoCuentaRepository.findById(Mockito.eq(cuentaLoginDTO.getIdEstadoCuenta())))
                .thenReturn(estadosCuenta);

        // Configurar mock para el modelMapper
        CuentasEntity cuentasEntity = new CuentasEntity();
        cuentasEntity.setSaldo(400L);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(CuentasEntity.class)))
                .thenReturn(cuentasEntity);

        // Configurar mock para el repositorio de cuentas
        Mockito.when(cuentasrepository.save(Mockito.any())).thenReturn(cuentasEntity);

        // Llamar al método bajo prueba
        var result = cuentasServices.createCuenta(cuentaLoginDTO);

        // Verificar el resultado
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getMensajeResponse());
        Assertions.assertNotNull(result.getMensajeResponse().getCuenta());
        Assertions.assertEquals(cuentasEntity, result.getMensajeResponse().getCuenta());
    }

    @Test
    void editarCuenta() {
        CuentaEditDTO cuentaEditDTO = new CuentaEditDTO();
        cuentaEditDTO.setIdCuenta(1L);
        cuentaEditDTO.setIdCliente("123456789");
        cuentaEditDTO.setTipoCuenta(3L);
        cuentaEditDTO.setIdEstadoCuenta(4L);

        // Configurar mocks para los repositorios usando Mockito.when
        Optional<CuentasEntity> cuentaExistenteOptional = Optional.empty(); // Opcionalmente puedes establecer un valor presente
        Mockito.when(cuentasrepository.findById(cuentaEditDTO.getIdCuenta()))
                .thenReturn(cuentaExistenteOptional);

        CuentasEntity cuentaExistente = new CuentasEntity();
        cuentaExistente.setIdCuenta(1l);
        cuentaExistente.setSaldo(4400L);
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNumIdent("1234678");
        TipoCuentaEntity tipoCuenta = new TipoCuentaEntity();
        tipoCuenta.setIdCuenta(1L);
        tipoCuenta.setNombreCuenta("Ahorro");
        EstadosCuentaEntity estadosCuenta = new EstadosCuentaEntity();
        estadosCuenta.setIdCuenta(1L);
        estadosCuenta.setNombreEstado("Activa");

        Mockito.when(cuentasrepository.findById(cuentaEditDTO.getIdCuenta()))
                .thenReturn(Optional.of(cuentaExistente));

        Mockito.when(clienteRepository.findById(cuentaEditDTO.getIdCliente()))
                .thenReturn(Optional.of(clienteEntity));

        Mockito.when(tipoCuentaRepository.findById(cuentaEditDTO.getTipoCuenta()))
                .thenReturn(Optional.of(tipoCuenta));

        Mockito.when(estadoCuentaRepository.findById(cuentaEditDTO.getIdEstadoCuenta()))
                .thenReturn(Optional.of(estadosCuenta));


        // Configurar mock para el modelMapper
        CuentasEntity cuentasEntity = new CuentasEntity();
        cuentasEntity.setSaldo(400L);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(CuentasEntity.class)))
                .thenReturn(cuentasEntity);

        // Llamar al método bajo prueba
        var result = cuentasServices.editarCuenta(cuentaEditDTO);

        // Verificar el resultado
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getMensajeResponse());
        Assertions.assertNotNull(result.getMensajeResponse().getCuenta());
        Assertions.assertEquals(cuentasEntity, result.getMensajeResponse().getCuenta());
    }

    @Test
    void listCuneta() {
        CuentasEntity cuenta1 = new CuentasEntity();  // Asegúrate de cambiar el tipo según tu código
        CuentasEntity cuenta2 = new CuentasEntity();  // Asegúrate de cambiar el tipo según tu código
        List<CuentasEntity> listaCuentas = Arrays.asList(cuenta1, cuenta2);

        // Configurar comportamiento del repositorio mock
        Mockito.when(cuentasrepository.findAll()).thenReturn(listaCuentas);

        // Llamar al método bajo prueba
        MensajeResponseRest resultado = cuentasServices.listCuneta();
    }
}