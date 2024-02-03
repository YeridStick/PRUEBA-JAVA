package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.TiposServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipo-entidad")
/*@CrossOrigin(origins = "http://localhost:3000/")*/
@Tag(name = "Tipo Documento Entidad")
public class TipoEntidadController {
    private final TiposServices tipoEntidadServices;

    public TipoEntidadController(TiposServices tipoEntidadServices) {
        this.tipoEntidadServices = tipoEntidadServices;
    }

    @GetMapping("listar")
    @Operation(summary = "Obtener listado de tipo de entidades", description = "Devuelve una lista de entidades")
    public ResponseEntity<MensajeResponseRest> listEntidad(){
        return new ResponseEntity<>(tipoEntidadServices.listEntidad(), HttpStatus.OK);
    }

}
