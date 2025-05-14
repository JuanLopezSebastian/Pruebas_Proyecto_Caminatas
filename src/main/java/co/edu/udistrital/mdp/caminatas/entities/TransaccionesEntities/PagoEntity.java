package co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "pagos")
@EqualsAndHashCode(callSuper = true)
public class PagoEntity extends BaseEntity {

    @Column
    private Long idPago;

    @OneToOne(mappedBy = "pago")
    private FacturaEntity factura;
}
