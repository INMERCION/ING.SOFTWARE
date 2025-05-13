package cl.douc.biblioteca.servicioprestamo.service;

import cl.douc.biblioteca.servicioprestamo.model.Prestamo;
import cl.douc.biblioteca.servicioprestamo.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    public Prestamo findById(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
    }

    public Prestamo save(Prestamo prestamo) {
        // Si el préstamo está marcado como devuelto, establecemos la fecha de devolución
        if (prestamo.getDevuelto() && prestamo.getFechaDevolucion() == null) {
            prestamo.setFechaDevolucion(new Date());
        }
        return prestamoRepository.save(prestamo);
    }

    public void delete(Long id) {
        if (!prestamoRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el préstamo con ID: " + id + " para eliminar.");
        }
        prestamoRepository.deleteById(id);
    }

    public Prestamo crearPrestamo(Prestamo prestamo) {
        boolean prestado = prestamoRepository.existsByLibroIdAndDevueltoFalse(prestamo.getLibroId());
        if (prestado) {
            throw new RuntimeException("El libro con ID: " + prestamo.getLibroId() + " ya está prestado.");
        }
        prestamo.setDevuelto(false);
        return prestamoRepository.save(prestamo);
    }
}


