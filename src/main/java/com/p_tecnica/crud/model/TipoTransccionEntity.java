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
@Table(name = "TIPO_TRANSACCION")
public class TipoTransccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTIPOTRANSACCION")
    private Long idEntidad;

    @Column(name = "NOMBRE", unique = true)
    private String nombreTransaccion;
}
