package com.p_tecnica.crud.services.impl;

import com.p_tecnica.crud.constantes.Constantes;
import com.p_tecnica.crud.dto.Login.ClienteEntityloginDTO;
import com.p_tecnica.crud.dto.Login.CuentaEditDTO;
import com.p_tecnica.crud.dto.Login.CuentaLoginDTO;
import com.p_tecnica.crud.exception.ExcepcionPersonalizada;
import com.p_tecnica.crud.model.*;
import com.p_tecnica.crud.repository.ClienteRepository;
import com.p_tecnica.crud.repository.Cuentasrepository;
import com.p_tecnica.crud.repository.EstadoCuentaRepository;
import com.p_tecnica.crud.repository.TipoCuentaRepository;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.CuentasServices;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CuentasServicesImpl implements CuentasServices {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Cuentasrepository cuentasrepository;
    private final ClienteRepository clienteRepository;
    private final TipoCuentaRepository tipoCuentaRepository;
    private final EstadoCuentaRepository estadoCuentaRepository;
    private final ModelMapper modelMapper;

    private String mensaje;

    private static final Random random = new Random();
    private String numeroPin;

    public CuentasServicesImpl(Cuentasrepository cuentasrepository, ClienteRepository clienteRepository, TipoCuentaRepository tipoCuentaRepository, EstadoCuentaRepository estadoCuentaRepository, ModelMapper modelMapper) {
        this.cuentasrepository = cuentasrepository;
        this.clienteRepository = clienteRepository;
        this.tipoCuentaRepository = tipoCuentaRepository;
        this.estadoCuentaRepository = estadoCuentaRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public MensajeResponseRest createCuenta(CuentaLoginDTO cuentaLoginDTO) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            ClienteEntity clienteEntity = clienteRepository.findById(cuentaLoginDTO.getIdCliente())
                    .orElseThrow(() -> new ExcepcionPersonalizada("Cliente no registrado", HttpStatus.NOT_FOUND));

            TipoCuentaEntity tipoCuenta = tipoCuentaRepository.findById(cuentaLoginDTO.getTipoCuenta())
                    .orElseThrow(() -> new ExcepcionPersonalizada("Tipo de cuenta no válida", HttpStatus.NOT_FOUND));

            EstadosCuentaEntity estadosCuenta = estadoCuentaRepository.findById(cuentaLoginDTO.getIdEstadoCuenta())
                    .orElseThrow(() -> new ExcepcionPersonalizada("Estado de cuenta no válido", HttpStatus.NOT_FOUND));

            CuentasEntity cuentasEntity = modelMapper.map(cuentaLoginDTO, CuentasEntity.class);
            cuentasEntity.setContrasena(passwordEncoder.encode(cuentaLoginDTO.getContrasena().trim()));
            cuentasEntity.setClienteEntity(clienteEntity);
            cuentasEntity.setTipoCuentaEntity(tipoCuenta);
            cuentasEntity.setEstadosCuenta(estadosCuenta);
            cuentasEntity.setFCreacion(LocalDateTime.now());
            cuentasEntity.setFEdicion(LocalDateTime.now());

            // Generar número de pin
            this.numeroPin = (cuentaLoginDTO.getTipoCuenta() == 1) ? "53" : "33";
            for (int i = 1; i <= 8; i++) {
                int numero = this.random.nextInt(10);
                this.numeroPin += Integer.toString(numero);
            }
            cuentasEntity.setNumeroPin(this.numeroPin);

            // Validar saldo de cuentas de ahorro
            if (cuentasEntity.getSaldo() < 0 && cuentaLoginDTO.getTipoCuenta() == 1) {
                response.setMetadata(Constantes.TextRespuestaNo, "Las cuentas de ahorro no pueden tener un saldo inferior a $0.00");
                return response;
            }

            cuentasrepository.save(cuentasEntity);

            response.getMensajeResponse().setCuenta(cuentasEntity);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados exitosamente");
            return response;

        } catch (ExcepcionPersonalizada e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MensajeResponseRest editarCuenta(CuentaEditDTO cuentaEditDTO) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            this.mensaje = "";

            Optional<CuentasEntity> cuentaExistente = cuentasrepository.findById(cuentaEditDTO.getIdCuenta());
            this.mensaje += !cuentaExistente.isPresent() ? " Cuenta no registrada," : "";

            Optional<ClienteEntity> clienteEntity = clienteRepository.findById(cuentaEditDTO.getIdCliente());
            this.mensaje += !clienteEntity.isPresent() ? " Cliente no se encuentra registrado," : "";

            Optional<TipoCuentaEntity> tipoCuenta = tipoCuentaRepository.findById(cuentaEditDTO.getTipoCuenta());
            this.mensaje += !tipoCuenta.isPresent() ? " Tipo de cuenta no válida," : "";

            Optional<EstadosCuentaEntity> estadosCuenta = estadoCuentaRepository.findById(cuentaEditDTO.getIdEstadoCuenta());
            this.mensaje += !estadosCuenta.isPresent() ? " Estado de cuenta no es válido," : "";

            if (!cuentaExistente.isPresent() || !clienteEntity.isPresent() || !estadosCuenta.isPresent()) {
                response.setMetadata(Constantes.TextRespuesta, mensaje);
                return response;
            }

            CuentasEntity cuentasEntity = modelMapper.map(cuentaEditDTO, CuentasEntity.class);
            cuentasEntity.setContrasena(passwordEncoder.encode(cuentaEditDTO.getContrasena().trim()));
            cuentasEntity.setClienteEntity(clienteEntity.get());
            cuentasEntity.setTipoCuentaEntity(tipoCuenta.get());
            cuentasEntity.setEstadosCuenta(estadosCuenta.get());
            cuentasEntity.setNumeroPin(cuentaExistente.get().getNumeroPin());

            LocalDateTime date = cuentaExistente.get().getFCreacion();

            if (date == null) {
                cuentasEntity.setFCreacion(LocalDateTime.now());
            } else {
                cuentasEntity.setFCreacion(date);
            }

            cuentasEntity.setFEdicion(LocalDateTime.now());

            // Validar saldo de cuentas de ahorro
            if (cuentasEntity.getSaldo() < 0 && cuentaEditDTO.getTipoCuenta() == 1) {
                response.setMetadata(Constantes.TextRespuestaNo, "Las cuentas de ahorro no pueden tener un saldo inferior a $0.00");
                return response;
            }

            cuentasrepository.save(cuentasEntity);

            response.getMensajeResponse().setCuenta(cuentasEntity);
            response.setMetadata(Constantes.TextRespuesta, "Datos editados exitosamente");
            return response;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MensajeResponseRest listCuneta() {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            List<CuentasEntity> listCuenta = cuentasrepository.findAll();

            response.getMensajeResponse().setCuentas(listCuenta);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados exitosamente");
            return response;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
