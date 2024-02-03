package com.p_tecnica.crud.services;

import com.p_tecnica.crud.dto.Login.CuentaEditDTO;
import com.p_tecnica.crud.dto.Login.CuentaLoginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;

public interface CuentasServices {
    MensajeResponseRest listCuneta();
    MensajeResponseRest createCuenta(CuentaLoginDTO cuentaLoginDTO);
    MensajeResponseRest editarCuenta(CuentaEditDTO cuentaEditDTO);
}
