-- EXAMEN PARCIAL II - ESTACIONAMIENTO
-- Paso 1: ejecute esta instrucción conectado a la base postgres.
CREATE DATABASE parcial2_estacionamiento;

-- Paso 2: conéctese a parcial2_estacionamiento antes de ejecutar lo siguiente.

CREATE TABLE vehiculo (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    propietario VARCHAR(100) NOT NULL,
    tipo VARCHAR(15) NOT NULL CHECK (tipo IN ('AUTOMOVIL', 'MOTOCICLETA')),
    hora_ingreso TIME NOT NULL,
    horas_utilizadas INTEGER NOT NULL CHECK (horas_utilizadas > 0),
    costo DECIMAL(10,2) NOT NULL CHECK (costo >= 0),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Cinco registros de ejemplo.
INSERT INTO vehiculo (placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo)
VALUES ('P123ABC', 'Ana López', 'AUTOMOVIL', '08:00', 3, 30.00);

INSERT INTO vehiculo (placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo)
VALUES ('M456DEF', 'Carlos Pérez', 'MOTOCICLETA', '09:15', 6, 32.40);

INSERT INTO vehiculo (placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo)
VALUES ('P789GHI', 'María García', 'AUTOMOVIL', '10:30', 7, 63.00);

INSERT INTO vehiculo (placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo)
VALUES ('M321JKL', 'José Morales', 'MOTOCICLETA', '11:00', 2, 12.00);

INSERT INTO vehiculo (placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo)
VALUES ('P654MNO', 'Lucía Hernández', 'AUTOMOVIL', '12:45', 5, 50.00);

-- Consulta general con columnas explícitas.
SELECT id, placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo, activo
FROM vehiculo;

-- Consulta filtrada por tipo.
SELECT id, placa, propietario, horas_utilizadas, costo
FROM vehiculo
WHERE tipo = 'AUTOMOVIL';

-- Vehículos cuyo costo es superior a Q40.00.
SELECT id, placa, propietario, tipo, costo
FROM vehiculo
WHERE costo > 40.00;

-- Ordenar del costo mayor al menor.
SELECT id, placa, propietario, tipo, costo
FROM vehiculo
ORDER BY costo DESC;

-- Actualizar un registro.
UPDATE vehiculo
SET propietario = 'Ana Lucía López'
WHERE placa = 'P123ABC';

-- Cambiar el estado de un vehículo.
UPDATE vehiculo
SET activo = FALSE
WHERE placa = 'M456DEF';

-- Eliminar un registro.
DELETE FROM vehiculo
WHERE placa = 'M321JKL';

-- PRUEBAS DE RESTRICCIONES
-- Ejecute cada prueba por separado para obtener la captura del error.

-- Prueba 1: PostgreSQL debe impedir la placa duplicada.
-- INSERT INTO vehiculo (placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo)
-- VALUES ('P123ABC', 'Propietario repetido', 'AUTOMOVIL', '14:00', 2, 20.00);

-- Prueba 2: PostgreSQL debe impedir horas iguales o menores que cero.
-- INSERT INTO vehiculo (placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo)
-- VALUES ('P000XYZ', 'Prueba Check', 'AUTOMOVIL', '15:00', 0, 0.00);
