package co.edu.udistrital.mdp.caminatas.dto.RequestDTO.TransaccionesDTO;

import java.math.BigDecimal;

import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.MetodoPago;
import lombok.Data;

@Data
public class PagoDTO {
    private Long facturaId;
    private MetodoPago metodo;
    private BigDecimal monto;
}

