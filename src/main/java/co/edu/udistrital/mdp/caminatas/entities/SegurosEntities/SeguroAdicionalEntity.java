package co.edu.udistrital.mdp.caminatas.entities.SegurosEntities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "seguros_adicionales")
@EqualsAndHashCode(callSuper = true)
public class SeguroAdicionalEntity extends SeguroEntity {

    @Column(length = 500)
    private String descripcionSeguroAdicional;
}
