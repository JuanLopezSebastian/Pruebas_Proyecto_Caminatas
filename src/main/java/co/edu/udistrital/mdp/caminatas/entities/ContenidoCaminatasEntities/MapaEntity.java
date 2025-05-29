package co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import java.util.ArrayList;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "mapas")
@EqualsAndHashCode(callSuper = true)
public class MapaEntity extends BaseEntity {

    @Column(nullable = false)
    private String descripcion;

    @ElementCollection
    private List<Coordenada> coordenadasGenerales; // Coordenadas del mapa base (opcional)

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mapa_id")
    @Size(min = 1, max = 5, message = "El mapa debe tener entre 1 y 5 rutas.")
    private List<RutaEntity> rutas = new ArrayList<>();

}

