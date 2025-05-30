package com.javeriana.edu.banco.banckservice.service;

import com.javeriana.edu.banco.banckservice.entity.Empleado;
import com.javeriana.edu.banco.banckservice.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public List<Empleado> listarEmpleados() {
        return repository.findAll();
    }

    public Optional<Empleado> buscarEmpleadoPorId(Long id) {
        return repository.findById(id);
    }

    public Empleado actualizarEmpleado(Long id, Empleado empleadoActualizado) {
        return repository.findById(id)
          .map(emp -> {
              emp.setNombres(empleadoActualizado.getNombres());
              emp.setApellidos(empleadoActualizado.getApellidos());
              emp.setNumeroTelefono(empleadoActualizado.getNumeroTelefono());
              // si tienes más campos, aquí los actualizas
              return repository.save(emp);
          })
          .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    public void eliminarEmpleado(Long id) {
        repository.deleteById(id);
    }
}
