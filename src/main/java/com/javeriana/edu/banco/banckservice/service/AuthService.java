package com.javeriana.edu.banco.banckservice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javeriana.edu.banco.banckservice.entity.Empleado;
import com.javeriana.edu.banco.banckservice.model.AuthResponse;
import com.javeriana.edu.banco.banckservice.model.LoginRequest;
import com.javeriana.edu.banco.banckservice.model.RegisterRequest;
import com.javeriana.edu.banco.banckservice.repository.EmpleadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmpleadoRepository empleadoRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;  // ← inyecta el bean

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
          new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        Empleado user = empleadoRepo.findByNumeroDocumento(req.getUsername())
                          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest req) {
        Empleado emp = Empleado.builder()
            .numeroDocumento(req.getNumeroDocumento())
            .nombres(req.getNombres())
            .apellidos(req.getApellidos())
            .numeroTelefono(req.getNumeroTelefono())
            .clave(passwordEncoder.encode(req.getClave()))
            .build();
        empleadoRepo.save(emp);
        String token = jwtService.getToken(emp);
        return AuthResponse.builder().token(token).build();
    }
}
