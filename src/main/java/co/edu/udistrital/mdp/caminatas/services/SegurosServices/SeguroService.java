package co.edu.udistrital.mdp.caminatas.services.SegurosServices;

import co.edu.udistrital.mdp.caminatas.entities.SegurosEntities.SeguroEntity;
import co.edu.udistrital.mdp.caminatas.repositories.SegurosRepositories.I_SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguroService {

    @Autowired
    private I_SeguroRepository seguroRepository;

    public List<SeguroEntity> findAll() {
        return seguroRepository.findAll();
    }

    public Optional<SeguroEntity> findById(Long id) {
        return seguroRepository.findById(id);
    }

    public SeguroEntity save(SeguroEntity seguro) {
        return seguroRepository.save(seguro);
    }

    public void delete(Long id) {
        seguroRepository.deleteById(id);
    }
}

