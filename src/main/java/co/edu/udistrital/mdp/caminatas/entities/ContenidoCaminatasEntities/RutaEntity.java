package co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities;

import java.util.ArrayList;
import java.util.List;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "rutas")
@EqualsAndHashCode(callSuper = true)
public class RutaEntity extends BaseEntity {

    @Column(nullable = false)
    private String nombreRuta;

    @Column(length = 500)
    private String descripcionRuta;

    @ElementCollection
    @CollectionTable(name = "coordenadas_ruta", joinColumns = @JoinColumn(name = "ruta_id"))
    private List<Coordenada> coordenadas = new ArrayList<>();
}


