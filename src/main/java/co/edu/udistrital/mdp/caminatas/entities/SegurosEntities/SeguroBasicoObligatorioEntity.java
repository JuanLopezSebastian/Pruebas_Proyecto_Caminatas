package co.edu.udistrital.mdp.caminatas.entities.SegurosEntities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "seguros_basicos")
@EqualsAndHashCode(callSuper = true)
public class SeguroBasicoObligatorioEntity extends SeguroEntity {
    @Column
    private String tipoSeguro;
}
