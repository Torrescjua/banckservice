package com.javeriana.edu.banco.banckservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Transaccion")
public class Transaccion {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne @JoinColumn(name = "compra_id")
    private Compra compra;

    @Column(name = "cliente_cedula")
    private Integer clienteCedula;

    @ManyToOne @JoinColumn(name = "cuenta_id")
    private CuentaBanco cuenta;

    private double monto;
    private Instant fecha;

    private boolean aprobada;
}
