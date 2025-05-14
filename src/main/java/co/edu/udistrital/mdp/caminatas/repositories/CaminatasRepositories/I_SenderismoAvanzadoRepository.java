package co.edu.udistrital.mdp.caminatas.repositories.CaminatasRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.SenderismoAvanzadoEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_SenderismoAvanzadoRepository extends JpaRepository<SenderismoAvanzadoEntity, Long> {
    
}
