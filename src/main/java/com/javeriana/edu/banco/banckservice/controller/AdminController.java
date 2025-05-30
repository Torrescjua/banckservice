package com.javeriana.edu.banco.banckservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javeriana.edu.banco.banckservice.entity.Compra;
import com.javeriana.edu.banco.banckservice.entity.CuentaBanco;
import com.javeriana.edu.banco.banckservice.entity.Transaccion;
import com.javeriana.edu.banco.banckservice.repository.CompraRepository;
import com.javeriana.edu.banco.banckservice.repository.CuentaBancoRepository;
import com.javeriana.edu.banco.banckservice.repository.TransaccionRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CuentaBancoRepository cuentaRepo;
    private final CompraRepository    compraRepo;
    private final TransaccionRepository transRepo;

    public AdminController(CuentaBancoRepository cuentaRepo,
                           CompraRepository compraRepo,
                           TransaccionRepository transRepo) {
        this.cuentaRepo   = cuentaRepo;
        this.compraRepo   = compraRepo;
        this.transRepo    = transRepo;
    }

    /** Listar todas las cuentas bancarias */
    @GetMapping("/cuentas")
    public List<CuentaBanco> getAllCuentas() {
        return cuentaRepo.findAll();
    }

    /** Listar todas las compras */
    @GetMapping("/compras")
    public List<Compra> getAllCompras() {
        return compraRepo.findAll();
    }

    /** Listar todas las transacciones */
    @GetMapping("/transacciones")
    public List<Transaccion> getAllTransacciones() {
        return transRepo.findAll();
    }
}
