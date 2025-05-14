package co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "rutas")
@EqualsAndHashCode(callSuper = true)
public class RutaEntity extends BaseEntity {

    @Column
    private String detallesRuta;
}
