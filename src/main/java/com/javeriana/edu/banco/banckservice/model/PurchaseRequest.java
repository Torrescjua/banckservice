package com.javeriana.edu.banco.banckservice.model;

import lombok.Data;

@Data
public class PurchaseRequest {
    private Integer clienteCedula;
    private double monto;
}
