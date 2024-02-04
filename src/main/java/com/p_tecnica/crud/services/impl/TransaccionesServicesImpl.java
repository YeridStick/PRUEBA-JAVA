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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransaccionesServicesImpl implements TransaccionesServices {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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

            // Validar existencia de la cuenta
            Optional<CuentasEntity> cuentaExistente = validarExistenciaCuenta(transaccionLoginDTO.getNumeroCuenta());
            if (cuentaExistente.isPresent()) {
                if (!passwordEncoder.matches(transaccionLoginDTO.getContrasena().trim(), cuentaExistente.get().getContrasena())) {
                    response.setMetadata(Constantes.TextRespuestaNo, "Contraseña incorrecta");
                    return response;
                }
            } else {
                response.setMetadata(Constantes.TextRespuesta, mensaje);
                return response;
            }

            // Validar tipo de transacción
            Optional<TipoTransccionEntity> tipoTransaccionEntity = transaccionesRepository.findById(transaccionLoginDTO.getTipoTransaccionEntity());
            if (!tipoTransaccionEntity.isPresent()) {
                response.setMetadata(Constantes.TextRespuesta, "Tipo de transacción no válida");
                return response;
            }

            // Lógica de transacción
            TransaccionesEntity transacciones = modelMapper.map(transaccionLoginDTO, TransaccionesEntity.class);
            transacciones.setTipoTransaccion(tipoTransaccionEntity.get());
            transacciones.setFechaTransaccion(LocalDateTime.now());

            // Calcular el GMF
            double gmfPagar = calcularGMF(transaccionLoginDTO.getValorTransaccion());

            switch (tipoTransaccionEntity.get().getNombreTransaccion()) {
                case Constantes.TRANSACCION_CONSIGNACION:
                    realizarConsignacion(cuentaExistente.get(), transaccionLoginDTO.getValorTransaccion());
                    break;

                case Constantes.TRANSACCION_RETIRO:
                    if (!validarSaldoSuficiente(cuentaExistente.get(), transaccionLoginDTO.getValorTransaccion())) {
                        response.setMetadata(Constantes.TextRespuesta, "Saldo insuficiente");
                        return response;
                    }
                    realizarRetiro(cuentaExistente.get(), transaccionLoginDTO.getValorTransaccion());
                    break;

                case Constantes.TRANSACCION_TRANSFERENCIA:
                    Optional<CuentasEntity> cuentaDestino = validarExistenciaCuenta(transaccionLoginDTO.getNumeroCuentaDestino());
                    if (!validarCuentaDestino(cuentaDestino, cuentaExistente)) {
                        response.setMetadata(Constantes.TextRespuesta, mensaje);
                        return response;
                    }

                    if (!validarSaldoSuficiente(cuentaExistente.get(), transaccionLoginDTO.getValorTransaccion())) {
                        response.setMetadata(Constantes.TextRespuesta, "Saldo insuficiente");
                        return response;
                    }

                    realizarTransferencia(cuentaExistente.get(), cuentaDestino.get(), transaccionLoginDTO.getValorTransaccion());
                    transacciones.setCuentaDestino(cuentaDestino.get());
                    break;

                default:
                    break;
            }

            response.setMetadata(Constantes.TextRespuesta, "Transacción creada correctamente");
            return response;

        } catch (ExcepcionPersonalizada e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private double calcularGMF(double valorTransaccion) {
        double montoExento = 14844000.0; // Monto exento en UVT (350 UVT en 2023)
        double tasaGMF = 0.004; // Tasa GMF: 4x1000
        double resultado = valorTransaccion - montoExento;

        // Si el resultado es menor o igual a 0, no se aplica GMF
        return resultado <= 0 ? 0 : resultado * tasaGMF;
    }

    private Optional<CuentasEntity> validarExistenciaCuenta(String numeroCuenta) {
        Optional<CuentasEntity> cuentaExistente = cuentasrepository.findByNumeroPin(numeroCuenta);
        this.mensaje += !cuentaExistente.isPresent() ? "Cuenta no registrada," : "";
        return cuentaExistente;
    }

    private boolean validarSaldoSuficiente(CuentasEntity cuenta, Long valorTransaccion) {
        return !cuenta.getTipoCuentaEntity().getNombreCuenta().equals("Ahorro") ||
                cuenta.getSaldo() >= valorTransaccion;
    }

    private boolean validarCuentaDestino(Optional<CuentasEntity> cuentaDestino, Optional<CuentasEntity> cuentaExistente) {
        if (!cuentaDestino.isPresent() || cuentaDestino.get().equals(cuentaExistente.orElse(null))) {
            this.mensaje += "Cuenta destino no válida o es la misma que la cuenta existente,";
            return false;
        }
        return true;
    }

    private void realizarConsignacion(CuentasEntity cuenta, Long valorTransaccion) {
        cuenta.setSaldo(cuenta.getSaldo() + valorTransaccion);
        cuentasrepository.save(cuenta);
    }

    private void realizarRetiro(CuentasEntity cuenta, Long valorTransaccion) {
        if (cuenta.getTipoCuentaEntity().getNombreCuenta().equals("Ahorro")) {
            if (cuenta.getSaldo() >= valorTransaccion) {
                double gmfPagar = calcularGMF(valorTransaccion);
                cuenta.setSaldo(cuenta.getSaldo() - valorTransaccion - gmfPagar);
                cuentasrepository.save(cuenta);
            } else {
                throw new ExcepcionPersonalizada("Saldo insuficiente para realizar el retiro", HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            double gmfPagar = calcularGMF(valorTransaccion);
            cuenta.setSaldo(cuenta.getSaldo() - valorTransaccion - gmfPagar);
            cuentasrepository.save(cuenta);
        }
    }

    private void realizarTransferencia(CuentasEntity cuentaOrigen, CuentasEntity cuentaDestino, Long valorTransaccion) {
        if (cuentaOrigen.getTipoCuentaEntity().getNombreCuenta().equals("Ahorro")) {
            if (cuentaOrigen.getSaldo() >= valorTransaccion) {
                double gmfPagar = calcularGMF(valorTransaccion);
                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + valorTransaccion);
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - valorTransaccion - gmfPagar);
                cuentasrepository.save(cuentaOrigen);
                cuentasrepository.save(cuentaDestino);
            } else {
                throw new ExcepcionPersonalizada("Saldo insuficiente para realizar la transferencia", HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            double gmfPagar = calcularGMF(valorTransaccion);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + valorTransaccion);
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - valorTransaccion - gmfPagar);
            cuentasrepository.save(cuentaOrigen);
            cuentasrepository.save(cuentaDestino);
        }
    }

}
