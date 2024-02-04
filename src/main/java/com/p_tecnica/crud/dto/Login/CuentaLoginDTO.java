package com.p_tecnica.crud.dto.Login;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentaLoginDTO {
    private Long tipoCuenta;
    private BigDecimal saldo;
    private String idCliente;
    private String contrasena;
    private Long idEstadoCuenta;
}
