#  La Maison — Sistema Reactivo de Restaurante

Proyecto desarrollado para la asignatura de **Programación Reactiva** — Fundación Universitaria Católica Lumen Gentium (Unicatólica).

---

##  Descripción del Proyecto

Sistema fullstack reactivo para la gestión del restaurante **La Maison**. Permite el acceso seguro al sistema mediante registro, inicio de sesión y control de roles, además de gestión de productos del menú, registro de pedidos y administración de usuarios.

El sistema distingue dos tipos de usuarios:
- **Cliente** — puede registrarse, iniciar sesión, ver el menú, agregar productos al carrito y realizar pedidos por WhatsApp.
- **Administrador** — gestiona productos, visualiza pedidos, administra usuarios y modifica roles.

---

##  Stack Tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal del backend |
| Spring Boot | 3.5.13 | Framework base |
| Spring WebFlux | Incluido | Programación reactiva (Mono / Flux) |
| Spring Data MongoDB Reactive | Incluido | Repositorios reactivos |
| Spring Security (WebFlux) | Incluido | Seguridad y control de acceso |
| JWT (jjwt) | 0.11.5 | Autenticación stateless |
| MongoDB Atlas | Cloud | Base de datos NoSQL en la nube |
| React | 19.2.0 | Frontend SPA |
| Vite | 7.x | Bundler del frontend |
| Axios | 1.x | Cliente HTTP en el frontend |
| React Router DOM | 7.x | Enrutamiento del frontend |
| Maven | 3.x | Gestión de dependencias Java |

---

##  Estructura del Proyecto

```
lamaison-auth/
├── pom.xml
└── src/main/java/com/lamaison/auth/
    ├── LamaisonAuthApplication.java
    ├── model/
    │   ├── User.java
    │   ├── Role.java (enum: CLIENTE, ADMINISTRADOR)
    │   ├── Producto.java
    │   ├── Pedido.java
    │   └── PasswordResetToken.java
    ├── repository/
    │   ├── UserRepository.java
    │   ├── ProductoRepository.java
    │   ├── PedidoRepository.java
    │   └── PasswordResetTokenRepository.java
    ├── service/
    │   ├── AuthService.java (interfaz)
    │   ├── UserService.java (interfaz)
    │   └── impl/
    │       ├── AuthServiceImpl.java
    │       └── UserServiceImpl.java
    ├── controller/
    │   ├── AuthController.java
    │   ├── AdminController.java
    │   ├── ProductoController.java
    │   └── PedidoController.java
    ├── config/
    │   ├── SecurityConfig.java
    │   ├── JwtUtil.java
    │   └── JwtAuthFilter.java
    ├── dto/
    │   ├── request/
    │   │   ├── RegisterRequest.java
    │   │   ├── LoginRequest.java
    │   │   └── PasswordResetRequest.java
    │   └── response/
    │       ├── AuthResponse.java
    │       └── UserResponse.java
    └── exception/
        └── GlobalExceptionHandler.java
```

---

##  Schema de Base de Datos (MongoDB)

### Colección `users`
| Campo | Tipo | Descripción |
|---|---|---|
| id | String (PK) | Identificador único |
| nombre | String | Nombre del usuario |
| correo | String (UK) | Correo electrónico único |
| password | String | Contraseña encriptada (BCrypt) |
| rol | String | CLIENTE o ADMINISTRADOR |
| activo | Boolean | Estado de la cuenta |
| creadoEn | DateTime | Fecha de registro |
| ultimoLogin | DateTime | Último acceso |

### Colección `productos`
| Campo | Tipo | Descripción |
|---|---|---|
| id | String (PK) | Identificador único |
| nombre | String | Nombre del plato |
| descripcion | String | Descripción del plato |
| precio | Double | Precio en COP |
| imagen | String | URL de la imagen |
| categoria | String | Categoría del plato |

### Colección `pedidos`
| Campo | Tipo | Descripción |
|---|---|---|
| id | String (PK) | Identificador único |
| usuario | Object | Nombre y correo del cliente |
| items | Array | Productos con nombre, precio y cantidad |
| total | Double | Total del pedido en COP |
| estado | String | pendiente / en proceso / entregado |
| createdAt | DateTime | Fecha y hora del pedido |

### Colección `password_reset_tokens`
| Campo | Tipo | Descripción |
|---|---|---|
| id | String (PK) | Identificador único |
| userId | String (FK) | Referencia al usuario |
| token | String (UK) | Token UUID generado |
| expiracion | DateTime | Fecha de expiración (1 hora) |
| usado | Boolean | Si ya fue utilizado |

---

##  Endpoints de la API

### Autenticación (`/api/auth`) — público
| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/register` | Registro de cliente |
| POST | `/api/auth/login` | Inicio de sesión |
| POST | `/api/auth/password-reset/request` | Solicitar recuperación de contraseña |
| POST | `/api/auth/password-reset/confirm` | Confirmar nueva contraseña |

### Administrador (`/api/admin`) — requiere rol ADMINISTRADOR
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/admin/users` | Listar todos los usuarios |
| GET | `/api/admin/users/{id}` | Obtener usuario por ID |
| PATCH | `/api/admin/users/{id}/role` | Cambiar rol de usuario |
| DELETE | `/api/admin/users/{id}` | Eliminar usuario |

### Productos (`/api/productos`) — público
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/productos` | Listar todos los productos |
| POST | `/api/productos` | Crear nuevo producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |

### Pedidos (`/api/pedidos`) — requiere token
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/pedidos` | Listar todos los pedidos |
| POST | `/api/pedidos` | Crear nuevo pedido |
| PUT | `/api/pedidos/{id}` | Actualizar estado del pedido |

---

##  Componente Reactivo

Todo el flujo usa programación reactiva con **Project Reactor**:
- `Mono<T>` — para operaciones que retornan un solo elemento (login, registro, buscar usuario, guardar pedido)
- `Flux<T>` — para operaciones que retornan múltiples elementos (listar usuarios, productos, pedidos)
- Los repositorios extienden `ReactiveMongoRepository`
- La seguridad usa `@EnableWebFluxSecurity` con un `JwtAuthFilter` no bloqueante
- Ningún hilo se bloquea esperando respuesta de MongoDB

---

##  Frontend React

| Pantalla | Descripción |
|---|---|
| `/` AuthPage | Login y registro con tabs |
| `/home` HomePage | Menú del restaurante con carrito y pedido por WhatsApp |
| `/admin` AdminPage | Panel admin con tabs: Productos, Pedidos, Usuarios |

---

##  Configuración

En `src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://<usuario>:<password>@<cluster>.mongodb.net/lamaison_db?retryWrites=true&w=majority&appName=<appName>
spring.data.mongodb.database=lamaison_db
server.port=8080
jwt.secret=lamaison-secret-key-muy-segura-2024
jwt.expiration=86400000
```

---

##  Documentación API

Swagger UI disponible en:
```
http://localhost:8080/swagger-ui.html
```

---

##  Integrantes

- Jhon Hader — Unicatólica (Tec. Desarrollo de Software)
- Danilo Mejia — Unicatólica (Tec. Desarrollo de Software)
