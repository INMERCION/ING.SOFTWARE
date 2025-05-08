const API_PRESTAMOS = 'http://localhost:8083/api/v1/prestamos'; // Ajusta el puerto según tu backend

document.addEventListener('DOMContentLoaded', () => {
  cargarPrestamos();

  document.getElementById('formPrestamo').addEventListener('submit', async (e) => {
    e.preventDefault();

    const prestamo = {
      usuarioId: document.getElementById('usuarioId').value,
      libroId: document.getElementById('libroId').value,
      fechaInicio: document.getElementById('fechaPrestamo').value,
      fechaDevolucion: document.getElementById('fechaDevolucion').value
    };

    try {
      const res = await fetch(API_PRESTAMOS, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(prestamo)
      });

      const data = await res.json();

      if (res.status === 201) {
        alert('Préstamo registrado correctamente.');
        e.target.reset();
        cargarPrestamos();
      } else {
        alert(data.error || 'Error al registrar préstamo');
      }
    } catch (error) {
      console.error('Error al registrar préstamo:', error);
    }
  });
});

async function cargarPrestamos() {
  const tabla = document.getElementById('tablaPrestamos');
  tabla.innerHTML = '';

  try {
    const res = await fetch(API_PRESTAMOS);
    const prestamos = await res.json();

    prestamos.forEach(p => {
      const fila = document.createElement('tr');
      fila.innerHTML = `
        <td>${p.id}</td>
        <td>${p.usuarioId}</td>
        <td>${p.libroId}</td>
        <td>${p.fechaInicio}</td>
        <td>${p.fechaDevolucion}</td>
        <td>${p.devuelto ? 'Sí' : 'No'}</td>
        <td>
          <button onclick="eliminarPrestamo(${p.id})">Eliminar</button>
        </td>
      `;
      tabla.appendChild(fila);
    });
  } catch (error) {
    console.error('Error al cargar préstamos:', error);
  }
}

async function eliminarPrestamo(id) {
  if (!confirm('¿Deseas eliminar este préstamo?')) return;

  try {
    const res = await fetch(`${API_PRESTAMOS}/${id}`, {
      method: 'DELETE'
    });

    if (res.ok) {
      alert('Préstamo eliminado correctamente');
      cargarPrestamos();
    } else {
      alert('No se pudo eliminar el préstamo');
    }
  } catch (error) {
    console.error('Error al eliminar préstamo:', error);
  }
}
