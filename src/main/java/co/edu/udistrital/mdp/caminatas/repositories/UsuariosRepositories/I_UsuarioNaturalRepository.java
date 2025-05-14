package co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_UsuarioNaturalRepository extends JpaRepository<UsuarioNaturalEntity, Long> {

}
