package co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.ComentariosEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_ComentariosRepository extends JpaRepository<ComentariosEntity, Long> {
    List<ComentariosEntity> findByCaminata_Id(Long caminataId);
    Optional<ComentariosEntity> findByIdAndCaminata_Id(Long id, Long caminataId);


}
