package com.javeriana.edu.banco.banckservice.controller;

import com.javeriana.edu.banco.banckservice.entity.CuentaBanco;
import com.javeriana.edu.banco.banckservice.entity.Transaccion;
import com.javeriana.edu.banco.banckservice.repository.CuentaBancoRepository;
import com.javeriana.edu.banco.banckservice.repository.TransaccionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaBancoController {

    private final CuentaBancoRepository cuentaRepo;
    private final TransaccionRepository transaccionRepo;

    public CuentaBancoController(CuentaBancoRepository cuentaRepo, TransaccionRepository transaccionRepo) {
        this.cuentaRepo = cuentaRepo;
        this.transaccionRepo = transaccionRepo;
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

    // Obtener transacciones de una cuenta por ID de cuenta
    @GetMapping("/{id}/transacciones")
    public ResponseEntity<List<Transaccion>> obtenerTransaccionesPorCuenta(@PathVariable UUID id) {
        if (!cuentaRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        List<Transaccion> transacciones = transaccionRepo.findByCuentaId(id);
        return ResponseEntity.ok(transacciones);
    }
}