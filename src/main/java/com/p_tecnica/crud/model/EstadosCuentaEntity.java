package com.p_tecnica.crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ESTADOS_CUENTA")
public class EstadosCuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESTADO_ESTADO")
    private Long idCuenta;

    @Column(name = "NOMBRE_ESTADO", unique = true)
    private String nombreEstado;
}
