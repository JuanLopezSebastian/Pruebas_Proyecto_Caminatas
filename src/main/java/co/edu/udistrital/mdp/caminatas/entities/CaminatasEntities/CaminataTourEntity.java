package co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "caminatas_tour")
@EqualsAndHashCode(callSuper = true)
public class CaminataTourEntity extends CaminataEntity {
    
}
