package co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioJuridicoEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_UsuarioJuridicoRepository extends JpaRepository<UsuarioJuridicoEntity, Long> {

}
