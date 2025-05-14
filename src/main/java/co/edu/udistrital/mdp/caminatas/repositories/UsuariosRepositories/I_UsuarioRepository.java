package co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioEntity;

@Repository
public interface I_UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UsuarioEntity findByCorreo(String correo);
}


