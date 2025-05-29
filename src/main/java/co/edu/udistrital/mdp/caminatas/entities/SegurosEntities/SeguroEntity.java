package co.edu.udistrital.mdp.caminatas.entities.SegurosEntities;

import java.math.BigDecimal;

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

    @Column(nullable = false)
    private String descripcionSeguro;

    @Column(nullable = false)
    private BigDecimal costoSeguro; // ✅ Usar BigDecimal en vez de Long
}
