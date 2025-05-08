package cl.douc.biblioteca.serviciolibros.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String genero;

    @Column(nullable = false)
    private String autor;

    @Column
    private String editorial;

    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
}
