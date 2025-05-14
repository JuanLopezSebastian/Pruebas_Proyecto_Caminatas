package co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;

@Data
@Entity
@Table(name = "blogs")
@EqualsAndHashCode(callSuper = true)
public class BlogEntity extends BaseEntity {
    
    @Id
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    private UsuarioNaturalEntity autor;

    @ElementCollection
    private List<String> imagenes;

    @ElementCollection
    private List<String> videos;

    @Column(columnDefinition = "TEXT")
    private String contenidoTextoBlog;
}

