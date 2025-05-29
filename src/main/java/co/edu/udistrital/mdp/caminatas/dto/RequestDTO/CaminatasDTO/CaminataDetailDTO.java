package co.edu.udistrital.mdp.caminatas.dto.RequestDTO.CaminatasDTO;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.GaleriaDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.MapaDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaminataDetailDTO extends CaminataDTO {

    @NotNull(message = "El mapa es obligatorio en el detalle.")
    private MapaDTO mapa;

    @NotNull(message = "La galería es obligatoria en el detalle.")
    private GaleriaDTO galeria;
}
