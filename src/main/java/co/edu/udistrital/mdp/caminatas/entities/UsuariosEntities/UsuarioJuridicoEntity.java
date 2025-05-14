package co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuarios_juridicos")
@EqualsAndHashCode(callSuper = true)
public class UsuarioJuridicoEntity extends UsuarioEntity {

    @Column(nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false)
    private String numParticipantes;
}