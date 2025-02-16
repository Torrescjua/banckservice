package com.javeriana.edu.banco.banckservice.repository;

import com.javeriana.edu.banco.banckservice.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
