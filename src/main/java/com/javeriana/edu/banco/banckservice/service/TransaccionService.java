package com.javeriana.edu.banco.banckservice.service;

import com.javeriana.edu.banco.banckservice.entity.Compra;
import com.javeriana.edu.banco.banckservice.entity.CuentaBanco;
import com.javeriana.edu.banco.banckservice.entity.Transaccion;
import com.javeriana.edu.banco.banckservice.model.PurchaseRequest;
import com.javeriana.edu.banco.banckservice.model.PurchaseResponse;
import com.javeriana.edu.banco.banckservice.repository.CompraRepository;
import com.javeriana.edu.banco.banckservice.repository.CuentaBancoRepository;
import com.javeriana.edu.banco.banckservice.repository.TransaccionRepository;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TransaccionService {

    private final CuentaBancoRepository cuentaRepo;
    private final TransaccionRepository transRepo;
    private final CompraRepository     compraRepo;
    private final SimpMessagingTemplate broker;               

    public TransaccionService(CuentaBancoRepository cuentaRepo,
                              TransaccionRepository transRepo,
                              CompraRepository     compraRepo,
                              SimpMessagingTemplate broker) {  
        this.cuentaRepo = cuentaRepo;
        this.transRepo  = transRepo;
        this.compraRepo = compraRepo;
        this.broker     = broker;                             
    }

    @Transactional
    public PurchaseResponse procesarCompra(PurchaseRequest req) {

        // ---------- 1. Verificar cuenta ----------
        var optCuenta = cuentaRepo.findByCedula(req.getClienteCedula());
        if (optCuenta.isEmpty()) {
            Transaccion intento = new Transaccion();
            intento.setClienteCedula(req.getClienteCedula());
            intento.setMonto(req.getMonto());
            intento.setFecha(Instant.now());
            intento.setAprobada(false);
            transRepo.save(intento);
            broker.convertAndSend("/topic/transacciones", intento); 

            return new PurchaseResponse(false, "Cuenta no encontrada", 0.0);
        }

        CuentaBanco cuenta = optCuenta.get();

        // ---------- 2. Registrar compra ----------
        Compra compra = new Compra();
        compra.setClienteCedula(req.getClienteCedula());
        compra.setFecha(Instant.now());
        compraRepo.save(compra);

        // ---------- 3. Preparar transacción ----------
        boolean fondosSuficientes = cuenta.getSaldo() >= req.getMonto();

        Transaccion tx = new Transaccion();
        tx.setCompra(compra);
        tx.setClienteCedula(req.getClienteCedula());
        tx.setCuenta(cuenta);
        tx.setMonto(req.getMonto());
        tx.setFecha(Instant.now());
        tx.setAprobada(fondosSuficientes);
        transRepo.save(tx);
        broker.convertAndSend("/topic/transacciones", tx);          

        // ---------- 4. Ajustar saldo (si aplica) ----------
        if (fondosSuficientes) {
            cuenta.setSaldo(cuenta.getSaldo() - req.getMonto());
            cuentaRepo.save(cuenta);
            return new PurchaseResponse(true, "Compra aceptada", cuenta.getSaldo());
        } else {
            return new PurchaseResponse(false, "Saldo insuficiente", cuenta.getSaldo());
        }
    }
}
