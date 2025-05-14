package co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;

@Data
@Entity
@Table(name = "galerias")
@EqualsAndHashCode(callSuper = true)
public class GaleriaEntity extends BaseEntity {

    @Column
    private String imagenPrincipal;

    @ElementCollection
    private List<String> imagenesGaleria;
}
