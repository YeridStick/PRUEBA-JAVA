package com.p_tecnica.crud.controller;

import com.p_tecnica.crud.dto.Login.CuentaEditDTO;
import com.p_tecnica.crud.dto.Login.CuentaLoginDTO;
import com.p_tecnica.crud.response.MensajeResponseRest;
import com.p_tecnica.crud.services.CuentasServices;
import com.p_tecnica.crud.services.TiposServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cuenta")
/*@CrossOrigin(origins = "http://localhost:3000/")*/
@Tag(name = "Controlador de cuenta")
public class CuentaController {
    private final CuentasServices cuentasServices;
    private final TiposServices tipoCuentaServices;
    private final TiposServices estadosCuentasServices;

    public CuentaController(CuentasServices cuentasServices, TiposServices tipoCuentaServices, TiposServices estadosCuentasServices) {
        this.cuentasServices = cuentasServices;
        this.tipoCuentaServices = tipoCuentaServices;
        this.estadosCuentasServices = estadosCuentasServices;
    }

    @GetMapping("listar")
    public ResponseEntity<MensajeResponseRest> listCuenta() {
        return new ResponseEntity<>(cuentasServices.listCuneta(), HttpStatus.OK);
    }

    @PostMapping("crear")
    public ResponseEntity<MensajeResponseRest> createCuenta(@RequestBody CuentaLoginDTO cuentaLoginDTO) {
        return new ResponseEntity<>(cuentasServices.createCuenta(cuentaLoginDTO), HttpStatus.OK);
    }

    @PutMapping("editar")
    @Operation(summary = "Crear un tipo de entidad")
    public ResponseEntity<MensajeResponseRest> editarCuenta(@RequestBody CuentaEditDTO cuentaEditDTO){
        return new ResponseEntity<>(cuentasServices.editarCuenta(cuentaEditDTO), HttpStatus.OK);
    }

    @GetMapping("tipo/listar")
    @Operation(summary = "Obtener listado de tipo de entidades", description = "Devuelve una lista de entidades")
    public ResponseEntity<MensajeResponseRest> listCuentas(){
        return new ResponseEntity<>(tipoCuentaServices.listCuenta(), HttpStatus.OK);
    }

    @GetMapping("estados/listar")
    @Operation(summary = "Obtener listado de tipo de entidades", description = "Devuelve una lista de entidades")
    public ResponseEntity<MensajeResponseRest> listEstados(){
        return new ResponseEntity<>(estadosCuentasServices.listEstadoCuenta(), HttpStatus.OK);
    }

}
