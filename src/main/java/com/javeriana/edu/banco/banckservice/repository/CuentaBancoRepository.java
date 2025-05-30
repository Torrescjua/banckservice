package com.javeriana.edu.banco.banckservice.repository;

import com.javeriana.edu.banco.banckservice.entity.CuentaBanco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CuentaBancoRepository extends JpaRepository<CuentaBanco, UUID> {}
