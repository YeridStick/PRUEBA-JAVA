package com.p_tecnica.crud.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUENTAS")
public class CuentasEntity {
    @Column(name = "ID_CUENTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long idCuenta;

    @Column(name = "NUMERO_CUENTA")
    private String numeroPin;

    @ManyToOne
    @JoinColumn(name = "ESTADOS_CUENTA")
    private EstadosCuentaEntity estadosCuenta;

    @Column(name = "SALDO")
    private Double saldo;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fCreacion;

    @Column(name = "FECHA_EDICION")
    private LocalDateTime fEdicion;

    @ManyToOne
    @JoinColumn(name = "TIPOCUENTA")
    private TipoCuentaEntity tipoCuentaEntity;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private ClienteEntity clienteEntity ;

    @Column(name = "CONTRASENA")
    private String contrasena;

}
