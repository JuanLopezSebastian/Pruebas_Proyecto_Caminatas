package co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "comentarios")
@EqualsAndHashCode(callSuper = true)
public class OpinionesComentariosEntity extends BaseEntity {
    
    @Column(length = 500)
    private String descripcionComentario;

    @Column
    private Integer calificacion; // entre 1 y 5

    @ManyToOne
    private UsuarioNaturalEntity autor;

    @ManyToOne
    private CaminataEntity caminata;
}
