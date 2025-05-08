package cl.douc.biblioteca.serviciolibros.repository;

import cl.douc.biblioteca.serviciolibros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByAutor(String autor);
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByGenero(String genero);
}
