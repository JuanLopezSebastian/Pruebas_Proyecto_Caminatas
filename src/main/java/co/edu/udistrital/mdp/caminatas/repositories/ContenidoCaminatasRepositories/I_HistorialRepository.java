package co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.HistorialEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_HistorialRepository extends JpaRepository <HistorialEntity, Long> {
    
}
