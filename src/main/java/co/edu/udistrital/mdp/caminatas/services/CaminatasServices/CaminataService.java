package co.edu.udistrital.mdp.caminatas.services.CaminatasServices;

import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.repositories.CaminatasRepositories.I_CaminataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaminataService {

    @Autowired
    private I_CaminataRepository caminataRepository;

    public List<CaminataEntity> findAll() {
        return caminataRepository.findAll();
    }

    public Optional<CaminataEntity> findById(Long id) {
        return caminataRepository.findById(id);
    }

    public CaminataEntity save(CaminataEntity caminata) {
        return caminataRepository.save(caminata);
    }

    public void delete(Long id) {
        caminataRepository.deleteById(id);
    }
}

