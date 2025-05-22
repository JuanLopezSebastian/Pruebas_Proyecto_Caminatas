package co.edu.udistrital.mdp.caminatas.services.UsuariosServices;

import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.ConflictException;
import co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories.I_UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioNaturalService {

    @Autowired
    private I_UsuarioRepository usuarioRepository;

    public List<UsuarioNaturalEntity> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u instanceof UsuarioNaturalEntity)
                .map(u -> (UsuarioNaturalEntity) u)
                .toList();
    }

    public Optional<UsuarioNaturalEntity> findById(Long id) {
        return usuarioRepository.findById(id)
                .filter(u -> u instanceof UsuarioNaturalEntity)
                .map(u -> (UsuarioNaturalEntity) u);
    }
    // Método para guardar un usuario natural
    // Verifica si el correo, cédula o nombre de usuario ya existen en la base de datos
    public UsuarioNaturalEntity save(UsuarioNaturalEntity usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new ConflictException("Ya existe un usuario con el correo: " + usuario.getCorreo());
        }

        if (usuarioRepository.existsByCedula(usuario.getCedula())) {
            throw new ConflictException("Ya existe un usuario con la cédula: " + usuario.getCedula());
        }

        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            throw new ConflictException("Ya existe un usuario con el nombre: " + usuario.getNombreUsuario());
        }
        
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
