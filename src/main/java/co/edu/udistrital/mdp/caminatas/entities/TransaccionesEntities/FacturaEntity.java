package co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "facturas")
@EqualsAndHashCode(callSuper = true)
public class FacturaEntity extends BaseEntity {

    @Column
    private Long idFactura;

    @OneToOne
    private PagoEntity pago;
}
