package cl.douc.biblioteca.servicioprestamo.config;

import cl.douc.biblioteca.servicioprestamo.model.Prestamo;
import cl.douc.biblioteca.servicioprestamo.repository.PrestamoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

import java.util.*;

@Component
public class DataFakerLoader {

    @Autowired
    private PrestamoRepository prestamoRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Faker faker = new Faker(new Locale("es"));

    private static final String LIBROS_URL = "http://localhost:8081/api/v1/libros";
    private static final String USUARIOS_URL = "http://localhost:8082/api/v1/usuarios";

    @PostConstruct
    public void init() {
        if (prestamoRepository.count() == 0) {
            cargarPrestamosFalsos();
        }
    }

    private void cargarPrestamosFalsos() {
        List<Long> libroIds = obtenerIdsDesdeServicio(LIBROS_URL);
        List<Long> usuarioIds = obtenerIdsDesdeServicio(USUARIOS_URL);

        if (libroIds.isEmpty() || usuarioIds.isEmpty()) {
            System.out.println("⚠ No hay libros o usuarios disponibles para generar préstamos.");
            return;
        }

        for (int i = 0; i < 10; i++) {
            Long libroId = faker.options().nextElement(libroIds);
            Long usuarioId = faker.options().nextElement(usuarioIds);

            // Evitar duplicación de préstamo activo
            if (prestamoRepository.existsByLibroIdAndDevueltoFalse(libroId)) {
                continue;
            }

            Date fechaInicio = faker.date().past(20, java.util.concurrent.TimeUnit.DAYS);
            boolean devuelto = faker.bool().bool();
            Date fechaDevolucion = devuelto ? faker.date().future(15, java.util.concurrent.TimeUnit.DAYS, fechaInicio) : null;

            Prestamo prestamo = new Prestamo();
            prestamo.setLibroId(libroId);
            prestamo.setUsuarioId(usuarioId);
            prestamo.setFechaInicio(fechaInicio);
            prestamo.setFechaDevolucion(fechaDevolucion);
            prestamo.setDevuelto(devuelto);

            prestamoRepository.save(prestamo);
        }

        System.out.println("✅ Datos falsos de préstamos generados correctamente.");
    }

    private List<Long> obtenerIdsDesdeServicio(String url) {
        try {
            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
            if (response == null) return Collections.emptyList();

            List<Long> ids = new ArrayList<>();
            for (Map<String, Object> item : response) {
                Object id = item.get("id");
                if (id instanceof Integer) {
                    ids.add(((Integer) id).longValue());
                } else if (id instanceof Long) {
                    ids.add((Long) id);
                }
            }
            return ids;
        } catch (Exception e) {
            System.out.println("❌ Error al obtener IDs desde " + url + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

