package co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 💡 Para usar @PreAuthorize con roles
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RateLimitingFilter rateLimitingFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, RateLimitingFilter rateLimitingFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.rateLimitingFilter = rateLimitingFilter;
    }
    //    Roles de usuario: SUPER_ADMIN, ADMIN_COMENTARIOS, NATURAL, JURIDICO
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            //.addFilterBefore(rateLimitingFilter, JwtAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                // Permitir login y registro
                .requestMatchers("/usuarios/naturales/login", "/usuarios/naturales").permitAll()
                .requestMatchers("/usuarios/juridicos/login", "/usuarios/juridicos").permitAll()
                .requestMatchers("/usuarios/admin-comentarios/login").permitAll()
                .requestMatchers("/admin/super/login").permitAll()

                // Swagger
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                // Rutas GET públicas como galerías, blogs, caminatas:
                .requestMatchers(HttpMethod.GET, "/galerias/**", "/blogs/**", "/caminatas/**", "/mapas/**", "/rutas/**").permitAll()
                .requestMatchers("/galerias").permitAll()
                .requestMatchers("/blogs").permitAll()
                .requestMatchers("/caminatas").permitAll()
                .requestMatchers("/mapas").permitAll()
                .requestMatchers("/rutas").permitAll()
                // Permitir acceso a inscripciones de caminatas
                .requestMatchers("/inscripciones").permitAll()

                // El resto, requiere autenticación
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}




