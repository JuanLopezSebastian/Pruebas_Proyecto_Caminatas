package co.edu.udistrital.mdp.caminatas.services.TransaccionesServices;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.TransaccionesDTO.PagoDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.TransaccionesResponsesDTO.PagoResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.InscripcionesEntities.InscripcionUsuarioEntity;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.EstadoPago;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.FacturaEntity;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.MetodoPago;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.PagoEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.NotFoundException;
import co.edu.udistrital.mdp.caminatas.repositories.InscripcionesRepositories.I_InscripcionUsuarioRepository;
import co.edu.udistrital.mdp.caminatas.repositories.TransaccionesRepositories.I_FacturaRepository;
import co.edu.udistrital.mdp.caminatas.repositories.TransaccionesRepositories.I_PagoRepository;
import co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices.HistorialFacturaPagoService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final I_PagoRepository pagoRepository;
    private final I_FacturaRepository facturaRepository;
    private final I_InscripcionUsuarioRepository inscripcionRepository;
    private final HistorialFacturaPagoService historialService;

    public PagoResponseDTO toResponseDTO(PagoEntity pago) {
        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setId(pago.getId());
        dto.setMonto(pago.getMonto());
        dto.setMetodo(pago.getMetodo());
        dto.setEstado(pago.getEstado());
        dto.setFechaPago(pago.getFechaPago());
        dto.setFacturaId(pago.getFactura() != null ? pago.getFactura().getId() : null);
        return dto;
    }

    public PagoEntity registrarPago(Long facturaId, PagoEntity pago) {
        FacturaEntity factura = obtenerFactura(facturaId);
        pago.setFactura(factura);
        PagoEntity guardado = pagoRepository.save(pago);
        historialService.registrar(factura, guardado);
        return guardado;
    }

    public PagoEntity registrarPagoMock(Long facturaId, BigDecimal monto, MetodoPago metodo) {
        return procesarPago(facturaId, monto, metodo);
    }

    public PagoEntity simularPago(PagoDTO dto) {
        return procesarPago(dto.getFacturaId(), dto.getMonto(), dto.getMetodo());
    }

    // 🔁 Método centralizado
    private PagoEntity procesarPago(Long facturaId, BigDecimal monto, MetodoPago metodo) {
        FacturaEntity factura = obtenerFactura(facturaId);

        PagoEntity pago = new PagoEntity();
        pago.setFactura(factura);
        pago.setMonto(monto);
        pago.setMetodo(metodo);
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstado(metodo == MetodoPago.MOCK ? EstadoPago.APROBADO : EstadoPago.PENDIENTE);

        PagoEntity guardado = pagoRepository.save(pago);
        historialService.registrar(factura, guardado);

        // ✅ Si es exitoso, marcar inscripción como pagada
        if (guardado.getEstado() == EstadoPago.APROBADO) {
            InscripcionUsuarioEntity inscripcion = factura.getInscripcion();
            inscripcion.setEstadoPago(true);
            inscripcionRepository.save(inscripcion);
        }

        return guardado;
    }

    private FacturaEntity obtenerFactura(Long id) {
        return facturaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Factura no encontrada"));
    }

    public List<PagoEntity> findAll() {
        return pagoRepository.findByActivoTrue();
    }

    public Optional<PagoEntity> findById(Long id) {
        return pagoRepository.findById(id)
            .filter(PagoEntity::isActivo);
    }

    public PagoEntity save(PagoEntity pago) {
        return pagoRepository.save(pago);
    }

    public void delete(Long id) {
        PagoEntity pago = pagoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        pago.setActivo(false);
        pagoRepository.save(pago);
    }
}
