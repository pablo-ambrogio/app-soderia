package com.soderia.soderia.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ICuentaService<T> {

    T guardar(T t);

    Optional<T> buscarPorId(Long id);

    List<T> listarTodos();

    void actualizar(T t);
    void eliminar(Long id);

}
