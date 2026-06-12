package org.example.lab08_20230420.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipoListadoDto {
    private String nombre;
    private String tag;
    private String capitan;
    private String juego;
    private String pais;
    private String estado;  // Acá puede ser ACTIVO o INACTIVO
}