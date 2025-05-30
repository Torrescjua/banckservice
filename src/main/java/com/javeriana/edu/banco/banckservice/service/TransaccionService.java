package com.javeriana.edu.banco.banckservice.service;

import com.javeriana.edu.banco.banckservice.entity.CuentaBanco;
import com.javeriana.edu.banco.banckservice.entity.Transaccion;
import com.javeriana.edu.banco.banckservice.repository.CuentaBancoRepository;
import com.javeriana.edu.banco.banckservice.repository.TransaccionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransaccionService {

    private final TransaccionRepository transRepo;
    private final CuentaBancoRepository cuentaRepo;

    public TransaccionService(TransaccionRepository transRepo,
                              CuentaBancoRepository cuentaRepo) {
        this.transRepo = transRepo;
        this.cuentaRepo = cuentaRepo;
    }

    public List<Transaccion> listarTodas() {
        return transRepo.findAll();
    }

    public List<Transaccion> porCliente(Integer cedula) {
        return transRepo.findByClienteCedula(cedula);
    }

    @Transactional
    public Transaccion crear(Transaccion t) {
        // 1) Buscar la cuenta en BD
        UUID cuentaId = t.getCuenta().getId();
        CuentaBanco cuenta = cuentaRepo.findById(cuentaId)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cuenta no encontrada: " + cuentaId));

        // 2) Validar saldo
        if (cuenta.getSaldo() < t.getMonto()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Saldo insuficiente en la cuenta " + cuentaId);
        }

        // 3) Descontar el monto y guardar la cuenta
        cuenta.setSaldo(cuenta.getSaldo() - t.getMonto());
        cuentaRepo.save(cuenta);

        // 4) Fijar fecha y guardar la transacción
        t.setFecha(Instant.now());
        return transRepo.save(t);
    }

    @Transactional
    public void eliminar(UUID id) {
        transRepo.deleteById(id);
    }
}
