package com.javeriana.edu.banco.banckservice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javeriana.edu.banco.banckservice.model.AuthResponse;
import com.javeriana.edu.banco.banckservice.model.Cliente;
import com.javeriana.edu.banco.banckservice.model.LoginRequest;
import com.javeriana.edu.banco.banckservice.model.RegisterRequest;
import com.javeriana.edu.banco.banckservice.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails cliente = clienteRepository.findByNumeroDocumento(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(cliente);
        return AuthResponse.builder()
            .token(token)
            .build();

    }

    public AuthResponse register(RegisterRequest request) {
        // Construir el objeto Cliente usando el builder
        Cliente cliente = Cliente.builder()
            .numeroDocumento(request.getNumeroDocumento())
            .nombres(request.getNombres())
            .apellidos(request.getApellidos())
            .fechaNacimiento(request.getFechaNacimiento())
            .numeroTelefono(request.getNumeroTelefono())
            .clave(passwordEncoder.encode(request.getClave()))
            .build();

        // Guardar el cliente en la base de datos
        clienteRepository.save(cliente);

        // Generar el token JWT y construir la respuesta
        return AuthResponse.builder()
            .token(jwtService.getToken(cliente))
            .build();
    }
}
