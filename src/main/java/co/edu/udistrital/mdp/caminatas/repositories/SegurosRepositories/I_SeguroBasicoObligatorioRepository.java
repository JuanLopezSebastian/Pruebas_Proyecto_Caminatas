package co.edu.udistrital.mdp.caminatas.repositories.SegurosRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.caminatas.entities.SegurosEntities.SeguroBasicoObligatorioEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface I_SeguroBasicoObligatorioRepository extends JpaRepository<SeguroBasicoObligatorioEntity, Long>{
    Optional<SeguroBasicoObligatorioEntity> findFirstByOrderByIdAsc();
}
