package cl.douc.biblioteca.serviciousuarios.service;

import cl.douc.biblioteca.serviciousuarios.model.Usuario;
import cl.douc.biblioteca.serviciousuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new RuntimeException("No hay usuarios registrados.");
        }
        return usuarios;
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
    }

    public Usuario save(Usuario usuario) {
        // Verifica si el usuario ya existe, si es necesario, podrías verificar por un RUT o email
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Usuario con ID " + id + " no encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}

