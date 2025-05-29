package co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;

@Data
@Entity
@Table(name = "galerias")
@EqualsAndHashCode(callSuper = true)
public class GaleriaEntity extends BaseEntity {

    @Column(nullable = false)
    private String imagenPrincipal;

    @ElementCollection
    @CollectionTable(name = "imagenes_galeria", joinColumns = @JoinColumn(name = "galeria_id"))
    private List<String> imagenesGaleria = new ArrayList<>();
}

