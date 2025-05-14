package co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities;

import co.edu.udistrital.mdp.caminatas.entities.BaseEntities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED) // Para herencia con UsuarioNatural y UsuarioJuridico
@EqualsAndHashCode(callSuper = true)
public abstract class UsuarioEntity extends BaseEntity {

    

    @Column(nullable = false)
    private String nombreUsuario;

    @Column(nullable = false)
    private String tipoUsuario;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private Long cedula;

    @Column(nullable = false)
    private Long telefono;
}
