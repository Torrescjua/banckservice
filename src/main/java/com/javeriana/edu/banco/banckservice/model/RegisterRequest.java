package com.javeriana.edu.banco.banckservice.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "Nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Apellidos son obligatorios")
    private String apellidos;

    @NotNull(message = "Fecha de nacimiento es obligatoria")
    private String fechaNacimiento;

    @NotBlank(message = "Número de teléfono es obligatorio")
    private String numeroTelefono;

    @Column(nullable = false)
    private String clave;
}

