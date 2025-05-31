package com.javeriana.edu.banco.banckservice.controller;

import com.javeriana.edu.banco.banckservice.entity.CuentaBanco;
import com.javeriana.edu.banco.banckservice.repository.CuentaBancoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaBancoController {

    private final CuentaBancoRepository cuentaRepo;

    public CuentaBancoController(CuentaBancoRepository cuentaRepo) {
        this.cuentaRepo = cuentaRepo;
    }

    // Crear cuenta
    @PostMapping
    public CuentaBanco crearCuenta(@RequestBody CuentaBanco cuenta) {
        return cuentaRepo.save(cuenta);
    }

    // Listar todas las cuentas
    @GetMapping
    public List<CuentaBanco> listarCuentas() {
        return cuentaRepo.findAll();
    }

    // Obtener cuenta por ID
    @GetMapping("/{id}")
    public ResponseEntity<CuentaBanco> obtenerCuenta(@PathVariable UUID id) {
        return cuentaRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar cuenta
    @PutMapping("/{id}")
    public ResponseEntity<CuentaBanco> actualizarCuenta(@PathVariable UUID id, @RequestBody CuentaBanco cuentaActualizada) {
        return cuentaRepo.findById(id)
                .map(cuenta -> {
                    cuenta.setSaldo(cuentaActualizada.getSaldo());
                    cuenta.setCedula(cuentaActualizada.getCedula());
                    return ResponseEntity.ok(cuentaRepo.save(cuenta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable UUID id) {
        if (cuentaRepo.existsById(id)) {
            cuentaRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}