
# 🥾 Proyecto Caminatas - API REST con Spring Boot

Este proyecto implementa una API REST para gestionar caminatas, usuarios, pagos, comentarios, seguros, entre otros módulos, usando **Java, Spring Boot, H2** (persistente) y **Swagger** para documentación interactiva.
Link Archivo Requerimientos Base:
https://docs.google.com/document/d/1lEyo4LKa3AaH6GPZtoMDofPq6aqc5Mm0eelrT5vCMQE/edit?usp=sharing
---

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3+
- Spring Data JPA
- Spring Validation
- H2 Database (modo archivo)
- Lombok
- Swagger / OpenAPI (`springdoc-openapi`)
- Maven

---

## 📁 Estructura del proyecto

```
src/main/java/co/edu/udistrital/mdp/caminatas/
│
├── config/                  # Configuraciones generales (Swagger, seguridad, etc.)
|      ├── SecurityConfiguration/
|      |           ├── CustomUserDetails.java
|      |           ├── JwtAuthentication.java
|      |           ├── JwtUtil.java
|      |           ├── SecurityConfig.java
|      |           ├── SuperAdminInitializer.java
|      ├── ApplicationConfig.java
|
├── controllers/             # Controladores REST agrupados por módulo
|      ├── CaminatasControllers/
|      |           ├── CaminataController.java
|      ├── ContenidoCaminatasControllers/
|      |           ├── BlogController.java
|      |           ├── ComentariosController.java
|      ├── SegurosControllers/
|      |           ├── SeguroController.java
|      ├── TransaccionesControllers/
|      |           ├── FacturaController.java
|      |           ├── PagoController.java
|      ├── UsuariosControllers/
|      |           ├── AdministradorComentariosAppController.java
|      |           ├── AdministradorSuperController.java
|      |           ├── UsuarioJuridicoController.java
|      |           ├── UsuarioNaturalController.java
|      ├── DefaultController.java
|
├── dto/                     # Clases DTO con validaciones
|      ├── CaminatasDTO/
|      |           ├── CaminataDTO.java
|      ├── ContenidoCaminatasDTO/
|      |           ├── BlogDTO.java
|      |           ├── ComentariosDTO.java
|      ├── SegurosDTO/
|      |           ├── SeguroDTO.java
|      ├── TransaccionesDTO/
|      |           ├── FacturaController.java
|      |           ├── PagoController.java
|      ├── UsuariosDTO/
|      |           ├── ResponsesDTO/
|      |           |       ├── UsuarioJuridicoResponseDTO.java
|      |           |       ├── UsuarioNaturalResponseDTO.java
|      |           |       ├── UsuarioResponseDTO.java
|      |           ├── AdministradorComentariosAppDTO.java
|      |           ├── UsuarioJuridicoDTO.java
|      |           ├── UsuarioNaturalDTO.java
|
├── entities/                # Entidades JPA organizadas por dominio
|      ├── BaseEntities/
|      |           ├── BaseEntity.java
|      ├── CaminatasEntities/
|      |           ├── CaminataDeportivaEntity.java
|      |           ├── CaminataEntity.java
|      |           ├── CaminataTourEntity.java
|      |           ├── ModalidadCaminataDeportivaEntity.java
|      |           ├── MontanaEntity.java
|      |           ├── SenderismoAvanzadoEntity.java
|      ├── ContenidoCaminatasEntities/
|      |           ├── BlogEntity.java
|      |           ├── CalendarioEntity.java
|      |           ├── ComentariosEntity.java
|      |           ├── GaleriaEntity.java
|      |           ├── HistorialEntity.java
|      |           ├── MapaEntity.java
|      |           ├── RutaEntity.java
|      ├── InscripcionesEntities/
|      |           ├── InscripcionUsuarioEntity.java
|      ├── SegurosEntities/
|      |           ├── SeguroAdicionalEntity.java
|      |           ├── SeguroBasicoObligatorioEntity.java
|      |           ├── SeguroEntity.java
|      |           ├── SeguroJuridicoEntity.java
|      ├── TransaccionesEntities/
|      |           ├── FacturaEntity.java
|      |           ├── PagoEntity.java
|      ├── UsuariosEntities/
|      |           ├── AdministradorComentariosAppEntity.java
|      |           ├── RolUsuario.java
|      |           ├── UsuarioEntity.java
|      |           ├── UsuarioJuridicoEntity.java
|      |           ├── UsuarioNaturalEntity.java
|
├── exceptions/              # Manejo global de errores (ApiError, handlers)
|      ├── BaseException/
|      |           ├── ApiError.java
|      ├── Handler/
|      |           ├── RestExceptionHandler.java
|      ├── http/
|      |           ├── ConflictException.java
|      |           ├── IllegalOperationException.java
|      |           ├── LowerCaseClassNameResolver.java
|      |           ├── NotFoundException.java
|      |           ├── UnauthorizedException.java
|
├── podam/                   # (Opcional) Datos de prueba automáticos
|      ├── DateStrategy.java
|
├── repositories/            # Repositorios Spring Data JPA
|      ├── CaminatasRepositories/
|      |           ├── I_CaminataDeportivarRepository.java
|      |           ├── I_CaminataRepository.java
|      |           ├── I_CaminataTourRepository.java
|      |           ├── I_ModalidadCaminataDeportivaRepository.java
|      |           ├── I_MontanaRepository.java
|      |           ├── I_SenderismoAvanzadoRepository.java
|      ├── ContenidoCaminatasRepositories/
|      |           ├── I_BlogRepository.java
|      |           ├── I_CalendarioRepository.java
|      |           ├── I_ComentariosRepository.java
|      |           ├── I_GaleriaRepository.java
|      |           ├── I_HistorialRepository.java
|      |           ├── I_MapaRepository.java
|      |           ├── I_RutaRepository.java
|      ├── InscripcionesRepositories/
|      |           ├── I_InscripcionUsuarioRepository.java
|      ├── SegurosRepositories/
|      |           ├── I_SeguroAdicionalRepository.java
|      |           ├── I_SeguroBasicoObligatorioRepository.java
|      |           ├── I_SeguroRepository.java
|      |           ├── I_SeguroJuridicoRepository.java
|      ├── TransaccionesRepositories/
|      |           ├── I_FacturaRepository.java
|      |           ├── I_PagoRepository.java
|      ├── UsuariosRepositories/
|      |           ├── I_AdministradorComentariosAppRepository.java
|      |           ├── I_UsuarioJuridicoRepository.java
|      |           ├── I_UsuarioNaturalRepository.java
|      |           ├── I_UsuarioRepository.java
|
├── services/                # Lógica de negocio
|      ├── CaminatasServices/
|      |           ├── CaminataService.java
|      ├── ContenidoCaminatasServices/
|      |           ├── BlogService.java
|      |           ├── ComentarioService.java
|      ├── InscripcionesServices/
|      |           ├── InscripcionUsuarioService.java
|      ├── SegurosServices/
|      |           ├── SeguroService.java
|      ├── TransaccionesServices/
|      |           ├── FacturaService.java
|      |           ├── PagoService.java
|      ├── UsuariosServices/
|      |           ├── AdministradorComentariosAppService.java
|      |           ├── AdministradorSuperService.java
|      |           ├── UsuarioJuridicoService.java
|      |           ├── UsuarioNaturalService.java
|      |           ├── UsuarioService.java
└── MainApplication.java     # Clase principal

src/main/
│
├── resources/                  # Propiedades de la aplicación
|      ├── application.properties
└── 
src
│
├── test/                  # Test
|      ├── java/
└── 
```
---

## 🧪 Base de datos H2 persistente

Este proyecto usa H2 en **modo archivo**, lo que permite persistir los datos entre reinicios.

- 🗂 **Archivo**: `./data/caminatas-db.mv.db`
- 🔍 Acceso web: `http://localhost:8080/api/h2-console`

### Configuración (en `application.properties`):

```properties
spring.datasource.url=jdbc:h2:file:./data/caminatas-db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

> ✅ Usa `jdbc:h2:file:./data/caminatas-db` como URL al conectarte desde la consola H2.

---

## 📚 Documentación de la API (Swagger UI)

Swagger está disponible en:

```
http://localhost:8080/api/swagger-ui/index.html
```

> Incluye DTOs documentados con `@Schema`, respuestas HTTP con `@ApiResponse` y agrupación por módulos con `@Tag`.

---

## ▶️ Cómo ejecutar el proyecto

1. Asegúrate de tener **Java 21+** y **Maven** instalados.
2. Ejecuta el proyecto:

```bash
./mvnw spring-boot:run
```

3. Accede a:
   - Swagger: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)
   - Consola H2: [http://localhost:8080/api/h2-console](http://localhost:8080/api/h2-console)

---

## ✅ Buenas prácticas aplicadas

- DTOs validados con `@Valid` y `@Schema`
- Controladores desacoplados de entidades
- `@RestControllerAdvice` global para manejo de errores
- `@Service` + `@Repository` por capa lógica
- Persistencia en H2 para desarrollo
- Rutas limpias: prefijo global `/api` definido en `application.properties`

---

## 📦 Recomendaciones

- Usa Postman para probar tus endpoints. La colección está en `collections/`.
- Si deseas cambiar a PostgreSQL, revisa la sección de configuración en `application.properties`.
- No olvides ignorar la base en Git (`/data/*.mv.db` en `.gitignore`).

---

## 🧑‍💻 Autor

Proyecto realizado como parte de un ejercicio académico para la Universidad Distrital.

---
