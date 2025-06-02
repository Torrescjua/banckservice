package com.javeriana.edu.banco.banckservice.repository;

import com.javeriana.edu.banco.banckservice.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {
    List<Transaccion> findByClienteCedula(Integer cedula);
    List<Transaccion> findByCuentaId(UUID cuentaId);
}
