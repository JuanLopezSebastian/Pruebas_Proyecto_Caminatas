package co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration;

import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.AdministradorComentariosAppEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.RolUsuario;
import co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories.I_UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@RequiredArgsConstructor
public class SuperAdminInitializer {

    private static final Logger logger = LoggerFactory.getLogger(SuperAdminInitializer.class);
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initSuperAdmin(I_UsuarioRepository usuarioRepository) {
        return args -> {
            final String superAdminCorreo = "admin@app.com";
            // NO OLVIDAR CAMBIAR ESTO EN PRODUCCIÓN
            final String securePassword = "CambiaEstaContraseña2024!";
            // Aquí hay un error de seguridad, ya que la contraseña no debería estar en el código fuente.
            // En un entorno de producción, deberiamos usar un gestor de secretos o variables de entorno.
    
            if (!usuarioRepository.existsByCorreo(superAdminCorreo)) {
                AdministradorComentariosAppEntity admin = new AdministradorComentariosAppEntity();
                admin.setNombreUsuario("superadmin");
                admin.setCorreo(superAdminCorreo);
                admin.setCedula(999999999L);
                admin.setTelefono(1234567890L);
                admin.setRol(RolUsuario.SUPER_ADMIN);
                admin.setPasswordHash(passwordEncoder.encode(securePassword));

                usuarioRepository.save(admin);
                logger.info("✅ SuperAdministrador creado.");
            } else {
                logger.info("ℹ️ SuperAdministrador ya existe. No se creó uno nuevo.");
            }
        };
    }
}

