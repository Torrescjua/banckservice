package com.javeriana.edu.banco.banckservice.model;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente implements UserDetails {

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

    // Retorna la clave (password) del usuario.
    @Override
    public String getPassword() {
        return this.clave;
    }

    // Retorna el identificador del usuario. Aquí se usa el número de documento.
    @Override
    public String getUsername() {
        return this.numeroDocumento;
    }

    // Estos métodos pueden configurarse según las necesidades de tu aplicación.
    // Si no manejas bloqueo, expiración o desactivación de cuentas, puedes retornar true.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
