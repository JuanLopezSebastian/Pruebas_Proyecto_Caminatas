package co.edu.udistrital.mdp.caminatas.repositories.CaminatasRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataDeportivaEntity;

@Repository
public interface I_CaminataDeportivaRepository extends JpaRepository<CaminataDeportivaEntity, Long> {

}

