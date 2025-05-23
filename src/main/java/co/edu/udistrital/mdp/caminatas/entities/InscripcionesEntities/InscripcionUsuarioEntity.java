package co.edu.udistrital.mdp.caminatas.entities.InscripcionesEntities;

import java.time.LocalDate;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "inscripciones")
@EqualsAndHashCode(callSuper = true)
public class InscripcionUsuarioEntity extends BaseEntity {

    @Column
    private LocalDate fechaInscripcion;

    @Column
    private Boolean estadoPago;

    @ManyToOne
    private UsuarioNaturalEntity usuario;

    @ManyToOne
    private CaminataEntity caminata;
}
