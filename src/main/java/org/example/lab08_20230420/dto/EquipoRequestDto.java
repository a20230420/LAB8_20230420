package org.example.lab08_20230420.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipoRequestDto {
    private String  nombre;
    private String  tag;
    private String  nombreCapitan;
    private Integer cantidadJugadores;
    private String  juego;
    private String  pais;
    private String  correoContacto;
    private String  telefonoContacto;
}