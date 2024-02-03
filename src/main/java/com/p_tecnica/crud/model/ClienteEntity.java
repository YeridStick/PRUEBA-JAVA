package com.p_tecnica.crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENTE")
public class ClienteEntity {
    @Id
    @Column(name = "NUMEROIDENTIFICACION")
    private String numIdent;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "ESATDO")
    private Boolean estadoCliente;

    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fNacimiento;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fCreacion;

    @Column(name = "FECHA_EDICION")
    private LocalDateTime fEdicion;

    @ManyToOne
    @JoinColumn(name = "TIPOIDENTIDAD")
    private TipoEntidadEntity tipoEntidad;
}
