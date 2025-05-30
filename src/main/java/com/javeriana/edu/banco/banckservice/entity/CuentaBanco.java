package com.javeriana.edu.banco.banckservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cuenta_Banco")
public class CuentaBanco {
    @Id
    @GeneratedValue
    private UUID id;

    private double saldo;

    @Column(unique = true)
    private Integer cedula;
}

