package co.edu.udistrital.mdp.caminatas.controllers.UsuariosControllers;

import co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration.CustomUserDetails;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.InicioSesionDTO.LoginRequestDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO.AdministradorComentariosAppDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.InicioSesionResponseDTO.LoginResponseDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.UsuariosResponsesDTO.UsuarioResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.ComentariosEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioEntity;
import co.edu.udistrital.mdp.caminatas.services.UsuariosServices.AdministradorComentariosAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Administrador Comentarios de la App", description = "Operaciones disponibles para administradores (comentarios)")
@RestController
@RequestMapping("/usuarios/admin-comentarios")
@RequiredArgsConstructor
public class AdministradorComentariosAppController {

    private final AdministradorComentariosAppService adminService;

    //No hay método para crear un administrador de comentarios, ya que se asume que este usuario es creado por un SUPER_ADMIN o durante el registro inicial.
    @Operation(summary = "Login administrador de comentarios")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = adminService.login(request.getIdentificador(), request.getPassword());
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Listar todos los comentarios del sistema")
    @PreAuthorize("hasAnyRole('ADMIN_COMENTARIOS', 'SUPER_ADMIN')")
    @GetMapping("/comentarios")
    public ResponseEntity<List<ComentariosEntity>> listarComentarios() {
        return ResponseEntity.ok(adminService.findAll());
    }

    @Operation(summary = "Obtener comentarios por ID de caminata")
    @PreAuthorize("hasAnyRole('ADMIN_COMENTARIOS', 'SUPER_ADMIN')")
    @GetMapping("/comentarios/caminata/{caminataId}")
    public ResponseEntity<List<ComentariosEntity>> comentariosPorCaminata(@PathVariable Long caminataId) {
        return ResponseEntity.ok(adminService.findByCaminataId(caminataId));
    }

    @Operation(summary = "Buscar un comentario específico dentro de una caminata")
    @PreAuthorize("hasAnyRole('ADMIN_COMENTARIOS', 'SUPER_ADMIN')")
    @GetMapping("/comentarios/caminata/{caminataId}/comentario/{comentarioId}")
    public ResponseEntity<ComentariosEntity> obtenerComentarioEspecifico(
            @PathVariable Long caminataId,
            @PathVariable Long comentarioId) {
        ComentariosEntity comentario = adminService.findComentarioByIdAndCaminataId(comentarioId, caminataId);
        return ResponseEntity.ok(comentario);
    }

    @Operation(summary = "Obtener perfil del administrador de comentarios autenticado")
    @PreAuthorize("hasRole('ADMIN_COMENTARIOS')")
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponseDTO> obtenerPerfil(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UsuarioEntity usuario = adminService.buscarPorCorreo(userDetails.getCorreo());
        return ResponseEntity.ok(adminService.toDTO(usuario));
    }

    @Operation(summary = "Actualizar perfil del administrador de comentarios")
    @PreAuthorize("hasRole('ADMIN_COMENTARIOS')")
    @PutMapping("/actualizar-perfil")
    public ResponseEntity<UsuarioResponseDTO> actualizarPerfil(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AdministradorComentariosAppDTO dto) {
        
        UsuarioResponseDTO actualizado = adminService.actualizarPerfil(userDetails.getCorreo(), dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar cuenta del administrador de comentarios")
    @PreAuthorize("hasRole('ADMIN_COMENTARIOS')")
    @DeleteMapping("/eliminar-cuenta")
    public ResponseEntity<Void> eliminarMiCuenta(@AuthenticationPrincipal CustomUserDetails userDetails) {
        adminService.eliminarPorCorreo(userDetails.getCorreo());
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Eliminar un comentario por ID")
    @PreAuthorize("hasAnyRole('ADMIN_COMENTARIOS', 'SUPER_ADMIN')")
    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long id) {
        adminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}

