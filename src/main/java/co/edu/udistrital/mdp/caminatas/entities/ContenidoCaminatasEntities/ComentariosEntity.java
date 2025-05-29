package co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "comentarios")
@EqualsAndHashCode(callSuper = true)
public class ComentariosEntity extends BaseEntity {

    @Column(length = 500, nullable = false)
    private String descripcionComentario;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer calificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private UsuarioNaturalEntity autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminata_id", nullable = false)
    private CaminataEntity caminata;
}

