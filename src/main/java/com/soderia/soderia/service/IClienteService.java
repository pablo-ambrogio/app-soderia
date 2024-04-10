package com.soderia.soderia.service;

import java.util.List;
import java.util.Optional;

public interface IClienteService<T> {

    T guardar(T t);

    Optional<T> buscarPorId(Long id);

    List<T> listarTodos();

    void actualizar(T t);

    void eliminar(Long id);

}
