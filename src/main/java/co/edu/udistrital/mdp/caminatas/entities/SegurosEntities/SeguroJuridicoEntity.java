package co.edu.udistrital.mdp.caminatas.entities.SegurosEntities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "seguros_juridicos")
@EqualsAndHashCode(callSuper = true)
public class SeguroJuridicoEntity extends SeguroEntity {

    @Column(nullable = false)
    private String tipoSeguro;
}
