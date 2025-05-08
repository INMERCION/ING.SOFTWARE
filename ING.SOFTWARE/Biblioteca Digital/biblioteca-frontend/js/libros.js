const API_LIBROS = 'http://localhost:8081/api/v1/libros'; // Ajusta el puerto si es necesario
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formLibro");
    const tabla = document.getElementById("tablaLibros");

    const API_URL = "http://localhost:8081/api/v1/libros";

    // Cargar libros en la tabla
    const cargarLibros = () => {
        fetch(API_URL)
            .then(res => {
                if (!res.ok) throw new Error("No se pudieron obtener los libros.");
                return res.json();
            })
            .then(libros => {
                tabla.innerHTML = "";
                libros.forEach(libro => {
                    const row = `
                        <tr>
                            <td>${libro.id}</td>
                            <td>${libro.titulo}</td>
                            <td>${libro.autor}</td>
                            <td>${libro.isbn}</td>
                            <td>${libro.editorial}</td>
                            <td>${libro.genero}</td>
                            <td>
                                <button onclick="eliminarLibro(${libro.id})">Eliminar</button>
                            </td>
                        </tr>`;
                    tabla.innerHTML += row;
                });
            })
            .catch(err => console.error("Error cargando libros:", err));
    };

    // Registrar libro
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const nuevoLibro = {
            titulo: document.getElementById("titulo").value,
            autor: document.getElementById("autor").value,
            isbn: document.getElementById("isbn").value,
            editorial: document.getElementById("editorial").value,
            genero: document.getElementById("genero").value

        };

        fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevoLibro)
        })
        .then(res => {
            if (!res.ok) throw new Error("Error al guardar libro.");
            return res.text();
        })
        .then(msg => {
            alert(msg);
            form.reset();
            cargarLibros();
        })
        .catch(err => console.error("Error al guardar libro:", err));
    });

    // Eliminar libro
    window.eliminarLibro = (id) => {
        if (confirm("¿Eliminar este libro?")) {
            fetch(`${API_URL}/${id}`, { method: "DELETE" })
                .then(res => {
                    if (!res.ok) throw new Error("Error al eliminar libro.");
                    return res.text();
                })
                .then(msg => {
                    alert(msg);
                    cargarLibros();
                })
                .catch(err => console.error("Error al eliminar libro:", err));
        }
    };

    cargarLibros(); // Inicial
});
