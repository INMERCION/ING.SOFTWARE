package cl.douc.biblioteca.serviciousuarios.repository;

import cl.douc.biblioteca.serviciousuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

