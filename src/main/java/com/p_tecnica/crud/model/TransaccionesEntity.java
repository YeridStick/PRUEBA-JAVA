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
@Table(name = "TRANSACCIONES")
public class TransaccionesEntity {
    @Column(name = "ID_TRANSACCION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long idCuenta;

    @ManyToOne
    @JoinColumn(name = "TIPO_TRANSACCION")
    private  TipoTransccionEntity tipoTransccionEntity;

    @Column(name = "VALOR_TRANSACCION")
    private Long valorTransaccion;

    @Column(name = "FECHA_TRANSCCION")
    private LocalDateTime fTransaccion;

    @ManyToOne
    @JoinColumn(name = "CUENTA_ID")
    private  CuentasEntity cuentasEntity;

    @ManyToOne
    @JoinColumn(name = "CUENTA_DESTINO")
    private  CuentasEntity cuentasEntityDestino;
}
