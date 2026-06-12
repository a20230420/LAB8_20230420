package org.example.lab08_20230420.controller;

import org.example.lab08_20230420.dto.EquipoListadoDto;
import org.example.lab08_20230420.dto.EquipoRequestDto;
import org.example.lab08_20230420.entity.Equipo;
import org.example.lab08_20230420.repository.EquipoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoRepository equipoRepository;

    public EquipoController(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    // PREGUNTA 1
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> registrar(
            @RequestBody EquipoRequestDto dto) {

        HashMap<String, Object> response = new HashMap<>();

        // Validamos que sea nombre único
        if (equipoRepository.existsByNombre(dto.getNombre())) {
            response.put("resultado", "error");
            response.put("mensaje", "Ya existe un equipo con ese nombre");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Validamos que sea tag único
        if (equipoRepository.existsByTag(dto.getTag())) {
            response.put("resultado", "error");
            response.put("mensaje", "Ya existe un equipo con ese tag");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Validamos que el tag debe tener entre 2 y5 caracteres
        if (dto.getTag() == null || dto.getTag().length() < 2 || dto.getTag().length() > 5) {
            response.put("resultado", "error");
            response.put("mensaje", "El tag debe tener entre 2 y 5 caracteres");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Validamos que la cantidad de jugadores sea mínimo 5 y máximo 10
        if (dto.getCantidadJugadores() == null ||
                dto.getCantidadJugadores() < 5 || dto.getCantidadJugadores() > 10) {
            response.put("resultado", "error");
            response.put("mensaje", "La cantidad de jugadores debe estar entre 5 y 10");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Validamos que el correo tenga formato válido
        if (dto.getCorreoContacto() == null ||
                dto.getCorreoContacto().matches("")) {
            response.put("resultado", "error");
            response.put("mensaje", "El correo no tiene un formato válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Validamos que el teléfono tenga sólo 9 dígitos
        if (dto.getTelefonoContacto() == null ||
                !dto.getTelefonoContacto().matches("^\\d{9}$")) {
            response.put("resultado", "error");
            response.put("mensaje", "El teléfono debe contener exactamente 9 dígitos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Crear entidad acá en controlador
        Equipo equipo = new Equipo();
        equipo.setNombre(dto.getNombre());
        equipo.setTag(dto.getTag());
        equipo.setNombreCapitan(dto.getNombreCapitan());
        equipo.setCantidadJugadores(dto.getCantidadJugadores());
        equipo.setJuego(dto.getJuego());
        equipo.setPais(dto.getPais());
        equipo.setCorreoContacto(dto.getCorreoContacto());
        equipo.setTelefonoContacto(dto.getTelefonoContacto());
        equipo.setEstado(1); // Acá le ponemos estado ACTIVO al registrarse automáticamente

        Equipo guardado = equipoRepository.save(equipo);

        response.put("resultado", "creado");
        response.put("mensaje", "Equipo registrado correctamente");
        response.put("id", guardado.getIdEquipo());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> listarTodos() {

        HashMap<String, Object> response = new HashMap<>();

        List<Equipo> equipos = equipoRepository.findAll();

        // Convertimos a DTO de respuesta para mostrar solo los campos pedidos
        List<EquipoListadoDto> lista = equipos.stream()
                .map(e -> {
                    EquipoListadoDto dto = new EquipoListadoDto();
                    dto.setNombre(e.getNombre());
                    dto.setTag(e.getTag());
                    dto.setCapitan(e.getNombreCapitan());
                    dto.setJuego(e.getJuego());
                    dto.setPais(e.getPais());
                    dto.setEstado(e.getEstado() == 1 ? "Activo" : "Inactivo");
                    return dto;
                })
                .toList();

        response.put("resultado", "ok");
        response.put("equipos", lista);
        return ResponseEntity.ok(response);
    }

    // PREGUNTA 2
    @GetMapping("/{tag}")
    public ResponseEntity<HashMap<String, Object>> buscarPorTag(
            @PathVariable String tag) {

        HashMap<String, Object> response = new HashMap<>();

        Optional<Equipo> opt = equipoRepository.findByTag(tag);

        if (opt.isEmpty()) {
            response.put("resultado", "error");
            response.put("mensaje", "No se encontró un equipo con el tag: " + tag);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Equipo e = opt.get();
        EquipoListadoDto dto = new EquipoListadoDto();
        dto.setNombre(e.getNombre());
        dto.setTag(e.getTag());
        dto.setCapitan(e.getNombreCapitan());
        dto.setJuego(e.getJuego());
        dto.setPais(e.getPais());
        dto.setEstado(e.getEstado() == 1 ? "Activo" : "Inactivo");

        response.put("resultado", "ok");
        response.put("equipo", dto);
        return ResponseEntity.ok(response);
    }

    // PREGUNTA 3


    // PREGUNTA 4 - (LA 3 HACIA QUE NO COMPILE ASI QUE NO LA PUSE xd :c)
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> desactivar(
            @PathVariable Integer id) {

        HashMap<String, Object> response = new HashMap<>();

        Optional<Equipo> opt = equipoRepository.findById(id);
        if (opt.isEmpty()) {
            response.put("resultado", "error");
            response.put("mensaje", "No se encontró un equipo con id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Equipo equipo = opt.get();
        equipo.setEstado(0); // ACÁ SE DESACTIVA
        equipoRepository.save(equipo);

        response.put("resultado", "ok");
        response.put("mensaje", "Equipo desactivado correctamente");
        return ResponseEntity.ok(response);
    }

}
