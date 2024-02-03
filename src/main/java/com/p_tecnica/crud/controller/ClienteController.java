package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.dto.Login.ClienteEntityloginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
/*@CrossOrigin(origins = "http://localhost:3000/")*/
@Tag(name = "Controlador de Cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("crear")
    public ResponseEntity<MensajeResponseRest> crateCliente(ClienteEntityloginDTO clienteEntityloginDTO) {
        return new ResponseEntity<>(clienteService.crateCliente(clienteEntityloginDTO), HttpStatus.OK);
    }

    @GetMapping("listar")
    public  ResponseEntity<MensajeResponseRest> listCliente() {
        return new ResponseEntity<>(clienteService.listCliente(), HttpStatus.OK);
    }

    @GetMapping("buscar/{numIdent}")
    public ResponseEntity<MensajeResponseRest> buscarCliente(@PathVariable String numIdent) {
        return new ResponseEntity<>(clienteService.buscarCliente(numIdent), HttpStatus.OK);
    }

    @PutMapping("actualizar")
    public ResponseEntity<MensajeResponseRest> actualizarCliente(ClienteEntityloginDTO clienteEntityloginDTO) {
        return new ResponseEntity<>(clienteService.actualizarCliente(clienteEntityloginDTO), HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{numIdent}")
    public ResponseEntity<MensajeResponseRest> eliminarCliente(@PathVariable String numIdent) {
        return new ResponseEntity<>(clienteService.eliminarCliente(numIdent), HttpStatus.OK);
    }
}
