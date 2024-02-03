package com.p_tecnica.crud.services.impl;

import com.p_tecnica.crud.constantes.Constantes;
import com.p_tecnica.crud.dto.Login.TransaccionLoginDTO;
import com.p_tecnica.crud.exception.ExcepcionPersonalizada;
import com.p_tecnica.crud.model.*;
import com.p_tecnica.crud.repository.Cuentasrepository;
import com.p_tecnica.crud.repository.TipoTransaccionesRepository;
import com.p_tecnica.crud.repository.TransaccioneRepository;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.TransaccionesServices;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransaccionesServicesImpl implements TransaccionesServices {
    private final TransaccioneRepository transaccioneRepository;
    private final TipoTransaccionesRepository transaccionesRepository;
    private final Cuentasrepository cuentasrepository;
    private final ModelMapper modelMapper;
    private String mensaje;

    public TransaccionesServicesImpl(TransaccioneRepository transaccioneRepository, TipoTransaccionesRepository transaccionesRepository, Cuentasrepository cuentasrepository, ModelMapper modelMapper) {
        this.transaccioneRepository = transaccioneRepository;
        this.transaccionesRepository = transaccionesRepository;
        this.cuentasrepository = cuentasrepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public MensajeResponseRest createTransaccion(TransaccionLoginDTO transaccionLoginDTO) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            this.mensaje = "";

            Optional<CuentasEntity> cuentaExistente = cuentasrepository.findByNumeroPin(transaccionLoginDTO.getNumeroCuenta());
            this.mensaje += !cuentaExistente.isPresent() ? "Cuenta no registrada," : "";

            Optional<TipoTransccionEntity> tipoTransaccionEntity = transaccionesRepository.findById(transaccionLoginDTO.getTipoTransccionEntity());
            this.mensaje += !tipoTransaccionEntity.isPresent() ? "Tipo de transaccion no es valida," : "";

            if (!cuentaExistente.isPresent() || !tipoTransaccionEntity.isPresent()) {
                response.setMetadata(Constantes.TextRespuesta, mensaje);
                return response;
            }

            TransaccionesEntity transacciones = modelMapper.map(transaccionLoginDTO, TransaccionesEntity.class);
            transacciones.setTipoTransccionEntity(tipoTransaccionEntity.get());
            transacciones.setFTransaccion(LocalDateTime.now());

            switch (tipoTransaccionEntity.get().getNombreTransaccion()) {
                case "Consignacion":
                    transaccionLoginDTO.getNumeroCuenta();
                    transaccionLoginDTO.setNumeroCuentaDestino(transaccionLoginDTO.getNumeroCuenta());
                    Long valorConsignacion = cuentaExistente.get().getSaldo() + transaccionLoginDTO.getValorTransaccion();
                    cuentaExistente.get().setSaldo(valorConsignacion);
                    cuentasrepository.save(cuentaExistente.get());
                    break;

                case "Retiro":
                    transaccionLoginDTO.getNumeroCuenta();
                    transaccionLoginDTO.setNumeroCuentaDestino(transaccionLoginDTO.getNumeroCuenta());
                    Long valorRetiro = cuentaExistente.get().getSaldo() - transaccionLoginDTO.getValorTransaccion();
                    cuentaExistente.get().setSaldo(valorRetiro);
                    cuentasrepository.save(cuentaExistente.get());
                    break;

                case "Transferencia":
                    Optional<CuentasEntity> cuentaDestino = cuentasrepository.findByNumeroPin(transaccionLoginDTO.getNumeroCuentaDestino());
                    if ("Transferencia".equals(tipoTransaccionEntity.get().getNombreTransaccion())) {
                        if (cuentaExistente.get().equals(cuentaDestino.get())) {
                            this.mensaje += "No puedes transferir a la misma cuenta.";
                        }
                        this.mensaje += !cuentaDestino.isPresent() ? "Cuenta destino no válida," : "";
                    }

                    if (!cuentaExistente.isPresent() || !tipoTransaccionEntity.isPresent() || !cuentaDestino.isPresent()) {
                        response.setMetadata(Constantes.TextRespuesta, mensaje);
                        return response;
                    }

                    if (!cuentaDestino.isPresent() || cuentaDestino.get() == cuentaExistente.get()) {
                        this.mensaje += " Cuenta destino no registrada o es la misma que la cuenta existente,";
                    } else {
                        Long valorDestino = cuentaDestino.get().getSaldo() + transaccionLoginDTO.getValorTransaccion();
                        cuentaDestino.get().setSaldo(valorDestino);

                        Long saldoRestante = cuentaExistente.get().getSaldo() - transaccionLoginDTO.getValorTransaccion();
                        cuentaExistente.get().setSaldo(saldoRestante);

                        cuentasrepository.save(cuentaExistente.get());
                        cuentasrepository.save(cuentaDestino.get());
                    }
                    transacciones.setCuentasEntityDestino(cuentaDestino.get());
                    break;

                default:
                    break;
            }

            response.setMetadata(Constantes.TextRespuesta, "Transacción creada correctamente");
            return response;

        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
