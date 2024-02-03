package com.p_tecnica.crud.services;

import com.p_tecnica.crud.dto.Login.TransaccionLoginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;

public interface TransaccionesServices {
    MensajeResponseRest createTransaccion(TransaccionLoginDTO transaccionLoginDTO);
}
