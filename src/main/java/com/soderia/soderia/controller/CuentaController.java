package com.soderia.soderia.controller;

import com.soderia.soderia.dto.ClienteDTO;
import com.soderia.soderia.dto.CuentaDTO;
import com.soderia.soderia.entities.Cliente;
import com.soderia.soderia.entities.Cuenta;
import com.soderia.soderia.entities.Producto;
import com.soderia.soderia.service.IClienteService;
import com.soderia.soderia.service.ICuentaService;
import com.soderia.soderia.service.IProductoService;
import org.hibernate.dialect.function.array.OracleArrayRemoveIndexFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/cuentas")
public class CuentaController {

    @Autowired
    private ICuentaService cuentaService;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IProductoService productoService;


//    @GetMapping
//    public ResponseEntity<?> obtenerTodos() {
//        List<CuentaDTO> cuentaDTOS = cuentaService.buscarTodos()
//                .stream()
//                .map(cuenta -> CuentaDTO.builder()
//                        .cliente(cuenta.))
//    }

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        // VER PORQUE NO ME DEJA REALIZAR EL MAPEO DE DTO

        return new ResponseEntity<>(cuentaService.listarTodos(), HttpStatus.OK);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Optional<Cuenta> optionalCuenta = cuentaService.buscarPorId(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();

            CuentaDTO cuentaDTO = CuentaDTO.builder()
                    .id(cuenta.getId())
                    .cliente(cuenta.getCliente())
                    .productos(cuenta.getProductos())
                    .build();

            return new ResponseEntity<>(cuentaDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Cuenta no encontrado", HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/add/{idCliente}")
    public ResponseEntity<?> guardaCuenta(@RequestBody CuentaDTO cuentaDTO, @PathVariable("idCliente") Long idCliente){

        // Buscamos el cliente por el ID en la BD
        Optional<Cliente> clienteOptional = clienteService.buscarPorId(idCliente);

//        Set<Producto> productos = null;
//
//        for ( Producto product: cuentaDTO.getProductos()) {
//            Optional<Producto> productoOptional = productoService.buscarPorId(product.getId());
//            productos.add(productoOptional.get());
//        }

        // Preguntamos si existe
        if (clienteOptional.isPresent()) {

            // Obtenemos el cliente
            Cliente cliente = clienteOptional.get();

            // Creamos una cuenta
            Cuenta cuenta = new Cuenta();
            // Seteamos el cliente a una cuenta
            cuenta.setCliente(cliente);
//            cuenta.setProductos(productos);

        return new ResponseEntity<>(cuentaService.guardar(cuenta), HttpStatus.CREATED);
        }

        return new ResponseEntity<>("El cliente debe estar creado", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> actualizarCuenta(@PathVariable("id") Long id, @RequestBody CuentaDTO cuentaDTO) {

        Optional<Cuenta> optionalCuenta = cuentaService.buscarPorId(id);

        if (optionalCuenta.isPresent()) {

            Cuenta cuenta = optionalCuenta.get();

            cuenta.setCliente(cuentaDTO.getCliente());
//            cuenta.setProductos(cuentaDTO.getProductos());

            cuentaService.actualizar(cuenta);

            return new ResponseEntity<>("Cuenta actualizada", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cuenta no encontrada", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable("id") Long id) {

        if (id != null) {
            cuentaService.eliminar(id);
            return new ResponseEntity<>("Cuenta eliminada", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Cuenta no encontrada", HttpStatus.NOT_FOUND);
    }
}
