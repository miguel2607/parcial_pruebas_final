-- Script de inicializacion de base de datos TicketFast

-- Crear tabla de reservas
CREATE TABLE IF NOT EXISTS reservas (
    id SERIAL PRIMARY KEY,
    evento_id VARCHAR(50) NOT NULL,
    cliente_email VARCHAR(150) NOT NULL,
    zona VARCHAR(20) NOT NULL,
    cantidad INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear indices para mejorar rendimiento
CREATE INDEX IF NOT EXISTS idx_evento_id ON reservas(evento_id);
CREATE INDEX IF NOT EXISTS idx_cliente_email ON reservas(cliente_email);

-- Datos de prueba (opcional)
-- INSERT INTO reservas (evento_id, cliente_email, zona, cantidad)
-- VALUES ('concierto-demo', 'demo@correo.com', 'VIP', 2);
