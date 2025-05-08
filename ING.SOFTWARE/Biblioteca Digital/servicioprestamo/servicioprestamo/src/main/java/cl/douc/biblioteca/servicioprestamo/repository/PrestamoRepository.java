package cl.douc.biblioteca.servicioprestamo.repository;

import cl.douc.biblioteca.servicioprestamo.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    boolean existsByLibroIdAndDevueltoFalse(Long libroId);
}

