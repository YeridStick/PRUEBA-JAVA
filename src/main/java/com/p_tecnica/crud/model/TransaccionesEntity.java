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
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_TRANSACCION")
    private Long idTransaccion;

    @ManyToOne
    @JoinColumn(name = "TIPO_TRANSACCION_ID")
    private TipoTransccionEntity tipoTransaccion;

    @Column(name = "VALOR_TRANSACCION")
    private Long valorTransaccion;

    @Column(name = "FECHA_TRANSACCION")
    private LocalDateTime fechaTransaccion;

    @ManyToOne
    @JoinColumn(name = "CUENTA_ORIGEN_ID")
    private CuentasEntity cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "CUENTA_DESTINO_ID")
    private CuentasEntity cuentaDestino;

    @Column(name = "GMF_PAGADO")
    private Double gmfPagado;
}
