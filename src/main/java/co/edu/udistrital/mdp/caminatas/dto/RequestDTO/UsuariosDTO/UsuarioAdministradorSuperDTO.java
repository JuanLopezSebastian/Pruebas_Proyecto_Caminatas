package co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioAdministradorSuperDTO {
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String correo;
    @NotNull
    private Long cedula;
    @NotNull
    private Long telefono;
    @NotBlank
    private String password;
    
}
