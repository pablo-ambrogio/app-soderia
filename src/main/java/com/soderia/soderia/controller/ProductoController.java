package com.soderia.soderia.controller;

import com.soderia.soderia.dto.ProductoDTO;
import com.soderia.soderia.entities.Producto;
import com.soderia.soderia.service.impl.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoServiceImpl productoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<ProductoDTO> productoDTOS = productoService.listarTodos()
                .stream()
                .map(producto -> ProductoDTO.builder()
                        .id(producto.getId())
                        .nombre(producto.getNombre())
                        .precio(producto.getPrecio())
                        .build())
                .toList();

        return new ResponseEntity<>(productoDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {

        // Buscamos producto por ID en la BD
        Optional<Producto> productoOptional = productoService.buscarPorId(id);

        // Preguntamos si existe
        if (productoOptional.isPresent()) {

            // Obtenemos el cliente encontrado
            Producto producto = productoOptional.get();


            // Transformamos el producto a un productoDTO
            ProductoDTO productoDTO = ProductoDTO.builder()
                    .id(producto.getId())
                    .nombre(producto.getNombre())
                    .precio(producto.getPrecio())
                    .build();

            // Devolvemos una respuesta "OK"
            return new ResponseEntity<>(productoDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody ProductoDTO productoDTO) throws URISyntaxException {

        // Verificamos que el campo nombre y apellido no esten vacios
        if (productoDTO.getNombre().isBlank() || productoDTO.getPrecio() == null) {

            return new ResponseEntity<>("Verifique los campos", HttpStatus.BAD_REQUEST);
        }

        // Transformamos un productoDTO a un Producto
        Producto producto = Producto.builder()
                .nombre(productoDTO.getNombre())
                .precio(productoDTO.getPrecio())
                .build();

        productoService.guardar(producto);

        return new ResponseEntity<>(new URI("/api/v1/productos"), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable("id") Long id,@RequestBody ProductoDTO productoDTO) {
        Optional<Producto> productoOptional = productoService.buscarPorId(id);
        if (productoOptional.isPresent()) {

            Producto producto = productoOptional.get();

            producto.setNombre(productoDTO.getNombre());
            producto.setPrecio(productoDTO.getPrecio());

            productoService.actualizar(producto);
            return new ResponseEntity<>("Producto actualizado", HttpStatus.OK);
        }
        return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("id") Long id) {

        if (id != null) {
            productoService.eliminar(id);
            return new ResponseEntity<>("Producto eliminado", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
    }
}
