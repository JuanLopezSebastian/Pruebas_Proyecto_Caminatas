package co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities;

import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.List;

@Entity
@Data

public class AdministradorAppEntity extends UsuarioEntity {
    private String rol;
    
    @OneToMany
    private List<CaminataEntity> caminatasGestionadas;
}
