package com.javeriana.edu.banco.banckservice.repository;

import com.javeriana.edu.banco.banckservice.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CompraRepository extends JpaRepository<Compra, UUID> {}
