package co.edu.udistrital.mdp.caminatas.services.UsuariosServices;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration.JwtUtil;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO.UsuarioJuridicoDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.InicioSesionResponseDTO.LoginResponseDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.UsuariosResponsesDTO.UsuarioJuridicoResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.RolUsuario;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioJuridicoEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.ConflictException;
import co.edu.udistrital.mdp.caminatas.exceptions.http.NotFoundException;
import co.edu.udistrital.mdp.caminatas.exceptions.http.UnauthorizedException;
import co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories.I_UsuarioRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UsuarioJuridicoService {

    private final I_UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO login(String identificador, String password) {
        Optional<UsuarioJuridicoEntity> usuarioOpt = usuarioRepository.findAll().stream()
            .filter(u -> u instanceof UsuarioJuridicoEntity)
            .map(u -> (UsuarioJuridicoEntity) u)
            .filter(u -> u.getCorreo().equalsIgnoreCase(identificador)
                    || u.getNombreUsuario().equalsIgnoreCase(identificador))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }

        UsuarioJuridicoEntity usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }

        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());

        return new LoginResponseDTO(usuario.getCorreo(), token, usuario.getRol());
    }



    public List<UsuarioJuridicoEntity> findAll() {
        return usuarioRepository.findAll().stream()
                .filter(u -> u instanceof UsuarioJuridicoEntity)
                .map(u -> (UsuarioJuridicoEntity) u)
                .toList();
    }

    public Optional<UsuarioJuridicoEntity> findById(Long id) {
        return usuarioRepository.findById(id)
                .filter(u -> u instanceof UsuarioJuridicoEntity)
                .map(u -> (UsuarioJuridicoEntity) u);
    }

    public UsuarioJuridicoEntity save(UsuarioJuridicoDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new ConflictException("Correo ya registrado");
        }
        if (usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
            throw new ConflictException("Nombre de usuario ya registrado");
        }
        if (usuarioRepository.existsByCedula(dto.getCedula())) {
            throw new ConflictException("Cédula ya registrada");
        }
        if (dto.getRol() == null) {
            throw new IllegalArgumentException("El campo 'rol' no puede ser nulo");
        }
        if (dto.getRol() == RolUsuario.SUPER_ADMIN) {
            throw new IllegalArgumentException("No está permitido crear usuarios con rol SUPER_ADMIN");
        }
        if (dto.getRol() != RolUsuario.JURIDICO) {
            throw new IllegalArgumentException("Este endpoint solo permite crear usuarios con rol JURIDICO");
        }

        UsuarioJuridicoEntity usuario = new UsuarioJuridicoEntity();

        usuario.setRol(dto.getRol());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setCedula(dto.getCedula());
        usuario.setTelefono(dto.getTelefono());
        usuario.setNombreEmpresa(dto.getNombreEmpresa());
        if (dto.getNombresParticipantes() == null || dto.getNombresParticipantes().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos un participante");
        }
        usuario.setNombresParticipantes(dto.getNombresParticipantes());
        usuario.setNumParticipantes(dto.getNombresParticipantes().size());

        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioJuridicoResponseDTO toResponseDTO(UsuarioJuridicoEntity e) {
        UsuarioJuridicoResponseDTO dto = new UsuarioJuridicoResponseDTO();
        dto.setId(e.getId());
        dto.setRol(e.getRol());
        dto.setNombreUsuario(e.getNombreUsuario());
        dto.setCorreo(e.getCorreo());
        dto.setCedula(e.getCedula());
        dto.setTelefono(e.getTelefono());
        dto.setNombreEmpresa(e.getNombreEmpresa());
        dto.setNumParticipantes(e.getNumParticipantes());
        dto.setNombresParticipantes(e.getNombresParticipantes());
        return dto;
    }

    public UsuarioJuridicoResponseDTO updateFromToken(String correoToken, UsuarioJuridicoDTO dto) {
        Optional<UsuarioJuridicoEntity> usuarioOpt = findAll().stream()
            .filter(u -> u.getCorreo().equalsIgnoreCase(correoToken))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            throw new NotFoundException("Usuario no encontrado");
        }

        UsuarioJuridicoEntity usuario = usuarioOpt.get();

        // Validaciones solo si cambia el valor
        if (!usuario.getCorreo().equalsIgnoreCase(dto.getCorreo()) &&
            usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new ConflictException("Correo ya registrado");
        }

        if (!usuario.getNombreUsuario().equalsIgnoreCase(dto.getNombreUsuario()) &&
            usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
            throw new ConflictException("Nombre de usuario ya registrado");
        }

        if (!usuario.getCedula().equals(dto.getCedula()) &&
            usuarioRepository.existsByCedula(dto.getCedula())) {
            throw new ConflictException("Cédula ya registrada");
        }

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setCedula(dto.getCedula());
        usuario.setTelefono(dto.getTelefono());
        usuario.setNombreEmpresa(dto.getNombreEmpresa());

        if (dto.getNombresParticipantes() != null && !dto.getNombresParticipantes().isEmpty()) {
            usuario.setNombresParticipantes(dto.getNombresParticipantes());
            usuario.setNumParticipantes(dto.getNombresParticipantes().size());
        }

        // Si viene una nueva contraseña
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        UsuarioJuridicoEntity actualizado = usuarioRepository.save(usuario);
        return toResponseDTO(actualizado);
    }

    
}