 La Maison — Sistema Reactivo de Restaurante
Proyecto desarrollado para la asignatura de Programación Reactiva — Fundación Universitaria Católica Lumen Gentium (Unicatólica).

 Descripción del Proyecto
Sistema fullstack reactivo para la gestión del restaurante La Maison. Permite el acceso seguro al sistema mediante registro, inicio de sesión y control de roles, además de gestión de productos del menú, registro de pedidos y administración de usuarios.
El sistema distingue dos tipos de usuarios:

Cliente — puede registrarse, iniciar sesión, ver el menú, agregar productos al carrito y realizar pedidos por WhatsApp.
Administrador — gestiona productos, visualiza pedidos, administra usuarios y modifica roles.


 Stack Tecnológico
TecnologíaVersiónUsoJava17Lenguaje principal del backendSpring Boot3.5.13Framework baseSpring WebFluxIncluidoProgramación reactiva (Mono / Flux)Spring Data MongoDB ReactiveIncluidoRepositorios reactivosSpring Security (WebFlux)IncluidoSeguridad y control de accesoJWT (jjwt)0.11.5Autenticación statelessMongoDB AtlasCloudBase de datos NoSQL en la nubeReact19.2.0Frontend SPAVite7.xBundler del frontendAxios1.xCliente HTTP en el frontendReact Router DOM7.xEnrutamiento del frontendMaven3.xGestión de dependencias Java

 Estructura del Proyecto
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

 Schema de Base de Datos (MongoDB)
Colección users
CampoTipoDescripciónidString (PK)Identificador úniconombreStringNombre del usuariocorreoString (UK)Correo electrónico únicopasswordStringContraseña encriptada (BCrypt)rolStringCLIENTE o ADMINISTRADORactivoBooleanEstado de la cuentacreadoEnDateTimeFecha de registroultimoLoginDateTimeÚltimo acceso
Colección productos
CampoTipoDescripciónidString (PK)Identificador úniconombreStringNombre del platodescripcionStringDescripción del platoprecioDoublePrecio en COPimagenStringURL de la imagencategoriaStringCategoría del plato
Colección pedidos
CampoTipoDescripciónidString (PK)Identificador únicousuarioObjectNombre y correo del clienteitemsArrayProductos con nombre, precio y cantidadtotalDoubleTotal del pedido en COPestadoStringpendiente / en proceso / entregadocreatedAtDateTimeFecha y hora del pedido
Colección password_reset_tokens
CampoTipoDescripciónidString (PK)Identificador únicouserIdString (FK)Referencia al usuariotokenString (UK)Token UUID generadoexpiracionDateTimeFecha de expiración (1 hora)usadoBooleanSi ya fue utilizado

🔗 Endpoints de la API
Autenticación (/api/auth) — público
MétodoEndpointDescripciónPOST/api/auth/registerRegistro de clientePOST/api/auth/loginInicio de sesiónPOST/api/auth/password-reset/requestSolicitar recuperación de contraseñaPOST/api/auth/password-reset/confirmConfirmar nueva contraseña
Administrador (/api/admin) — requiere rol ADMINISTRADOR
MétodoEndpointDescripciónGET/api/admin/usersListar todos los usuariosGET/api/admin/users/{id}Obtener usuario por IDPATCH/api/admin/users/{id}/roleCambiar rol de usuarioDELETE/api/admin/users/{id}Eliminar usuario
Productos (/api/productos) — público
MétodoEndpointDescripciónGET/api/productosListar todos los productosPOST/api/productosCrear nuevo productoPUT/api/productos/{id}Actualizar productoDELETE/api/productos/{id}Eliminar producto
Pedidos (/api/pedidos) — requiere token
MétodoEndpointDescripciónGET/api/pedidosListar todos los pedidosPOST/api/pedidosCrear nuevo pedidoPUT/api/pedidos/{id}Actualizar estado del pedido

 Componente Reactivo
Todo el flujo usa programación reactiva con Project Reactor:

Mono<T> — para operaciones que retornan un solo elemento (login, registro, buscar usuario, guardar pedido)
Flux<T> — para operaciones que retornan múltiples elementos (listar usuarios, productos, pedidos)
Los repositorios extienden ReactiveMongoRepository
La seguridad usa @EnableWebFluxSecurity con un JwtAuthFilter no bloqueante
Ningún hilo se bloquea esperando respuesta de MongoDB


 Frontend React
PantallaDescripción/ AuthPageLogin y registro con tabs/home HomePageMenú del restaurante con carrito y pedido por WhatsApp/admin AdminPagePanel admin con tabs: Productos, Pedidos, Usuarios

 Configuración
En src/main/resources/application.properties:
propertiesspring.data.mongodb.uri=mongodb+srv://<usuario>:<password>@<cluster>.mongodb.net/lamaison_db?retryWrites=true&w=majority&appName=<appName>
spring.data.mongodb.database=lamaison_db
server.port=8080
jwt.secret=lamaison-secret-key-muy-segura-2024
jwt.expiration=86400000

 Documentación API
Swagger UI disponible en:
http://localhost:8080/swagger-ui.html

 Integrantes

Jhon Hader — Unicatólica (Tec. Desarrollo de Software)
Danilo Mejia — Unicatólica (Tec. Desarrollo de Software)
