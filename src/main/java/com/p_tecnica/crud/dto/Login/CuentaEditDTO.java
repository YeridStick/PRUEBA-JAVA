package com.p_tecnica.crud.dto.Login;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentaEditDTO {
    private Long idCuenta;
    private Long tipoCuenta;
    private BigDecimal saldo;
    private String idCliente;
    private Long idEstadoCuenta;
}
