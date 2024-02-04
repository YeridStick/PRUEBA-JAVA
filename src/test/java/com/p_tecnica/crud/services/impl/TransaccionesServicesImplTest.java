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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransaccionesServicesImplTest {
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
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
        transaccionLoginDTO.setTipoTransaccionEntity(2L);
        transaccionLoginDTO.setNumeroCuenta("123456789");
        transaccionLoginDTO.setNumeroCuentaDestino("12599986358");
        transaccionLoginDTO.setValorTransaccion(400L);
        transaccionLoginDTO.setContrasena("9122003.");


        String validHashedPassword = "$2a$10$2JdhxU1zWT4RXaczc0DPk.9albUwKF3pyf5Y5XtlokYXfR1yG1Ide";

        Mockito.lenient().when(passwordEncoder.matches(
                transaccionLoginDTO.getContrasena().trim(),
                validHashedPassword
        )).thenReturn(true);

        CuentasEntity cuentaExistente = new CuentasEntity();
        cuentaExistente.setSaldo(4400.0);

        cuentaExistente.setContrasena(validHashedPassword);
        Mockito.when(cuentasrepository.findByNumeroPin(transaccionLoginDTO.getNumeroCuenta()))
                .thenReturn(Optional.of(cuentaExistente));

        TipoTransccionEntity tipoTransaccion = new TipoTransccionEntity();
        tipoTransaccion.setIdEntidad(2L);

        TransaccionesEntity transaccionesEntity = new TransaccionesEntity();
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TransaccionesEntity.class)))
                .thenReturn(transaccionesEntity);

        Optional<TipoTransccionEntity> tipoTransaccionEntity = Optional.of(new TipoTransccionEntity());
        tipoTransaccionEntity.get().setNombreTransaccion("Consignacion");
        Mockito.when(transaccionesRepository.findById(Mockito.eq(transaccionLoginDTO.getTipoTransaccionEntity())))
                .thenReturn(tipoTransaccionEntity);

        Optional<CuentasEntity> cuentaExistenteObjeto = Optional.of(new CuentasEntity());
        cuentaExistenteObjeto.get().setSaldo(4400.0);

        var result = transaccionesServices.createTransaccion(transaccionLoginDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void createTransaccionRetiro() {
        TransaccionLoginDTO transaccionLoginDTO = new TransaccionLoginDTO();
        transaccionLoginDTO.setTipoTransaccionEntity(2L);
        transaccionLoginDTO.setNumeroCuenta("123456789");
        transaccionLoginDTO.setNumeroCuentaDestino("12599986358");
        transaccionLoginDTO.setValorTransaccion(400L);
        transaccionLoginDTO.setContrasena("9122003.");

        String validHashedPassword = "$2a$10$2JdhxU1zWT4RXaczc0DPk.9albUwKF3pyf5Y5XtlokYXfR1yG1Ide";

        Mockito.lenient().when(passwordEncoder.matches(
                transaccionLoginDTO.getContrasena().trim(),
                validHashedPassword
        )).thenReturn(true);

        CuentasEntity cuentaExistente = new CuentasEntity();
        cuentaExistente.setSaldo(4400.0);

        TipoCuentaEntity tipoCuenta = new TipoCuentaEntity();
        tipoCuenta.setNombreCuenta("Ahorro");

        cuentaExistente.setContrasena(validHashedPassword);
        cuentaExistente.setTipoCuentaEntity(tipoCuenta);

        Mockito.when(cuentasrepository.findByNumeroPin(Mockito.anyString()))
                .thenReturn(Optional.of(cuentaExistente));

        Optional<TipoTransccionEntity> tipoTransaccionEntity = Optional.of(new TipoTransccionEntity());
        tipoTransaccionEntity.get().setNombreTransaccion("Retiro");
        Mockito.when(transaccionesRepository.findById(Mockito.eq(transaccionLoginDTO.getTipoTransaccionEntity())))
                .thenReturn(tipoTransaccionEntity);

        TransaccionesEntity transaccionesEntity = new TransaccionesEntity();
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TransaccionesEntity.class)))
                .thenReturn(transaccionesEntity);

        var result = transaccionesServices.createTransaccion(transaccionLoginDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void createTransaccionTransferencia() {
        // Crear un DTO para la transacción de transferencia
        TransaccionLoginDTO transaccionLoginDTO = new TransaccionLoginDTO();
        transaccionLoginDTO.setTipoTransaccionEntity(2L); // Asumiendo que 2L es el ID para el tipo de transacción de transferencia
        transaccionLoginDTO.setNumeroCuenta("123456789"); // Cuenta origen
        transaccionLoginDTO.setNumeroCuentaDestino("123456787"); // Cuenta destino
        transaccionLoginDTO.setValorTransaccion(400L);
        transaccionLoginDTO.setContrasena("9122003.");

        // Hash de contraseña válido generado previamente
        String validHashedPassword = "$2a$10$2JdhxU1zWT4RXaczc0DPk.9albUwKF3pyf5Y5XtlokYXfR1yG1Ide";


        CuentasEntity cuentaExistente = new CuentasEntity();
        cuentaExistente.setSaldo(4400.0);

        TipoCuentaEntity tipoCuenta = new TipoCuentaEntity();
        tipoCuenta.setNombreCuenta("Ahorro");

        cuentaExistente.setContrasena(validHashedPassword);
        cuentaExistente.setTipoCuentaEntity(tipoCuenta);
        cuentaExistente.setContrasena(validHashedPassword);

        // Configurar el mock para que devuelva true cuando se compare la contraseña con su hash
        Mockito.lenient().when(passwordEncoder.matches(
                transaccionLoginDTO.getContrasena().trim(),
                validHashedPassword
        )).thenReturn(true);


        // Crear una entidad de cuenta destino (cuenta destino)
        CuentasEntity cuentaDestino = new CuentasEntity();
        cuentaDestino.setTipoCuentaEntity(tipoCuenta);
        cuentaDestino.getTipoCuentaEntity().setNombreCuenta("Ahorro");
        cuentaDestino.setSaldo(3000.0); // Saldo diferente para distinguirlo
        cuentaDestino.setContrasena(validHashedPassword); // Contraseña igual para simplificar la prueba
        cuentaDestino.setTipoCuentaEntity(new TipoCuentaEntity()); // Tipo de cuenta igual para simplificar la prueba

        // Configurar el mock para que devuelva la cuenta existente cuando se llame a findByNumeroPin con el número de cuenta de origen
        Mockito.when(cuentasrepository.findByNumeroPin(transaccionLoginDTO.getNumeroCuenta()))
                .thenReturn(Optional.of(cuentaExistente));

        // Configurar el mock para que devuelva la cuenta destino cuando se llame a findByNumeroPin con el número de cuenta de destino
        Mockito.when(cuentasrepository.findByNumeroPin(transaccionLoginDTO.getNumeroCuentaDestino()))
                .thenReturn(Optional.of(cuentaDestino));

        // Crear una entidad de tipo de transacción
        TipoTransccionEntity tipoTransaccion = new TipoTransccionEntity();
        tipoTransaccion.setNombreTransaccion("Transferencia");

        // Configurar el mock para que devuelva un Optional con el objeto TipoTransaccionEntity cuando se llame a findById
        Mockito.when(transaccionesRepository.findById(transaccionLoginDTO.getTipoTransaccionEntity()))
                .thenReturn(Optional.of(tipoTransaccion));

        TransaccionesEntity transaccionesEntity = new TransaccionesEntity();
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TransaccionesEntity.class)))
                .thenReturn(transaccionesEntity);

        /*Mockito.when(cuentasrepository.findByNumeroPin(transaccionLoginDTO.getNumeroCuenta()))
                .thenReturn(Optional.of(cuentaExistente));*/


        /*Mockito.when(cuentasrepository.findByNumeroPin(transaccionLoginDTO.getNumeroCuentaDestino()))
                .thenReturn(Optional.of(cuentaDestino));*/


        // Ejecutar el método de creación de transacción
        var result = transaccionesServices.createTransaccion(transaccionLoginDTO);

        // Verificar el resultado
        Assertions.assertNotNull(result);
        // Aquí puedes agregar más aserciones para verificar el estado de 'result' si es necesario
    }




}