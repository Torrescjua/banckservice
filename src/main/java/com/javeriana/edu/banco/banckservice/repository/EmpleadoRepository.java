package com.javeriana.edu.banco.banckservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.javeriana.edu.banco.banckservice.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByNumeroDocumento(String numeroDocumento);
}
