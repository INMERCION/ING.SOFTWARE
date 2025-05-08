package cl.douc.biblioteca.servicioprestamo.controller;

import cl.douc.biblioteca.servicioprestamo.model.Prestamo;
import cl.douc.biblioteca.servicioprestamo.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*") // O especifica "http://localhost:5500" si usas Live Server
@RestController
@RequestMapping("/api/v1/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Prestamo> prestamos = prestamoService.findAll();
        if (prestamos.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensaje", "No hay préstamos registrados."));
        }
        return ResponseEntity.ok(prestamos);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Prestamo prestamo) {
        try {
            Prestamo nuevo = prestamoService.crearPrestamo(prestamo);
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Préstamo creado exitosamente.");
            respuesta.put("prestamo", nuevo);
            return ResponseEntity.status(201).body(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            Prestamo prestamo = prestamoService.findById(id);
            return ResponseEntity.ok(prestamo);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Préstamo no encontrado.", "detalle", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        try {
            prestamo.setId(id);
            Prestamo actualizado = prestamoService.save(prestamo);
            return ResponseEntity.ok(Map.of("mensaje", "Préstamo actualizado correctamente.", "prestamo", actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("mensaje", "No se pudo actualizar el préstamo.", "detalle", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            prestamoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Préstamo eliminado correctamente."));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("mensaje", "No se encontró el préstamo para eliminar.", "detalle", e.getMessage()));
        }
    }
}


