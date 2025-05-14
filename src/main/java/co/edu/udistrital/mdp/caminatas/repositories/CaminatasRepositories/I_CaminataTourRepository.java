package co.edu.udistrital.mdp.caminatas.repositories.CaminatasRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataTourEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_CaminataTourRepository extends JpaRepository<CaminataTourEntity, Long> {

}
