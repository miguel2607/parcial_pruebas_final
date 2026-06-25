package com.ticketfast.controller;

import com.ticketfast.model.Reserva;
import com.ticketfast.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @PostMapping("/{eventoId}")
    public ResponseEntity<?> crearReserva(
            @PathVariable String eventoId,
            @RequestBody ReservaRequest request) {

        // Validar zona
        if (!request.getZona().equals("VIP") && !request.getZona().equals("General")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("detail", "Zona invalida"));
        }

        // Validar cantidad
        if (request.getCantidad() < 1) {
            return ResponseEntity.badRequest()
                    .body(Map.of("detail", "Cantidad invalida"));
        }

        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setEventoId(eventoId);
        reserva.setClienteEmail(request.getClienteEmail());
        reserva.setZona(request.getZona());
        reserva.setCantidad(request.getCantidad());

        Reserva savedReserva = reservaRepository.save(reserva);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Reserva creada con exito");
        response.put("reserva_id", savedReserva.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{eventoId}/resumen")
    public ResponseEntity<?> obtenerResumen(@PathVariable String eventoId) {
        Double totalRecaudado = reservaRepository.calcularTotalEvento(eventoId);

        if (totalRecaudado == null) {
            totalRecaudado = 0.0;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("evento_id", eventoId);
        response.put("total_recaudado", totalRecaudado);

        return ResponseEntity.ok(response);
    }
}

class ReservaRequest {
    private String clienteEmail;
    private String zona;
    private Integer cantidad;

    // Getters y Setters
    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
