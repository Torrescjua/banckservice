// src/main/java/com/javeriana/edu/banco/banckservice/entity/Empleado.java
package com.javeriana.edu.banco.banckservice.entity;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "Empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override public String getPassword() { return clave; }
    @Override public String getUsername() { return numeroDocumento; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked()  { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
