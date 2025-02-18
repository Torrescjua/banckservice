package com.javeriana.edu.banco.banckservice.service;

import com.javeriana.edu.banco.banckservice.model.Cliente;
import com.javeriana.edu.banco.banckservice.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return repository.findById(id);
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        return repository.findById(id).map(cliente -> {
            cliente.setNombres(clienteActualizado.getNombres());
            cliente.setApellidos(clienteActualizado.getApellidos());
            cliente.setNumeroTelefono(clienteActualizado.getNumeroTelefono());
            return repository.save(cliente);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void eliminarCliente(Long id) {
        repository.deleteById(id);
    }
}

