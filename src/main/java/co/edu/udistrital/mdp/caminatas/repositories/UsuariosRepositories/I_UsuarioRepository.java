package co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioEntity;

@Repository
public interface I_UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByCorreo(String correo); // ✅ Mejor práctica

    boolean existsByCorreo(String correo);
    boolean existsByCedula(Long cedula);
    boolean existsByNombreUsuario(String nombreUsuario);
}



