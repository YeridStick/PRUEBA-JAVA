package com.p_tecnica.crud.services.impl;

import com.p_tecnica.crud.dto.Login.TransaccionLoginDTO;
import com.p_tecnica.crud.model.*;
import com.p_tecnica.crud.repository.Cuentasrepository;
import com.p_tecnica.crud.repository.TipoTransaccionesRepository;
import com.p_tecnica.crud.repository.TransaccioneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransaccionesServicesImplTest {
    @Mock
    private TransaccioneRepository transaccioneRepository;
    @Mock
    private TipoTransaccionesRepository transaccionesRepository;
    @Mock
    private Cuentasrepository cuentasrepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks TransaccionesServicesImpl transaccionesServices;

    @Test
    void createTransaccion() {
        TransaccionLoginDTO transaccionLoginDTO = new TransaccionLoginDTO();
        transaccionLoginDTO.setTipoTransccionEntity(2L);
        transaccionLoginDTO.setNumeroCuenta("123456789");
        transaccionLoginDTO.setNumeroCuentaDestino("12599986358");
        transaccionLoginDTO.setValorTransaccion(400L);

        // Configurar el mock para que findByNumeroPin devuelva Optional.of
        CuentasEntity cuentaExistente = new CuentasEntity();
        cuentaExistente.setSaldo(400L);
        Mockito.when(cuentasrepository.findByNumeroPin(Mockito.anyString()))
                .thenReturn(Optional.of(cuentaExistente));

        // Configurar el mock para que transaccionesRepository devuelva Optional.empty()
        Optional<TipoTransccionEntity> tipoTransaccionEntity = Optional.of(new TipoTransccionEntity());
        tipoTransaccionEntity.get().setNombreTransaccion("Consignacion");
        Mockito.when(transaccionesRepository.findById(Mockito.eq(transaccionLoginDTO.getTipoTransccionEntity())))
                .thenReturn(tipoTransaccionEntity);

        // Configurar el mock para el modelMapper
        TransaccionesEntity transaccionesEntity = new TransaccionesEntity();
        transaccionesEntity.setCuentasEntityDestino(cuentaExistente);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TransaccionesEntity.class)))
                .thenReturn(transaccionesEntity);



        // Ejecutar el método bajo prueba
        var result = transaccionesServices.createTransaccion(transaccionLoginDTO);

        // Verificar el resultado
        Assertions.assertNotNull(result);
    }

    @Test
    void createTransaccionRetiro() {
        TransaccionLoginDTO transaccionLoginDTO = new TransaccionLoginDTO();
        transaccionLoginDTO.setTipoTransccionEntity(3L); // Ajusta el tipo de transacción para retiro
        transaccionLoginDTO.setNumeroCuenta("123456789");
        transaccionLoginDTO.setNumeroCuentaDestino("12599986358");
        transaccionLoginDTO.setValorTransaccion(200L);

        CuentasEntity cuentaExistente = new CuentasEntity();
        cuentaExistente.setSaldo(400L); // Ajusta el saldo según tus necesidades
        Mockito.when(cuentasrepository.findByNumeroPin(Mockito.anyString()))
                .thenReturn(Optional.of(cuentaExistente));

        Optional<TipoTransccionEntity> tipoTransaccionEntity = Optional.of(new TipoTransccionEntity());
        tipoTransaccionEntity.get().setNombreTransaccion("Retiro");
        Mockito.when(transaccionesRepository.findById(Mockito.eq(transaccionLoginDTO.getTipoTransccionEntity())))
                .thenReturn(tipoTransaccionEntity);

        TransaccionesEntity transaccionesEntity = new TransaccionesEntity();
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TransaccionesEntity.class)))
                .thenReturn(transaccionesEntity);

        var result = transaccionesServices.createTransaccion(transaccionLoginDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void createTransaccionTransferencia() {
        TransaccionLoginDTO transaccionLoginDTO = new TransaccionLoginDTO();
        transaccionLoginDTO.setTipoTransccionEntity(4L);
        transaccionLoginDTO.setNumeroCuenta("123456789");
        transaccionLoginDTO.setNumeroCuentaDestino("12599986358");
        transaccionLoginDTO.setValorTransaccion(200L);

        CuentasEntity cuentaExistente = new CuentasEntity();
        cuentaExistente.setSaldo(400L);
        Mockito.when(cuentasrepository.findByNumeroPin(Mockito.anyString()))
                .thenReturn(Optional.of(cuentaExistente));

        CuentasEntity cuentaDestino = new CuentasEntity();
        cuentaDestino.setSaldo(300L); // Ajusta el saldo según tus necesidades
        Mockito.when(cuentasrepository.findByNumeroPin(Mockito.eq(transaccionLoginDTO.getNumeroCuentaDestino())))
                .thenReturn(Optional.of(cuentaDestino));

        Optional<TipoTransccionEntity> tipoTransaccionEntity = Optional.of(new TipoTransccionEntity());
        tipoTransaccionEntity.get().setNombreTransaccion("Transferencia");
        Mockito.when(transaccionesRepository.findById(Mockito.eq(transaccionLoginDTO.getTipoTransccionEntity())))
                .thenReturn(tipoTransaccionEntity);

        TransaccionesEntity transaccionesEntity = new TransaccionesEntity();
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TransaccionesEntity.class)))
                .thenReturn(transaccionesEntity);

        var result = transaccionesServices.createTransaccion(transaccionLoginDTO);
        Assertions.assertNotNull(result);
    }



}