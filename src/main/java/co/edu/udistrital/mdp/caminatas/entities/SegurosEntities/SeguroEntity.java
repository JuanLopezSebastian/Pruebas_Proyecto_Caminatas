package co.edu.udistrital.mdp.caminatas.entities.SegurosEntities;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "seguros")
@EqualsAndHashCode(callSuper = true)
public abstract class SeguroEntity extends BaseEntity {

    @Column
    private String descripcionSeguro;

    @Column
    private Long costoSeguro;
}
