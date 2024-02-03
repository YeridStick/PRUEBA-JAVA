package com.p_tecnica.crud.services.impl;

import com.p_tecnica.crud.constantes.Constantes;
import com.p_tecnica.crud.model.EstadosCuentaEntity;
import com.p_tecnica.crud.model.TipoCuentaEntity;
import com.p_tecnica.crud.model.TipoEntidadEntity;
import com.p_tecnica.crud.model.TipoTransccionEntity;
import com.p_tecnica.crud.repository.EstadoCuentaRepository;
import com.p_tecnica.crud.repository.TipoCuentaRepository;
import com.p_tecnica.crud.repository.TipoEntidadEntityRepository;
import com.p_tecnica.crud.repository.TipoTransaccionesRepository;
import com.p_tecnica.crud.response.MensajeResponseRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TiposSerivesImplTest {
    @Mock
    private EstadoCuentaRepository estadoCuentaRepository;
    @Mock
    private TipoCuentaRepository tipoCuentaRepository;
    @Mock
    private TipoEntidadEntityRepository tipoEntidad;
    @Mock
    private TipoTransaccionesRepository transaccionesRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks TiposSerivesImpl tiposSerives;

    @Test
    void listEstadoCuenta() {
        // Crear datos de prueba
        EstadosCuentaEntity estadosCuenta = new EstadosCuentaEntity();
        estadosCuenta.setIdCuenta(1L);
        estadosCuenta.setNombreEstado("Estado 1");

        EstadosCuentaEntity estadoCuenta2 = new EstadosCuentaEntity();
        estadoCuenta2.setIdCuenta(2L);
        estadoCuenta2.setNombreEstado("Estado 2");

        List<EstadosCuentaEntity> estadosCuentaList = Arrays.asList(estadosCuenta, estadoCuenta2);

        // Configurar comportamiento del repositorio mock
        Mockito.when(estadoCuentaRepository.findAll())
                .thenReturn(estadosCuentaList);

        var result = tiposSerives.listEstadoCuenta();
        Assertions.assertNotNull(result);
    }

    @Test
    void listCuenta() {
        TipoCuentaEntity tipoCuenta1 = new TipoCuentaEntity();
        tipoCuenta1.setIdCuenta(1L);
        tipoCuenta1.setNombreCuenta("TipoCuenta 1");

        TipoCuentaEntity tipoCuenta2 = new TipoCuentaEntity();
        tipoCuenta2.setIdCuenta(2L);
        tipoCuenta2.setNombreCuenta("TipoCuenta 2");

        List<TipoCuentaEntity> tipoCuentaList = Arrays.asList(tipoCuenta1, tipoCuenta2);

        // Configurar comportamiento del repositorio mock
        Mockito.when(tipoCuentaRepository.findAll())
                .thenReturn(tipoCuentaList);

        var result = tiposSerives.listCuenta();
        Assertions.assertNotNull(result);
    }

    @Test
    void listEntidad() {
        // Crear datos de prueba
        TipoEntidadEntity tipoEntidad1 = new TipoEntidadEntity();
        tipoEntidad1.setIdEntidad(1L);
        tipoEntidad1.setNombreEntidad("TipoEntidad 1");

        TipoEntidadEntity tipoEntidad2 = new TipoEntidadEntity();
        tipoEntidad2.setIdEntidad(2L);
        tipoEntidad2.setNombreEntidad("TipoEntidad 2");

        List<TipoEntidadEntity> tipoEntidadList = Arrays.asList(tipoEntidad1, tipoEntidad2);

        Mockito.when(tipoEntidad.findAll())
                .thenReturn(tipoEntidadList);

        var result = tiposSerives.listEntidad();
        Assertions.assertNotNull(result);
    }

    @Test
    void lisTipoTransacciones() {
        // Crear datos de prueba
        TipoTransccionEntity tipoTransaccion1 = new TipoTransccionEntity();
        tipoTransaccion1.setIdEntidad(1L);
        tipoTransaccion1.setNombreTransaccion("TipoTransaccion 1");

        TipoTransccionEntity tipoTransaccion2 = new TipoTransccionEntity();
        tipoTransaccion2.setIdEntidad(2L);
        tipoTransaccion2.setNombreTransaccion("TipoTransaccion 2");

        List<TipoTransccionEntity> tipoTransaccionList = Arrays.asList(tipoTransaccion1, tipoTransaccion2);

        // Configurar comportamiento del repositorio mock
        Mockito.when(transaccionesRepository.findAll())
                .thenReturn(tipoTransaccionList);

        var result = tiposSerives.lisTipoTransacciones();
        Assertions.assertNotNull(result);
    }

    @Test
    void crearEstadosCuenta() {
        Mockito.when(estadoCuentaRepository.count()).thenReturn(0L);
        Mockito.when(estadoCuentaRepository.save(Mockito.any(EstadosCuentaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));  // Devolver el argumento (entity) como respuesta

        System.out.println("Count inicial: " + estadoCuentaRepository.count());
        tiposSerives.crearEstadosCuenta();

        Mockito.verify(estadoCuentaRepository, Mockito.times(3)).save(Mockito.any(EstadosCuentaEntity.class));
        System.out.println("Count final: " + estadoCuentaRepository.count());
    }


    @Test
    void crearTipoCuentas() {
        Mockito.when(tipoCuentaRepository.count()).thenReturn(0L);
        Mockito.when(tipoCuentaRepository.save(Mockito.any(TipoCuentaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));  // Devolver el argumento (entity) como respuesta

        System.out.println("Count inicial: " + tipoCuentaRepository.count());
        tiposSerives.crearTipoCuentas();

        Mockito.verify(tipoCuentaRepository, Mockito.times(2)).save(Mockito.any(TipoCuentaEntity.class));
        System.out.println("Count final: " + tipoCuentaRepository.count());
    }

    @Test
    void crearTiposEntidad() {
        Mockito.when(tipoEntidad.count()).thenReturn(0L);
        Mockito.when(tipoEntidad.save(Mockito.any(TipoEntidadEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));  // Devolver el argumento (entity) como respuesta

        System.out.println("Count inicial: " + tipoEntidad.count());
        tiposSerives.crearTiposEntidad();

        Mockito.verify(tipoEntidad, Mockito.times(3)).save(Mockito.any(TipoEntidadEntity.class));
        System.out.println("Count final: " + tipoEntidad.count());
    }

    @Test
    void crearTiposTransaccion() {
        Mockito.when(transaccionesRepository.count()).thenReturn(0L);
        Mockito.when(transaccionesRepository.save(Mockito.any(TipoTransccionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));  // Devolver el argumento (entity) como respuesta

        System.out.println("Count inicial: " + transaccionesRepository.count());
        tiposSerives.crearTiposTransaccion();

        Mockito.verify(transaccionesRepository, Mockito.times(3)).save(Mockito.any(TipoTransccionEntity.class));
        System.out.println("Count final: " + transaccionesRepository.count());
    }
}