package com.javeriana.edu.banco.banckservice.controller;

import com.javeriana.edu.banco.banckservice.repository.TransaccionRepository;
import com.javeriana.edu.banco.banckservice.entity.Transaccion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/conciliacion")
@RequiredArgsConstructor
public class ConciliacionController {

    private final TransaccionRepository transaccionRepo;

    @PostMapping(
      path = "/procesar",
      consumes = MediaType.TEXT_PLAIN_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Map<String, String>>> procesarConciliacion(
            @RequestBody String contenidoTxt
    ) {
        List<Map<String, String>> resultado = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new StringReader(contenidoTxt))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 3) {
                    // Saltamos líneas mal formadas
                    continue;
                }

                Integer cedula = Integer.valueOf(partes[0].trim());
                Instant fecha = Instant.parse(partes[1].trim());
                double monto = Double.parseDouble(partes[2].trim());

                // Filtramos solo las transacciones APROBADAS (aprobada == 1)
                Optional<Transaccion> txAprobada = transaccionRepo.findAll().stream()
                    .filter(t -> t.isAprobada())
                    .filter(t -> Objects.equals(t.getClienteCedula(), cedula))
                    .filter(t -> t.getFecha().equals(fecha))
                    .filter(t -> t.getMonto() == monto)
                    .findFirst();

                Map<String, String> res = new HashMap<>();
                res.put("cliente_cedula", partes[0].trim());
                res.put("fecha",          partes[1].trim());
                res.put("monto",          partes[2].trim());
                res.put("estado",         txAprobada.isPresent() ? "aprobado" : "no aprobado");
                resultado.add(res);
            }
        } catch (IOException e) {
            return ResponseEntity
                    .status(500)
                    .body(Collections.singletonList(
                        Map.of("error", "No se pudo procesar el texto: " + e.getMessage())
                    ));
        }

        return ResponseEntity.ok(resultado);
    }
}
