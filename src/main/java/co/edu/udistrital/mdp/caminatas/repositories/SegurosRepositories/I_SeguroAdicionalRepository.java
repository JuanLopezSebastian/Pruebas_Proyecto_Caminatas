package co.edu.udistrital.mdp.caminatas.repositories.SegurosRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.SegurosEntities.SeguroAdicionalEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_SeguroAdicionalRepository extends JpaRepository<SeguroAdicionalEntity, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    
}
