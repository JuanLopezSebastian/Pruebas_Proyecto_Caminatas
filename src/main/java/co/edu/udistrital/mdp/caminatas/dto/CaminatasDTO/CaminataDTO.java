package co.edu.udistrital.mdp.caminatas.dto.CaminatasDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CaminataDTO {

    @Schema(description = "Nombre de la caminata", example = "Vuelo del condor")
    @NotBlank(message = "El nombre de la caminata es obligatorio.")
    private String nombreCaminata;

    @Schema(description = "Costo de la caminata", example = "100000.00")
    @NotNull(message = "El costo es obligatorio.")
    @DecimalMin(value = "0.0", inclusive = true, message = "El costo no puede ser negativo.")
    private BigDecimal costoCaminata;

    @Schema(description = "Nombre del patrocinador caminata", example = "Juan")
    @NotBlank(message = "El patrocinador es obligatorio.")
    private String patrocinador;

    @Schema(description = "Fecha de la caminata", example = "2025-04-10")
    @NotBlank(message = "La fecha es obligatoria.")
    private LocalDate fecha;  // Puedes usar LocalDate si lo manejas bien en el JSON

    @Schema(description = "Hora de la caminata", example = "07:00:00")
    @NotBlank(message = "La hora es obligatoria.")
    private LocalTime hora;

    @Schema(description = "Lugar de la caminata", example = "Paramo Sumapaz")
    @NotBlank(message = "El lugar es obligatorio.")
    private String lugar;

    @Schema(description = "Duración de la caminata", example = "6 horas")
    @NotBlank(message = "La duración es obligatoria.")
    private String duracion;

    @Schema(description = "Descripción de la caminata", example = "Observación de flora y fauna endemica")
    @NotBlank(message = "La descripción es obligatoria.")
    private String descripcion;

    @Schema(description = "Dificultad de la caminata", example = "Facil")
    @NotBlank(message = "La dificultad es obligatoria.")
    private String dificultad;

    @Schema(description = "Itinerario de la caminata", example = "Inicio a las 7am, regreso 1pm")
    @NotBlank(message = "El itinerario es obligatorio.")
    private String itinerario;

    @Schema(description = "Recomendaciones de la caminata", example = "Llevar alimentos, agua y binoculares")
    @NotBlank(message = "Las recomendaciones son obligatorias.")
    private String recomendaciones;
}

