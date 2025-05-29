package co.edu.udistrital.mdp.caminatas.controllers.UsuariosControllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.InicioSesionDTO.LoginRequestDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO.UsuarioNaturalDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.InicioSesionResponseDTO.LoginResponseDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.UsuariosResponsesDTO.UsuarioNaturalResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.services.UsuariosServices.UsuarioNaturalService;
import co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration.CustomUserDetails;

@Tag(name = "Usuarios Naturales", description = "Operaciones relacionadas con usuarios tipo natural")
@RestController
@RequestMapping("/usuarios/naturales")
public class UsuarioNaturalController {

    private final UsuarioNaturalService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioNaturalController(UsuarioNaturalService usuarioService, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Crear un usuario natural", description = "Registra un nuevo usuario tipo NATURAL en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud malformada o datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Conflicto: el usuario ya existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<UsuarioNaturalResponseDTO> create(@Valid @RequestBody UsuarioNaturalDTO dto) {
        UsuarioNaturalEntity usuario = new UsuarioNaturalEntity();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setRol(dto.getRol());
        usuario.setCorreo(dto.getCorreo());
        usuario.setCedula(dto.getCedula());
        usuario.setTelefono(dto.getTelefono());

        // Hashea la contraseña
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        usuario.setPasswordHash(hashedPassword);

        UsuarioNaturalEntity guardado = usuarioService.save(usuario);
        return ResponseEntity.status(201).body(usuarioService.toResponseDTO(guardado));
    }

    @Operation(summary = "Login usuario natural", description = "Inicia sesión para usuarios naturales")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = usuarioService.login(request.getIdentificador(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener perfil usuario registrado", description = "Obtiene el perfil del usuario registrado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil obtenido exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('NATURAL') or hasRole('SUPER_ADMIN')")
    @GetMapping("/perfil")
    public ResponseEntity<?> perfil(@AuthenticationPrincipal CustomUserDetails user) {
        String correo = user.getCorreo();

        // Buscar en la base de datos si el usuario sigue existiendo
        Optional<UsuarioNaturalEntity> usuarioOpt = usuarioService.findAll().stream()
            .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario ya no existe en el sistema.");
        }

        UsuarioNaturalEntity usuario = usuarioOpt.get();
        return ResponseEntity.ok(usuarioService.toResponseDTO(usuario));
    }

    @Operation(summary = "Obtener una lista de los usuarios naturales", description = "Obtiene todos los usuarios naturales registrados")
    @GetMapping
    public ResponseEntity<List<UsuarioNaturalResponseDTO>> getAll() {
        List<UsuarioNaturalResponseDTO> usuarios = usuarioService.findAll().stream()
            .map(usuarioService::toResponseDTO)
            .toList();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener un usuario natural por ID", description = "Obtiene un usuario{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioNaturalResponseDTO> getById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuarioService::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/actualizar-perfil")
    @PreAuthorize("hasRole('NATURAL')")
    public ResponseEntity<?> actualizarMiPerfil(@Valid @RequestBody UsuarioNaturalDTO dto,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        Optional<UsuarioNaturalEntity> usuarioOpt = usuarioService.findAll().stream()
            .filter(u -> u.getCorreo().equalsIgnoreCase(userDetails.getCorreo()))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        UsuarioNaturalEntity usuario = usuarioOpt.get();

        // Actualizar datos permitidos
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setCedula(dto.getCedula());
        usuario.setTelefono(dto.getTelefono());

        UsuarioNaturalEntity actualizado = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioService.toResponseDTO(actualizado));
    }


    
    @Operation(summary = "Eliminar cuenta del usuario natural")
    @DeleteMapping("/eliminar-cuenta")
    @PreAuthorize("hasRole('NATURAL')")
    public ResponseEntity<Void> eliminarMiCuenta(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String correo = userDetails.getCorreo(); // Usa el correo directamente del token

        Optional<UsuarioNaturalEntity> usuarioOpt = usuarioService.findAll().stream()
            .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.delete(usuarioOpt.get().getId());
        return ResponseEntity.noContent().build();
    }
    


}

