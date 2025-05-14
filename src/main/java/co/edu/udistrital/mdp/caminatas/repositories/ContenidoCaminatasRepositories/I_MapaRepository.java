package co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.MapaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_MapaRepository extends JpaRepository<MapaEntity, Long> {
    // Aquí puedes agregar métodos específicos para el repositorio de Mapa si es necesario.
    
}
