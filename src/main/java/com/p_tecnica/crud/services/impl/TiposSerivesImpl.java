package com.p_tecnica.crud.services.impl;

import com.p_tecnica.crud.constantes.Constantes;
import com.p_tecnica.crud.exception.ExcepcionPersonalizada;
import com.p_tecnica.crud.model.EstadosCuentaEntity;
import com.p_tecnica.crud.model.TipoCuentaEntity;
import com.p_tecnica.crud.model.TipoEntidadEntity;
import com.p_tecnica.crud.model.TipoTransccionEntity;
import com.p_tecnica.crud.repository.EstadoCuentaRepository;
import com.p_tecnica.crud.repository.TipoCuentaRepository;
import com.p_tecnica.crud.repository.TipoEntidadEntityRepository;
import com.p_tecnica.crud.repository.TipoTransaccionesRepository;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.TiposServices;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiposSerivesImpl implements TiposServices {
    private  final EstadoCuentaRepository estadoCuentaRepository;
    private  final TipoCuentaRepository tipoCuentaRepository;
    private final TipoEntidadEntityRepository tipoEntidad;
    private final TipoTransaccionesRepository transaccionesRepository;
    private final ModelMapper modelMapper;

    public TiposSerivesImpl(EstadoCuentaRepository estadoCuentaRepository, TipoCuentaRepository tipoCuentaRepository, TipoEntidadEntityRepository tipoEntidad, TipoTransaccionesRepository transaccionesRepository, ModelMapper modelMapper) {
        this.estadoCuentaRepository = estadoCuentaRepository;
        this.tipoCuentaRepository = tipoCuentaRepository;
        this.tipoEntidad = tipoEntidad;
        this.transaccionesRepository = transaccionesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MensajeResponseRest listEstadoCuenta() {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            List<EstadosCuentaEntity> listEstados = estadoCuentaRepository.findAll();

            response.getMensajeResponse().setEstadosCuentas(listEstados);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados Correctamenete");
            return response;
        }catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MensajeResponseRest listCuenta() {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            List<TipoCuentaEntity> tipoCuentaEntities = tipoCuentaRepository.findAll();

            response.getMensajeResponse().setTipoCuentas(tipoCuentaEntities);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados Correctamenete");
            return response;
        }catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MensajeResponseRest listEntidad() {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            List<TipoEntidadEntity> tipoEntidadEntityList = tipoEntidad.findAll();

            response.getMensajeResponse().setTipoEntidad(tipoEntidadEntityList);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados Correctamenete");
            return response;
        }catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MensajeResponseRest lisTipoTransacciones() {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            List<TipoTransccionEntity> tipoTransccionEntities = transaccionesRepository.findAll();

            response.getMensajeResponse().setTipoTransacciones(tipoTransccionEntities);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados Correctamenete");
            return response;
        }catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostConstruct
    public void crearEstadosCuenta() {
        if (estadoCuentaRepository.count() > 0) {
            return;
        }

        EstadosCuentaEntity entity1 = new EstadosCuentaEntity();
        entity1.setIdCuenta(1L);
        entity1.setNombreEstado("Activa");
        estadoCuentaRepository.save(entity1);

        EstadosCuentaEntity entity2 = new EstadosCuentaEntity();
        entity2.setIdCuenta(2L);
        entity2.setNombreEstado("Inactiva");
        estadoCuentaRepository.save(entity2);

        EstadosCuentaEntity entity3 = new EstadosCuentaEntity();
        entity3.setIdCuenta(3L);
        entity3.setNombreEstado("Cancelada");
        estadoCuentaRepository.save(entity3);
    }

    @PostConstruct
    public void crearTipoCuentas() {
        if (tipoCuentaRepository.count() > 0) {
            return;
        }

        TipoCuentaEntity entity1 = new TipoCuentaEntity();
        entity1.setIdCuenta(1L);
        entity1.setNombreCuenta("Ahorro");
        tipoCuentaRepository.save(entity1);

        TipoCuentaEntity entity2 = new TipoCuentaEntity();
        entity2.setIdCuenta(2L);
        entity2.setNombreCuenta("Corriente");
        tipoCuentaRepository.save(entity2);
    }

    @PostConstruct
    public void crearTiposEntidad() {
        if (tipoEntidad.count() > 0) {
            return;
        }

        TipoEntidadEntity entity1 = new TipoEntidadEntity();
        entity1.setSigla("CC");
        entity1.setNombreEntidad("Cédula de Ciudadanía");
        tipoEntidad.save(entity1);

        TipoEntidadEntity entity2 = new TipoEntidadEntity();
        entity2.setSigla("CE");
        entity2.setNombreEntidad("Cédula de Extranjería");
        tipoEntidad.save(entity2);

        TipoEntidadEntity entity3 = new TipoEntidadEntity();
        entity3.setSigla("TI");
        entity3.setNombreEntidad("Tarjeta de Identidad");
        tipoEntidad.save(entity3);
    }

    @PostConstruct
    public void crearTiposTransaccion() {
        if (transaccionesRepository.count() > 0) {
            return;
        }

        TipoTransccionEntity entity1 = new TipoTransccionEntity();
        entity1.setNombreTransaccion("Consignacion");
        transaccionesRepository.save(entity1);

        TipoTransccionEntity entity2 = new TipoTransccionEntity();
        entity2.setNombreTransaccion("Retiro");
        transaccionesRepository.save(entity2);

        TipoTransccionEntity entity3 = new TipoTransccionEntity();
        entity3.setNombreTransaccion("Transferencia");
        transaccionesRepository.save(entity3);
    }

}
