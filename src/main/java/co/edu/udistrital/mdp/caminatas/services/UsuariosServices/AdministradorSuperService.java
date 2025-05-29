package co.edu.udistrital.mdp.caminatas.services.UsuariosServices;

import co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration.JwtUtil;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO.AdministradorComentariosAppDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO.UsuarioJuridicoDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO.UsuarioNaturalDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.InicioSesionResponseDTO.LoginResponseDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.UsuariosResponsesDTO.UsuarioResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.AdministradorComentariosAppEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.RolUsuario;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioJuridicoEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.ConflictException;
import co.edu.udistrital.mdp.caminatas.exceptions.http.NotFoundException;
import co.edu.udistrital.mdp.caminatas.exceptions.http.UnauthorizedException;
import co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories.I_UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministradorSuperService {

    private final I_UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Value("${admin.super.correo}")
    private String correoSuperAdmin;


    public List<UsuarioEntity> listarTodos() {
        return usuarioRepository.findAll();
    }
    // Login para SuperAdministrador
    public LoginResponseDTO login(String identificador, String password) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findAll().stream()
            .filter(u -> u.getRol() == RolUsuario.SUPER_ADMIN)
            .filter(u -> u.getCorreo().equalsIgnoreCase(identificador)
                    || u.getNombreUsuario().equalsIgnoreCase(identificador))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }

        UsuarioEntity usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }

        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());

        return new LoginResponseDTO(usuario.getCorreo(), token, usuario.getRol());
    }

    // Buscar usuario por ID
    public UsuarioEntity buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    // Crear administrador de comentarios con todas las validaciones necesarias
    public UsuarioResponseDTO crearAdministradorComentarios(AdministradorComentariosAppDTO dto) {
        if (dto.getRol() == null) {
            throw new IllegalArgumentException("El campo 'rol' no puede ser nulo");
        }

        if (dto.getRol() != RolUsuario.ADMIN_COMENTARIOS) {
            throw new IllegalArgumentException("Este endpoint solo permite crear usuarios con rol ADMIN_COMENTARIOS");
        }

        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new ConflictException("Correo ya registrado");
        }

        if (usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
            throw new ConflictException("Nombre de usuario ya registrado");
        }

        if (usuarioRepository.existsByCedula(dto.getCedula())) {
            throw new ConflictException("Cédula ya registrada");
        }

        AdministradorComentariosAppEntity admin = new AdministradorComentariosAppEntity();
        admin.setRol(dto.getRol());  // debe ser ADMIN_COMENTARIOS
        admin.setNombreUsuario(dto.getNombreUsuario());
        admin.setCorreo(dto.getCorreo());
        admin.setCedula(dto.getCedula());
        admin.setTelefono(dto.getTelefono());
        admin.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        usuarioRepository.save(admin);

        return toDTO(admin);
    }



    // Actualizar perfil SuperAdministrador
    public UsuarioResponseDTO actualizarSuperAdmin(AdministradorComentariosAppDTO dto) {
        UsuarioEntity superAdmin = usuarioRepository.findAll().stream()
            .filter(u -> RolUsuario.SUPER_ADMIN.equals(u.getRol()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("SuperAdministrador no encontrado"));

        if (!superAdmin.getCorreo().equalsIgnoreCase(correoSuperAdmin)) {
            throw new ConflictException("No se puede modificar un usuario que no es el SuperAdministrador principal.");
        }

        // Actualiza solo información permitida
        superAdmin.setNombreUsuario(dto.getNombreUsuario());
        superAdmin.setCorreo(dto.getCorreo());  // Se permite actualizar correo
        superAdmin.setCedula(dto.getCedula());
        superAdmin.setTelefono(dto.getTelefono());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            superAdmin.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        usuarioRepository.save(superAdmin);
        return toDTO(superAdmin);
    }
    // Convertir UsuarioEntity a UsuarioResponseDTO
    public UsuarioResponseDTO toDTO(UsuarioEntity entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entity.getId());
        dto.setRol(entity.getRol());
        dto.setNombreUsuario(entity.getNombreUsuario());
        dto.setCorreo(entity.getCorreo());
        dto.setCedula(entity.getCedula());
        dto.setTelefono(entity.getTelefono());
        return dto;
    }
    
    // Buscar usuarios por rol
    public List<UsuarioEntity> buscarPorRol(RolUsuario rol) {
        return usuarioRepository.findAll().stream()
            .filter(u -> u.getRol() == rol)
            .toList();
    }

    // Buscar usuario por correo
    public UsuarioEntity buscarPorCorreo(String correo) {
        return usuarioRepository.findAll().stream()
            .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    // Actualizar usuario por ID
    public UsuarioResponseDTO actualizarUsuarioPorId(Long id, AdministradorComentariosAppDTO dto) {
        UsuarioEntity usuario = buscarPorId(id);

        if (usuario.getRol() == RolUsuario.SUPER_ADMIN) {
            throw new ConflictException("No puedes modificar al SuperAdministrador desde este endpoint.");
        }

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setCedula(dto.getCedula());
        usuario.setTelefono(dto.getTelefono());

        return toDTO(usuarioRepository.save(usuario));
    }

    // Actualizar perfil Usuario Nutural
    public UsuarioResponseDTO actualizarUsuarioNatural(Long id, UsuarioNaturalDTO dto) {
        UsuarioNaturalEntity usuario = (UsuarioNaturalEntity) usuarioRepository.findById(id)
            .filter(u -> u instanceof UsuarioNaturalEntity)
            .orElseThrow(() -> new NotFoundException("Usuario natural no encontrado"));

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCedula(dto.getCedula());
        return toDTO(usuarioRepository.save(usuario));
    }

    // Actualizar perfil Usuario Juridico
    public UsuarioResponseDTO actualizarUsuarioJuridico(Long id, UsuarioJuridicoDTO dto) {
        UsuarioJuridicoEntity usuario = (UsuarioJuridicoEntity) usuarioRepository.findById(id)
            .filter(u -> u instanceof UsuarioJuridicoEntity)
            .orElseThrow(() -> new NotFoundException("Usuario jurídico no encontrado"));

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCedula(dto.getCedula());
        usuario.setNombreEmpresa(dto.getNombreEmpresa());
        usuario.setNombresParticipantes(dto.getNombresParticipantes());
        usuario.setNumParticipantes(dto.getNombresParticipantes().size());
        return toDTO(usuarioRepository.save(usuario));
    }

    // Actualizar perfil Administrador de Comentarios
    public UsuarioResponseDTO actualizarUsuarioAdministradorComentarios(Long id, AdministradorComentariosAppDTO dto) {
        AdministradorComentariosAppEntity usuario = (AdministradorComentariosAppEntity) usuarioRepository.findById(id)
            .filter(u -> u instanceof AdministradorComentariosAppEntity)
            .orElseThrow(() -> new NotFoundException("Administrador de comentarios no encontrado"));

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCedula(dto.getCedula());
        return toDTO(usuarioRepository.save(usuario));
    }
    // Eliminar usuario por ID
    public void eliminarUsuario(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (usuario.getRol() == RolUsuario.SUPER_ADMIN) {
            throw new ConflictException("❌ No se puede eliminar al SuperAdministrador.");
        }

        usuarioRepository.deleteById(id);
    }



}

