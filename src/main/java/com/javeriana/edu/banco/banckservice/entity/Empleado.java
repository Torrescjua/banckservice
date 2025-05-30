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
@Table(name = "Empleado")
public class Empleado {
    @Id
    @GeneratedValue
    private UUID id;

    private String rol;
    private String nombre;
    private String apellido;
}
