package com.javeriana.edu.banco.banckservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseResponse {
    private boolean accepted;
    private String message;
    private double nuevoSaldo;
}
