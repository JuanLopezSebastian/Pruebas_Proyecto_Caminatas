package co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.GaleriaEntity;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.MapaEntity;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.RutaEntity;

@Data
@Entity
@Table(name = "caminatas")
@EqualsAndHashCode(callSuper = true)
public class CaminataEntity extends BaseEntity {

    @Column(nullable = false)
    private String nombreCaminata;

    @Column(nullable = false)
    private BigDecimal costoCaminata;

    @Column
    private String patrocinador;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private String lugar;

    @Column
    private String duracion;

    @Column
    private String descripcion;

    @Column
    private String dificultad;

    @Column
    private String itinerario;

    @Column
    private String recomendaciones;

    // Relaciones

    @OneToOne(cascade = CascadeType.ALL)
    private MapaEntity mapa;

    @OneToOne(cascade = CascadeType.ALL)
    private RutaEntity ruta;

    @OneToOne(cascade = CascadeType.ALL)
    private GaleriaEntity galeria;
}
