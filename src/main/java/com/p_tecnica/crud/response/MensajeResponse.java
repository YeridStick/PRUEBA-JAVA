package com.p_tecnica.crud.response;

import com.p_tecnica.crud.model.*;
import lombok.Data;

import java.util.List;

@Data
public class MensajeResponse {
    private List<TipoEntidadEntity> tipoEntidad;
    private TipoEntidadEntity entidad;
    private TipoCuentaEntity tipoCuenta;
    private EstadosCuentaEntity estadosCuenta;
    private List<EstadosCuentaEntity> estadosCuentas;
    private List<TipoCuentaEntity> tipoCuentas;
    private ClienteEntity cliente;
    private List<ClienteEntity> clientes;
    private List<CuentasEntity> cuentas;
    private CuentasEntity cuenta;
    private TipoTransccionEntity tipoTransaccion;
    private List<TipoTransccionEntity> tipoTransacciones;
    private TransaccionesEntity transaccion;
    private List<TipoTransccionEntity> transacciones;

    /*private List<UserOutDTO> users;
    private List<RolesOutDTO> rolesUser;*/
}
