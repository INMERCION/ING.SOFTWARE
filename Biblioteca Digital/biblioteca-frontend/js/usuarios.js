const API_URL = 'http://localhost:8082/api/v1/usuarios'; // Ajusta el puerto si es necesario

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formUsuario");
    const tabla = document.getElementById("tablaUsuarios");

    const API_URL = "http://localhost:8082/api/v1/usuarios";

    // Obtener y mostrar usuarios
    const cargarUsuarios = () => {
        fetch(API_URL)
            .then(res => {
                if (!res.ok) throw new Error("No se pudieron obtener los usuarios.");
                return res.json();
            })
            .then(usuarios => {
                tabla.innerHTML = "";
                usuarios.forEach(usuario => {
                    const row = `
                        <tr>
                            <td>${usuario.id}</td>
                            <td>${usuario.nombre}</td>
                            <td>${usuario.correo}</td>
                            <td>${usuario.rol || 'N/A'}</td>
                            <td>
                                <!-- Botones editar y eliminar -->
                                <button onclick="eliminarUsuario(${usuario.id})">Eliminar</button>
                            </td>
                        </tr>`;
                    tabla.innerHTML += row;
                });
            })
            .catch(err => console.error("Error cargando usuarios:", err));
    };

    // Registrar nuevo usuario
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const nuevoUsuario = {
            nombre: document.getElementById("nombre").value,
            correo: document.getElementById("email").value,
            rol: document.getElementById("rol").value
        };

        fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevoUsuario)
        })
        .then(res => {
            if (!res.ok) throw new Error("Error al guardar usuario.");
            return res.text();
        })
        .then(msg => {
            alert(msg);
            form.reset();
            cargarUsuarios();
        })
        .catch(err => console.error("Error al guardar usuario:", err));
    });

    // Eliminar usuario
    window.eliminarUsuario = (id) => {
        if (confirm("¿Estás seguro de eliminar este usuario?")) {
            fetch(`${API_URL}/${id}`, { method: "DELETE" })
                .then(res => {
                    if (!res.ok) throw new Error("Error al eliminar usuario.");
                    return res.text();
                })
                .then(msg => {
                    alert(msg);
                    cargarUsuarios();
                })
                .catch(err => console.error("Error al eliminar:", err));
        }
    };

    cargarUsuarios(); // Inicial
});
