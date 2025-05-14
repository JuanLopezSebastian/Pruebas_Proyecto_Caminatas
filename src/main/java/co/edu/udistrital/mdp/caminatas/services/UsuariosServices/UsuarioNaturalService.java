package co.edu.udistrital.mdp.caminatas.services.UsuariosServices;

import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
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

    public UsuarioNaturalEntity save(UsuarioNaturalEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
