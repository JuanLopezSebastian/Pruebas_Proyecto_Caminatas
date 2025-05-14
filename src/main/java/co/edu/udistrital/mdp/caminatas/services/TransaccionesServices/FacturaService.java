package co.edu.udistrital.mdp.caminatas.services.TransaccionesServices;

import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.FacturaEntity;
import co.edu.udistrital.mdp.caminatas.repositories.TransaccionesRepositories.I_FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private I_FacturaRepository facturaRepository;

    public List<FacturaEntity> findAll() {
        return facturaRepository.findAll();
    }

    public Optional<FacturaEntity> findById(Long id) {
        return facturaRepository.findById(id);
    }

    public FacturaEntity save(FacturaEntity factura) {
        return facturaRepository.save(factura);
    }

    public void delete(Long id) {
        facturaRepository.deleteById(id);
    }
}

