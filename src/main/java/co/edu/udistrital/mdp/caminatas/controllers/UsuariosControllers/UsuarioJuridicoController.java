package co.edu.udistrital.mdp.caminatas.controllers.UsuariosControllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration.CustomUserDetails;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.InicioSesionDTO.LoginRequestDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO.UsuarioJuridicoDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.InicioSesionResponseDTO.LoginResponseDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.UsuariosResponsesDTO.UsuarioJuridicoResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioJuridicoEntity;
import co.edu.udistrital.mdp.caminatas.services.UsuariosServices.UsuarioJuridicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios/juridicos")
@Tag(name = "Usuarios Jurídicos", description = "Operaciones para usuarios tipo jurídico")
@RequiredArgsConstructor
public class UsuarioJuridicoController {

    private final UsuarioJuridicoService usuarioService;

    @Operation(summary = "Registrar un nuevo usuario jurídico")
    @PostMapping
    public ResponseEntity<UsuarioJuridicoResponseDTO> create(@Valid @RequestBody UsuarioJuridicoDTO dto) {
        UsuarioJuridicoEntity guardado = usuarioService.save(dto);
        return ResponseEntity.status(201).body(usuarioService.toResponseDTO(guardado));
    }

    @Operation(summary = "Iniciar sesión como usuario jurídico")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = usuarioService.login(request.getIdentificador(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener perfil del usuario jurídico autenticado")
    @PreAuthorize("hasRole('JURIDICO')")
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioJuridicoResponseDTO> perfil() {
        // Obtener el correo del token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();

        Optional<UsuarioJuridicoEntity> usuarioOpt = usuarioService.findAll().stream()
            .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        UsuarioJuridicoResponseDTO dto = usuarioService.toResponseDTO(usuarioOpt.get());
        return ResponseEntity.ok(dto);
    }
    
    @Operation(summary = "Obtener todos los usuarios jurídicos")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioJuridicoResponseDTO>> getAll() {
        List<UsuarioJuridicoResponseDTO> lista = usuarioService.findAll().stream()
            .map(usuarioService::toResponseDTO)
            .toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Actualizar datos del usuario jurídico autenticado")
    @PreAuthorize("hasRole('JURIDICO')")
    @PutMapping("/actualizar")
    public ResponseEntity<UsuarioJuridicoResponseDTO> actualizarMiPerfil(@Valid @RequestBody UsuarioJuridicoDTO dto) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioJuridicoResponseDTO actualizado = usuarioService.updateFromToken(correo, dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar cuenta del usuario jurídico autenticado")
    @PreAuthorize("hasRole('JURIDICO')")
    @DeleteMapping("/eliminar-cuenta")
    public ResponseEntity<Void> eliminarMiCuenta(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String correo = userDetails.getCorreo();
        Optional<UsuarioJuridicoEntity> usuarioOpt = usuarioService.findAll().stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst();

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        usuarioService.delete(usuarioOpt.get().getId());
        return ResponseEntity.noContent().build();
    }

    

}
