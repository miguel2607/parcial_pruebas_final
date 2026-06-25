package com.ticketfast.integration;

import com.ticketfast.model.Reserva;
import com.ticketfast.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Punto 2: Prueba de Integracion de API (30%)
 *
 * Esta prueba realiza una peticion POST al endpoint /reservas/concierto-2026
 * y verifica:
 * 1. Que el codigo de estado HTTP sea 201 (Created)
 * 2. Que el registro exista en la base de datos mediante consulta directa con JPA
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservaRepository reservaRepository;

    @Test
    public void testCrearReservaIntegracion() throws Exception {
        // Preparar payload JSON
        String requestBody = """
            {
                "clienteEmail": "test@correo.com",
                "zona": "VIP",
                "cantidad": 2
            }
            """;

        // Realizar peticion POST y verificar codigo de estado 201
        mockMvc.perform(post("/reservas/concierto-2026")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje").value("Reserva creada con exito"))
                .andExpect(jsonPath("$.reserva_id").isNumber());

        // Verificar que el registro existe en la base de datos
        List<Reserva> reservas = reservaRepository.findByEventoId("concierto-2026");

        assertFalse(reservas.isEmpty(), "Debe existir al menos una reserva");

        Reserva reservaGuardada = reservas.get(0);
        assertEquals("test@correo.com", reservaGuardada.getClienteEmail(),
                    "El email del cliente debe coincidir");
        assertEquals("VIP", reservaGuardada.getZona(),
                    "La zona debe ser VIP");
        assertEquals(2, reservaGuardada.getCantidad(),
                    "La cantidad debe ser 2");
    }
}
