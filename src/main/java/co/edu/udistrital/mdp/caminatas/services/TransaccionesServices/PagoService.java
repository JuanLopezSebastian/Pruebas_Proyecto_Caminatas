package co.edu.udistrital.mdp.caminatas.services.TransaccionesServices;

import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.PagoEntity;
import co.edu.udistrital.mdp.caminatas.repositories.TransaccionesRepositories.I_PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private I_PagoRepository pagoRepository;

    public List<PagoEntity> findAll() {
        return pagoRepository.findAll();
    }

    public Optional<PagoEntity> findById(Long id) {
        return pagoRepository.findById(id);
    }

    public PagoEntity save(PagoEntity pago) {
        return pagoRepository.save(pago);
    }

    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }
}

