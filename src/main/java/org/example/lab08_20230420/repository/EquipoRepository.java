package org.example.lab08_20230420.repository;

import org.example.lab08_20230420.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    boolean existsByNombre(String nombre);
    boolean existsByTag(String tag);
    boolean existsByNombreAndIdEquipoNot(String nombre, Integer idEquipo);
    boolean existsByTagAndIdEquipoNot(String tag, Integer idEquipo);
    Optional<org.example.lab08_20230420.entity.Equipo> findByTag(String tag);
}
