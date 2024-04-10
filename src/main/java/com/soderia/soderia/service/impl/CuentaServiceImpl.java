package com.soderia.soderia.service.impl;

import com.soderia.soderia.entities.Cuenta;
import com.soderia.soderia.repository.ICuentaRepository;
import com.soderia.soderia.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements ICuentaService<Cuenta> {

    @Autowired ICuentaRepository cuentaRepository;

    @Override
    public Cuenta guardar(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Optional<Cuenta> buscarPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public List<Cuenta> listarTodos() {
        return cuentaRepository.findAll();
    }

    @Override
    public void actualizar(Cuenta cuenta) {
        cuentaRepository.save(cuenta);
    }

    @Override
    public void eliminar(Long id) {
    cuentaRepository.deleteById(id);
    }
}
