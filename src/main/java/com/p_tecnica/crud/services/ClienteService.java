package com.p_tecnica.crud.services;

import com.p_tecnica.crud.dto.Login.ClienteEntityloginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;

public interface ClienteService {
    MensajeResponseRest crateCliente(ClienteEntityloginDTO clienteEntityloginDTO);
    MensajeResponseRest actualizarCliente(ClienteEntityloginDTO clienteEntityloginDTO);
    MensajeResponseRest listCliente();
    MensajeResponseRest buscarCliente(String numIdent);
    MensajeResponseRest eliminarCliente(String numeroIdent);
}
