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
@Table(name = "TIPO_CUENTA")
public class TipoCuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCUENTA")
    private Long idCuenta;

    @Column(name = "NOMBRECUENTA", unique = true)
    private String nombreCuenta;

}
