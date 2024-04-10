package com.soderia.soderia.service.impl;

import com.soderia.soderia.entities.Producto;
import com.soderia.soderia.repository.IProductoRepository;
import com.soderia.soderia.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService<Producto> {

    @Autowired
    private IProductoRepository productoRepository;
    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public void actualizar(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}
