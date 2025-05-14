package co.edu.udistrital.mdp.caminatas.repositories.CaminatasRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.MontanaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_MontanaRepository extends JpaRepository<MontanaEntity, Long> {
    
    // Aquí puedes agregar métodos personalizados si es necesario
    
}
