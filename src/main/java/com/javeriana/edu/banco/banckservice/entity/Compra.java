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
@Table(name = "Compra")
public class Compra {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "cliente_cedula", unique = false)
    private Integer clienteCedula;

    private Instant fecha;
}

