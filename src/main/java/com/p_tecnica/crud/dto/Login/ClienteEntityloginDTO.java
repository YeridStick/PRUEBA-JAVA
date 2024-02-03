package com.p_tecnica.crud.dto.Login;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class ClienteEntityloginDTO {
    private String numIdent;
    private String nombre;
    private String apellido;
    private String correo;
    private String fnacimiento;
    private String tipoEntidad;
    private Boolean estadoCliente;
}
