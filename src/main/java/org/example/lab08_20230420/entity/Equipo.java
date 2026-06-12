package org.example.lab08_20230420.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "equipo")
@Getter
@Setter
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Integer idEquipo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tag")
    private String tag;

    @Column(name = "nombre_capitan")
    private String nombreCapitan;

    @Column(name = "cantidad_jugadores")
    private Integer cantidadJugadores;

    @Column(name = "juego")
    private String juego;

    @Column(name = "pais")
    private String pais;

    @Column(name = "correo_contacto")
    private String correoContacto;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    // 1 = Activo, 0 = Inactivo
    @Column(name = "estado")
    private Integer estado;
}
