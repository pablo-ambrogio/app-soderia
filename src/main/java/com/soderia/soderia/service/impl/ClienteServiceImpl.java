package com.soderia.soderia.service.impl;

import com.soderia.soderia.dto.ClienteDTO;
import com.soderia.soderia.entities.Cliente;
import com.soderia.soderia.repository.IClienteRepository;
import com.soderia.soderia.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService<Cliente> {

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public void actualizar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
