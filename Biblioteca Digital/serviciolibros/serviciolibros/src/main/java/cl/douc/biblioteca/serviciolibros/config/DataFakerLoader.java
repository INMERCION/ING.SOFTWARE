package cl.douc.biblioteca.serviciolibros.config;

import cl.douc.biblioteca.serviciolibros.model.Libro;
import cl.douc.biblioteca.serviciolibros.repository.LibroRepository;
import net.datafaker.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Profile("dev")
@Component
public class DataFakerLoader implements CommandLineRunner {

    private final LibroRepository libroRepository;
    private final Faker faker = new Faker();

    public DataFakerLoader(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public void run(String... args) {
        if (libroRepository.count() == 0) {
            IntStream.range(0, 20).forEach(i -> {
                Libro libro = new Libro();
                libro.setIsbn(faker.code().isbn13(true));
                libro.setTitulo(faker.book().title());
                libro.setAutor(faker.book().author());
                libro.setGenero(faker.book().genre());
                libro.setEditorial(faker.book().publisher());
                libro.setFechaPublicacion(faker.date().birthday(5, 30));

                libroRepository.save(libro);
            });
            System.out.println("Libros ficticios cargados en modo DEV.");
        }
    }
}

