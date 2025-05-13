package cl.douc.biblioteca.serviciousuarios.config;

import cl.douc.biblioteca.serviciousuarios.model.Usuario;
import cl.douc.biblioteca.serviciousuarios.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Profile("dev")
@Component
public class DataFakerLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final Faker faker = new Faker();

    public DataFakerLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            IntStream.range(0, 20).forEach(i -> {
                Usuario usuario = new Usuario();
                usuario.setNombre(faker.name().fullName());
                usuario.setRut(generarRutValidoChile(i)); // RUT ficticio
                usuario.setCorreo(faker.internet().emailAddress());
                usuario.setTelefono(faker.phoneNumber().cellPhone());
                usuario.setDireccion(faker.address().fullAddress());

                usuarioRepository.save(usuario);
            });
            System.out.println("Usuarios ficticios cargados en modo DEV.");
        }
    }

    // Simula un RUT chileno válido
    private String generarRutValidoChile(int secuencia) {
        int numero = 10000000 + secuencia;
        char digitoVerificador = calcularDV(numero);
        return numero + "-" + digitoVerificador;
    }

    private char calcularDV(int rut) {
        int suma = 0;
        int multiplicador = 2;
        while (rut > 0) {
            int digito = rut % 10;
            suma += digito * multiplicador;
            rut /= 10;
            multiplicador = multiplicador == 7 ? 2 : multiplicador + 1;
        }
        int resto = 11 - (suma % 11);
        if (resto == 11) return '0';
        if (resto == 10) return 'K';
        return (char) (resto + '0');
    }
}
