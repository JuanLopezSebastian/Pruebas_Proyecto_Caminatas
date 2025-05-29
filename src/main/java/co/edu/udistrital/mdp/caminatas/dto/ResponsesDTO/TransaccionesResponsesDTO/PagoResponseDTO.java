package co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.TransaccionesResponsesDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.EstadoPago;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.MetodoPago;

@Data
public class PagoResponseDTO {
    private Long id;
    private BigDecimal monto;
    private MetodoPago metodo;
    private EstadoPago estado;
    private LocalDateTime fechaPago;
    private Long facturaId;
}
