package cl.douc.biblioteca.serviciousuarios.controller;

import cl.douc.biblioteca.serviciousuarios.model.Usuario;
import cl.douc.biblioteca.serviciousuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*") // O especifica "http://localhost:5500" si usas Live Server
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).body("No hay usuarios registrados.");
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(201).body("Usuario creado exitosamente con ID: " + nuevoUsuario.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Usuario con ID " + id + " no encontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            usuarioService.save(usuario);
            return ResponseEntity.ok("Usuario con ID " + id + " actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se pudo actualizar. Usuario con ID " + id + " no encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se pudo eliminar. Usuario con ID " + id + " no encontrado.");
        }
    }
}

