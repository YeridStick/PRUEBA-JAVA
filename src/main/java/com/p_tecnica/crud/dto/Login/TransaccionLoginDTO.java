package com.p_tecnica.crud.dto.Login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransaccionLoginDTO {
    private Long valorTransaccion;
    private Long tipoTransccionEntity;
    private String numeroCuenta;
    private String numeroCuentaDestino;
}
