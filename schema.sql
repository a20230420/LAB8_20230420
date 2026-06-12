DROP DATABASE IF EXISTS lab8_esports;
CREATE DATABASE lab8_esports CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab8_esports;

CREATE TABLE equipo (
    id_equipo        INT          NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(100) NOT NULL UNIQUE,
    tag              VARCHAR(5)   NOT NULL UNIQUE,
    nombre_capitan   VARCHAR(100) NOT NULL,
    cantidad_jugadores INT        NOT NULL,
    juego            VARCHAR(100) NOT NULL,
    pais             VARCHAR(80)  NOT NULL,
    correo_contacto  VARCHAR(150) NOT NULL,
    telefono_contacto VARCHAR(9)  NOT NULL,
    estado           TINYINT(1)  NOT NULL DEFAULT 1,
    PRIMARY KEY (id_equipo)
);