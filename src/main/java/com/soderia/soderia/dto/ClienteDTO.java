package com.soderia.soderia.dto;

import com.soderia.soderia.entities.Cuenta;
import com.soderia.soderia.entities.Direccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private Direccion direccion;
    private Cuenta cuenta;

}
