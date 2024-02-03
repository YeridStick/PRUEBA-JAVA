package com.p_tecnica.crud.services;

import com.p_tecnica.crud.response.MensajeResponseRest;

public interface TiposServices {
    MensajeResponseRest listEstadoCuenta();
    MensajeResponseRest listCuenta();
    MensajeResponseRest listEntidad();
    MensajeResponseRest lisTipoTransacciones();
}
