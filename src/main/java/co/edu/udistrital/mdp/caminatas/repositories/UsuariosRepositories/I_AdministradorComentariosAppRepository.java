package co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.AdministradorComentariosAppEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_AdministradorComentariosAppRepository extends JpaRepository<AdministradorComentariosAppEntity, Long> {
    
}
