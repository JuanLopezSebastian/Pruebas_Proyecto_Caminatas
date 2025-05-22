package co.edu.udistrital.mdp.caminatas.dto.TransaccionesDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PagoDTO {
    
    @Schema(description = "ID del pago", example = "1")
    @NotNull(message = "El ID del pago es obligatorio.")
    @Min(value = 1, message = "El ID del pago debe ser mayor que cero.")
    private Long idPago;
}
