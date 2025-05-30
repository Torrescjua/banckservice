package com.javeriana.edu.banco.banckservice.controller;

import com.javeriana.edu.banco.banckservice.model.PurchaseRequest;
import com.javeriana.edu.banco.banckservice.model.PurchaseResponse;
import com.javeriana.edu.banco.banckservice.service.TransaccionService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/retail")
public class TransaccionController {

    private final TransaccionService transaccionService;
    private static final String RETAIL_APP_IP = "10.43.102.241";

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/compras")
    public ResponseEntity<PurchaseResponse> procesarCompra(
            @RequestBody PurchaseRequest req,
            HttpServletRequest httpReq
    ) {
        // Si prefieres validar IP manualmente aquí:
        String xf = httpReq.getHeader("X-Forwarded-For");
        String remote = (xf != null ? xf.split(",")[0].trim() : httpReq.getRemoteAddr());
        if (!RETAIL_APP_IP.equals(remote)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Llama al service que hace TODO: ver cuenta, restar saldo, guardar Transaccion
        PurchaseResponse resp = transaccionService.procesarCompra(req);

        HttpStatus status = resp.isAccepted()
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(resp, status);
    }
}
