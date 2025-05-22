package co.edu.udistrital.mdp.caminatas.dto.TransaccionesDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FacturaDTO {

    @Schema(description = "ID de la factura", example = "1")
    @NotNull(message = "El ID de la factura es obligatorio.")
    @Min(value = 1, message = "El ID de la factura debe ser mayor que cero.")
    private Long idFactura;

    @Schema(description = "ID del pago asociado a la factura", example = "1")
    @NotNull(message = "El ID del pago asociado es obligatorio.")
    private Long idPago;
}
