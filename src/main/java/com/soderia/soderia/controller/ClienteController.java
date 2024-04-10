package com.soderia.soderia.controller;

import com.soderia.soderia.dto.ClienteDTO;
import com.soderia.soderia.entities.Cliente;
import com.soderia.soderia.service.impl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<ClienteDTO> clienteDTOS = clienteService.listarTodos()
                .stream()
                .map(cliente -> ClienteDTO.builder()
                        .id(cliente.getId())
                        .nombre(cliente.getNombre())
                        .apellido(cliente.getApellido())
                        .direccion(cliente.getDireccion())
                        .build())
                .toList();

        return new ResponseEntity<>(clienteDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {

        // Buscamos el cliente en la BD por ID
        Optional<Cliente> clienteOptional = clienteService.buscarPorId(id);

        // Preguntamos si existe
        if (clienteOptional.isPresent()){
            // Una vez encontrado lo obtenemos y lo guardamos como cliente
            Cliente cliente = clienteOptional.get();

            // Transformamos un cliente en un clienteDTO
            ClienteDTO clienteDTO = ClienteDTO.builder()
                    .id(cliente.getId())
                    .nombre(cliente.getNombre())
                    .apellido(cliente.getApellido())
                    .direccion(cliente.getDireccion())
                    .cuenta(cliente.getCuenta())
                    .build();

            // Devolvemos una respuesta "OK"
            return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
        }

        // En caso de no encontrarlo devolvemos una respuesta "NOT_FOUND"
        return new ResponseEntity<String>("Cliente no encontrado", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> guardarCliente(@RequestBody ClienteDTO clienteDTO) {

        // Preguntamos si los campos nombre o apellido están vacios
        if (clienteDTO.getNombre().isBlank() || clienteDTO.getApellido().isBlank()) {

            // Si están vacios devolvemos un "BAD_REQUEST"
            return new ResponseEntity<>("Por favor, complete los datos", HttpStatus.BAD_REQUEST);
        }

        // Convertimos el ClienteDTO a una entity
        Cliente cliente = Cliente.builder()
                .nombre(clienteDTO.getNombre())
                .apellido(clienteDTO.getApellido())
                .direccion(clienteDTO.getDireccion())
                .build();

        // Guardamos el cliente en la BD.

        return new ResponseEntity<>(clienteService.guardar(cliente), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable("id") Long id,@RequestBody ClienteDTO clienteDTO) {

        // Buscamos si el cliente existe en la BD
        Optional<Cliente> clienteOptional = clienteService.buscarPorId(id);

        // Preguntamos si existe
        if (clienteOptional.isPresent()) {

            // Obtenemos el cliente encontrado
            Cliente cliente = clienteOptional.get();

            // Seteamos los campos modificados
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setApellido(clienteDTO.getApellido());
            cliente.setDireccion(clienteDTO.getDireccion());

            // Actualizamos el cliente
            clienteService.actualizar(cliente);

            // Devolvemos una respuesta "OK"
            return new ResponseEntity<>("Cliente Actualizado", HttpStatus.OK);
        }

        // En caso de no encontrarlo devolvemos un "NOT_FOUND"
        return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable("id") Long id){

        if (id != null){
            clienteService.eliminar(id);
            return new ResponseEntity<>("Cliente eliminado", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("Cliente no existe", HttpStatus.NOT_FOUND);

    }
}
