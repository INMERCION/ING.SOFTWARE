const API_BASE = "http://localhost:8080/api/v1"; // Cambia el puerto según el microservicio

// Función genérica para manejar respuestas JSON
async function fetchJSON(url, options = {}) {
  try {
    const token = localStorage.getItem("token");

    // Inicializar headers si no existen
    if (!options.headers) {
      options.headers = {
        'Content-Type': 'application/json'
      };
    }

    // Agregar token si está disponible
    if (token) {
      options.headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await fetch(url, options);
    const contentType = response.headers.get("Content-Type");

    if (!response.ok) {
      const error = contentType && contentType.includes("application/json")
        ? await response.json()
        : await response.text();
      throw new Error(error?.mensaje || error || "Error en la petición");
    }

    return contentType && contentType.includes("application/json")
      ? await response.json()
      : await response.text();

  } catch (error) {
    console.error("Error al realizar fetch:", error.message);
    alert(`Error: ${error.message}`);
    throw error;
  }
}

// Función para limpiar formularios
function limpiarFormulario(form) {
  form.reset();
}

// Función para crear filas de tabla con datos y botones
function crearFilaTabla(objeto, campos, onEditar, onEliminar) {
  const tr = document.createElement("tr");

  campos.forEach(campo => {
    const td = document.createElement("td");
    td.textContent = objeto[campo];
    tr.appendChild(td);
  });

  const tdAcciones = document.createElement("td");

  const btnEditar = document.createElement("button");
  btnEditar.textContent = "Editar";
  btnEditar.onclick = () => onEditar(objeto);
  tdAcciones.appendChild(btnEditar);

  const btnEliminar = document.createElement("button");
  btnEliminar.textContent = "Eliminar";
  btnEliminar.style.marginLeft = "8px";
  btnEliminar.onclick = () => onEliminar(objeto.id);
  tdAcciones.appendChild(btnEliminar);

  tr.appendChild(tdAcciones);

  return tr;
}
