package com.soderia.soderia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {

    private Long id;
    private String calle;
    private Number numero;
}
