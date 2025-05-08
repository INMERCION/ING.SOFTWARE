# 📚 Biblioteca Universitaria - Microservicios con Spring Boot

Este proyecto implementa un sistema de gestión de biblioteca basado en una arquitectura de microservicios utilizando **Spring Boot**. El sistema está dividido en tres servicios principales: **Usuarios**, **Libros** y **Préstamos**, cada uno desarrollado de manera independiente para facilitar el mantenimiento, la escalabilidad y el despliegue continuo.

---

## 🔧 Tecnologías Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Maven
- RESTful APIs
- Postman (para pruebas)
- Git

---

## 📦 Estructura del Proyecto
                        ┌────────────────────┐
                        │    Cliente (Postman / Frontend)    
                        └────────▲───────────┘
                                 │
                      REST APIs (HTTP JSON)
                                 │
           ┌─────────────────────┼───────────────────────┐
    ┌───────▼──────────┐  ┌───────▼────────────┐ ┌───────▼───────────┐
    │ Servicio Libros  │  │ Servicio Usuarios  │ │ Servicio Préstamos│
    │  /api/v1/libros  │  │ /api/v1/usuarios   │ │ /api/v1/prestamos │
    └────────┬─────────┘  └─────────┬──────────┘ └───────┬───────────┘
             ▼                       ▼                   ▼
      🗃️ Base de Datos      🗃️ Base de Datos      🗃️ Base de Datos
         db_libros              db_usuarios           db_prestamos

                    
     
Cada carpeta representa un microservicio independiente, con su propio:
- `model/`: Clase entidad JPA
- `repository/`: Acceso a datos con JPA
- `service/`: Lógica de negocio
- `controller/`: Endpoints REST

---

## 🚀 Endpoints Principales

### ✅ Servicio de Libros

- `GET /api/v1/libros`: Listar libros
- `GET /api/v1/libros/{id}`: Buscar libro por ID
- `POST /api/v1/libros`: Crear nuevo libro
- `PUT /api/v1/libros/{id}`: Actualizar libro
- `DELETE /api/v1/libros/{id}`: Eliminar libro

### ✅ Servicio de Usuarios

- `GET /api/v1/usuarios`: Listar usuarios
- `GET /api/v1/usuarios/{id}`: Buscar usuario por ID
- `POST /api/v1/usuarios`: Crear nuevo usuario
- `PUT /api/v1/usuarios/{id}`: Actualizar usuario
- `DELETE /api/v1/usuarios/{id}`: Eliminar usuario

### ✅ Servicio de Préstamos

- `GET /api/v1/prestamos`: Listar préstamos
- `GET /api/v1/prestamos/{id}`: Buscar préstamo por ID
- `POST /api/v1/prestamos`: Crear préstamo
- `PUT /api/v1/prestamos/{id}`: Actualizar préstamo
- `DELETE /api/v1/prestamos/{id}`: Eliminar préstamo

---

## 🛠️ Configuración del Entorno

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tuusuario/biblioteca-microservicios.git
cd biblioteca-microservicios

📁 Estructura del Proyecto Completa
Nueva Estructura que contempla un Login y un Front basico en HTML, CSS, y JavaScript

biblioteca-universitaria/
│
├── auth-service/                      # Nuevo microservicio para login y JWT
│   ├── src/
│   │   └── main/java/com/biblioteca/auth/
│   │       ├── controller/           # AuthController.java
│   │       ├── model/                # Usuario.java
│   │       ├── repository/           # UsuarioRepository.java
│   │       ├── service/              # UserDetailsServiceImpl.java
│   │       ├── security/             # JwtUtil.java, SecurityConfig.java
│   │       └── AuthServiceApp.java   # Clase principal
│   └── resources/
│       └── application.properties
│
├── usuarios-service/
│   └── ...                           # CRUD de usuarios (como ya tienes)
│
├── libros-service/
│   └── ...                           # CRUD de libros
│
├── prestamos-service/
│   └── ...                           # CRUD de préstamos
│
├── frontend/                         # Frontend básico con HTML/JS
│   ├── login.html                    # Página de login y listado de libros
│   └── css/
│       └── estilos.css               # (opcional)
│
├── .gitignore
├── README.md
├── proyecto-kanban.png              # Captura de tablero Kanban Scrum
├── github-evidencias/               # Carpeta con capturas de GitHub
│   ├── commits.png
│   ├── pull-requests.png
│   ├── branches.png
│   └── ...
│
└── docker-compose.yml (opcional)    # Para orquestar los servicios con contenedores


🧩 Interacción entre servicios
text
Copiar
Editar
[login.html] → [auth-service] → 🔐 Genera token JWT
         ↓
[libros-service] ←🔒 Token
[usuarios-service] ←🔒 Token
[prestamos-service] ←🔒 Token


🔗 Endpoints clave
POST /api/v1/auth/login → login (retorna JWT)

GET /api/v1/libros → protegido (requiere JWT)

GET /api/v1/usuarios → protegido (requiere JWT)

GET /api/v1/prestamos → protegido (requiere JWT)
