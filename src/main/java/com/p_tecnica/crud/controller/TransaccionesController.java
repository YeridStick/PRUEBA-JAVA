package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.dto.Login.TransaccionLoginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.TiposServices;
import com.p_tecnica.crud.services.TransaccionesServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacciones")
/*@CrossOrigin(origins = "http://localhost:3000/")*/
@Tag(name = "Transacciones")
public class TransaccionesController {
    private final TiposServices tipoTransaccionesServices;
    private final TransaccionesServices transaccionesServices;

    public TransaccionesController(TiposServices tipoTransaccionesServices, TransaccionesServices transaccionesServices) {
        this.tipoTransaccionesServices = tipoTransaccionesServices;
        this.transaccionesServices = transaccionesServices;
    }

    @PostMapping("crear")
    @Operation(summary = "Crear un tipo de tansaccion")
    public ResponseEntity<MensajeResponseRest> crearTransaccion(@RequestBody TransaccionLoginDTO transaccionLoginDTO){
        return new ResponseEntity<>(transaccionesServices.createTransaccion(transaccionLoginDTO), HttpStatus.OK);
    }

    @GetMapping("tipo/listar")
    @Operation(summary = "Obtener listado de tipo de entidades", description = "Devuelve una lista de entidades")
    public ResponseEntity<MensajeResponseRest> listTransaccion(){
        return new ResponseEntity<>(tipoTransaccionesServices.lisTipoTransacciones(), HttpStatus.OK);
    }
}
