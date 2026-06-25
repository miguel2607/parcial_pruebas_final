package com.ticketfast.system;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Punto 3: Prueba de Sistema (20%)
 *
 * Esta prueba valida el flujo completo de extremo a extremo realizando
 * peticiones HTTP reales contra el servicio en contenedores:
 * 1. POST para crear una reserva en zona General con 3 asientos
 * 2. GET para obtener el resumen del evento
 * 3. Validar que el total_recaudado sea exactamente 150000 (3 * 50000)
 */
public class SystemE2ETest {

    @BeforeAll
    public static void setup() {
        // Configurar la URL base apuntando al contenedor
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8001;
    }

    @Test
    public void testFlujoCompletoSistema() {
        String eventoId = "sistema-evento-xyz";

        // 1. Crear reserva con zona General y cantidad 3
        String requestBody = """
            {
                "clienteEmail": "sistema@test.com",
                "zona": "General",
                "cantidad": 3
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/reservas/" + eventoId)
        .then()
            .statusCode(201)
            .body("mensaje", equalTo("Reserva creada con exito"));

        // 2. Obtener resumen del evento
        // 3. Validar total_recaudado = 3 * 50000 = 150000
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/reservas/" + eventoId + "/resumen")
        .then()
            .statusCode(200)
            .body("evento_id", equalTo(eventoId))
            .body("total_recaudado", equalTo(150000.0f));
    }
}
