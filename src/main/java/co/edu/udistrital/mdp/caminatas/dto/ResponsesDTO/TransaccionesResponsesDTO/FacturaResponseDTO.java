package co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.TransaccionesResponsesDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.TransaccionesDTO.PagoSimpleDTO;

@Data
public class FacturaResponseDTO {

    private Long id;
    private BigDecimal total;
    private LocalDate fechaCreacion;
    private String descripcion;
    private Boolean pagada;

    private Long inscripcionId;
    private Long usuarioId;

    private List<PagoSimpleDTO> pagos; // información resumida de pagos
}
