# TicketFast - Sistema de Reservas

Sistema de reserva de boletos para eventos masivos con pruebas de integracion, sistema y E2E.

## Tecnologias

- Java 17
- Spring Boot 3.2.0
- PostgreSQL 16
- Docker
- JUnit 5
- REST Assured
- Selenium WebDriver

## Estructura del Proyecto

```
ticketfast/
├── src/main/java/com/ticketfast/
│   ├── model/           # Entidades JPA
│   ├── repository/      # Repositorios de datos
│   ├── controller/      # Controladores REST
│   └── TicketFastApplication.java
├── src/test/java/com/ticketfast/
│   ├── integration/     # Pruebas de integracion
│   ├── system/          # Pruebas de sistema
│   └── e2e/            # Pruebas E2E de frontend
└── docker-compose.test.yml
```

## Ejecutar las Pruebas

### Compilar el proyecto
```bash
mvn clean package -DskipTests
```

### Levantar contenedores de prueba
```bash
docker-compose -f docker-compose.test.yml up -d
```

### Ejecutar pruebas de integracion
```bash
mvn test -Dtest=ApiIntegrationTest
```

### Ejecutar pruebas de sistema
```bash
mvn test -Dtest=SystemE2ETest
```

### Ejecutar pruebas E2E
```bash
mvn test -Dtest=FrontendE2ETest
```

### Detener contenedores
```bash
docker-compose -f docker-compose.test.yml down
```

## Reglas de Negocio

### Zonas y Precios
- **VIP**: 150.000 COP por asiento
- **General**: 50.000 COP por asiento

### Restricciones
- Minimo 1 asiento por reserva
- Solo se aceptan zonas VIP y General
- Cada reserva esta asociada a un email

## Endpoints

### Crear Reserva
```
POST /reservas/{eventoId}
Content-Type: application/json

{
  "clienteEmail": "cliente@correo.com",
  "zona": "VIP",
  "cantidad": 2
}
```

### Obtener Resumen
```
GET /reservas/{eventoId}/resumen
```
