package cl.douc.biblioteca.serviciolibros.controller;

import cl.douc.biblioteca.serviciolibros.model.Libro;
import cl.douc.biblioteca.serviciolibros.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // O especifica "http://localhost:5500" si usas Live Server
@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Libro> libros = libroService.findAll();
        if (libros.isEmpty()) {
            return ResponseEntity.status(204).body("No hay libros registrados.");
        }
        return ResponseEntity.ok(libros);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Libro libro) {
        Libro nuevoLibro = libroService.save(libro);
        return ResponseEntity.status(201).body("Libro creado exitosamente con ID: " + nuevoLibro.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            Libro libro = libroService.findById(id);
            return ResponseEntity.ok(libro);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Libro con ID " + id + " no encontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            libro.setId(id);
            libroService.save(libro);
            return ResponseEntity.ok("Libro con ID " + id + " actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se pudo actualizar. Libro con ID " + id + " no encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            libroService.delete(id);
            return ResponseEntity.ok("Libro con ID " + id + " eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se pudo eliminar. Libro con ID " + id + " no encontrado.");
        }
    }
}
