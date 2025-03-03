
-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS mibasedatos;

-- Usar la base de datos
USE mibasedatos;

-- Crear la tabla personas con restricciones básicas
CREATE TABLE IF NOT EXISTS personas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- Insertar datos de prueba
INSERT INTO personas (nombre, email) VALUES 
('Juan Perez', 'juan.perez@example.com'),
('Ana Gomez', 'ana.gomez@example.com');
