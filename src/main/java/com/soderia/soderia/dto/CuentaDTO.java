package com.soderia.soderia.dto;

import com.soderia.soderia.entities.Cliente;
import com.soderia.soderia.entities.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaDTO {

    private Long id;
    private Cliente cliente;
    private Set<Producto> productos;
}
