package com.p_tecnica.crud.services.impl;

import com.p_tecnica.crud.constantes.Constantes;
import com.p_tecnica.crud.dto.Login.ClienteEntityloginDTO;
import com.p_tecnica.crud.exception.ExcepcionPersonalizada;
import com.p_tecnica.crud.model.ClienteEntity;
import com.p_tecnica.crud.model.CuentasEntity;
import com.p_tecnica.crud.model.TipoEntidadEntity;
import com.p_tecnica.crud.repository.ClienteRepository;
import com.p_tecnica.crud.repository.Cuentasrepository;
import com.p_tecnica.crud.repository.TipoEntidadEntityRepository;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository cliente;
    private final Cuentasrepository cuenta;
    private final TipoEntidadEntityRepository tipoEntidad;
    private final ModelMapper modelMapper;
    private String mensaje;

    public ClienteServiceImpl(ClienteRepository cliente, Cuentasrepository cuenta, TipoEntidadEntityRepository tipoEntidad, ModelMapper modelMapper) {
        this.cliente = cliente;
        this.cuenta = cuenta;
        this.tipoEntidad = tipoEntidad;
        this.modelMapper = modelMapper;
    }
    @Override
    public  MensajeResponseRest crateCliente(ClienteEntityloginDTO clienteEntityloginDTO) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            this.mensaje = "";
            Optional<ClienteEntity> clienteEntity = cliente.findById(clienteEntityloginDTO.getNumIdent());
            this.mensaje += clienteEntity.isPresent() ? "El cliente: " + clienteEntity.get().getNombre() + "  Ya existe" : "";

            Optional<TipoEntidadEntity> tipoEntidadEntity = tipoEntidad.findBySigla(clienteEntityloginDTO.getTipoEntidad());
            this.mensaje += !tipoEntidadEntity.isPresent() ? " Tipo de entodad no valida," : "";

            if (clienteEntity.isPresent() || !tipoEntidadEntity.isPresent()) {
                response.setMetadata(Constantes.TextRespuesta, mensaje);
                return response;
            }

            ClienteEntity newCliente = modelMapper.map(clienteEntityloginDTO, ClienteEntity.class);

            LocalDate fNacimiento = LocalDate.parse(clienteEntityloginDTO.getFnacimiento(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            int age = Period.between(fNacimiento, LocalDate.now()).getYears();

            newCliente.setFCreacion(LocalDateTime.now());
            newCliente.setFEdicion(LocalDateTime.now());
            newCliente.setTipoEntidad(tipoEntidadEntity.get());
            newCliente.setFNacimiento(fNacimiento);

            if (age >= 18) {
                cliente.save(newCliente);
            } else {
                response.setMetadata(Constantes.TextRespuesta, "Cliente no creado: Es menor de edad");
                return response;
            }
            response.getMensajeResponse().setCliente(newCliente);
            response.setMetadata(Constantes.TextRespuesta, Constantes.ClienteCreate);
            return response;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new ExcepcionPersonalizada("Formato de fecha de nacimiento invalido", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MensajeResponseRest listCliente() {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            List<ClienteEntity> listCliente = cliente.findAll();

            response.getMensajeResponse().setClientes(listCliente);
            response.setMetadata(Constantes.TextRespuesta, Constantes.ClienteCreate);
            return response;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MensajeResponseRest buscarCliente(String numIdent) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            Optional<ClienteEntity> buscarCliente = cliente.findById(numIdent);
            if (!buscarCliente.isPresent()){
                response.setMetadata(Constantes.TextRespuesta, "Cliente no reguistrado");
                return response;
            }

            response.getMensajeResponse().setCliente(buscarCliente.get());
            response.setMetadata(Constantes.TextRespuesta, "Cliente encontrado exitosamente");
            return response;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public MensajeResponseRest actualizarCliente(ClienteEntityloginDTO clienteEntityloginDTO) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            this.mensaje = "";
            Optional<ClienteEntity> clienteEntity = cliente.findById(clienteEntityloginDTO.getNumIdent());
            this.mensaje += !clienteEntity.isPresent() ? "El cliente: " + clienteEntityloginDTO.getNumIdent() + " No existe," : "";

            Optional<TipoEntidadEntity> tipoEntidadEntity = tipoEntidad.findBySigla(clienteEntityloginDTO.getTipoEntidad());
            this.mensaje += !tipoEntidadEntity.isPresent() ? " Tipo de entidad no encontrado" : "";

            if (!clienteEntity.isPresent() || !clienteEntity.isPresent() || !tipoEntidadEntity.isPresent()) {
                response.setMetadata(Constantes.TextRespuesta, mensaje);
                return response;
            }

            ClienteEntity editarCliente = modelMapper.map(clienteEntityloginDTO, ClienteEntity.class);

            LocalDate fNacimiento = LocalDate.parse(clienteEntityloginDTO.getFnacimiento(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            int age = Period.between(fNacimiento, LocalDate.now()).getYears();

            editarCliente.setFNacimiento(fNacimiento);
            editarCliente.setFEdicion(LocalDateTime.now());
            editarCliente.setFCreacion(clienteEntity.get().getFCreacion());
            editarCliente.setTipoEntidad(tipoEntidadEntity.get());

            if (clienteEntity.get().getFCreacion() == null ) {
                editarCliente.setFCreacion(LocalDateTime.now());
            }

            if (age >= 18) {
                cliente.save(editarCliente);
            } else {
                response.setMetadata(Constantes.TextRespuesta, "Cliente no puede ser menor de edad");
                return response;
            }

            cliente.save(editarCliente);

            response.getMensajeResponse().setCliente(editarCliente);
            response.setMetadata(Constantes.TextRespuesta, Constantes.ClienteEdit);
            return response;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public MensajeResponseRest eliminarCliente(String numeroIdent) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            this.mensaje = "";
            Optional<ClienteEntity> clienteEntity = cliente.findById(numeroIdent);
            if (!clienteEntity.isPresent()){
                response.setMetadata(Constantes.TextRespuesta, "Cliente no registrado");
                return response;
            }

            List<CuentasEntity> cuentasEntity = cuenta.findByClienteId(numeroIdent);
            if (!cuentasEntity.isEmpty()){
                response.getMensajeResponse().setCuentas(cuentasEntity);
                response.setMetadata(Constantes.TextRespuesta, "El cliente tiene cuentas vinculadas");
                return response;
            }

            cliente.delete(clienteEntity.get());

            response.getMensajeResponse().setCliente(clienteEntity.get());
            response.setMetadata(Constantes.TextRespuesta, "Cliente eliminado correctamente");
            return response;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
