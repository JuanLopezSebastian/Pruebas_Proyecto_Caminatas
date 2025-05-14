package co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuarios_naturales")
@EqualsAndHashCode(callSuper = true)
public class UsuarioNaturalEntity extends UsuarioEntity {

}

