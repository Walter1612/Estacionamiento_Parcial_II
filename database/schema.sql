CREATE TABLE registro_estacionamiento (
                                          id SERIAL PRIMARY KEY,
                                          placa VARCHAR(10) NOT NULL,
                                          marca VARCHAR(40) NOT NULL,
                                          color VARCHAR(30) NOT NULL,
                                          tipo VARCHAR(15) NOT NULL CHECK (
                                              tipo IN ('MOTOCICLETA', 'AUTOMOVIL', 'PESADO')
                                              ),
                                          fecha_entrada TIMESTAMP NOT NULL,
                                          fecha_salida TIMESTAMP,
                                          horas_cobradas INTEGER,
                                          total DECIMAL(10,2) CHECK (
                                              total IS NULL OR total >= 0
                                              )
);