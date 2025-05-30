package com.javeriana.edu.banco.banckservice.repository;

import com.javeriana.edu.banco.banckservice.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpleadoRepository extends JpaRepository<Empleado, UUID> {}