# Examen Parcial II Estacionamiento

Aplicación de consola en Java para registrar automóviles y motocicletas, calcular el costo de permanencia y demostrar los conceptos solicitados durante el segundo parcial.

## Contenido

- `src/`: código Java.
- `database/estacionamiento.sql`: creación y pruebas de PostgreSQL.
- `evidencias/`: capturas solicitadas para el documento final.
- `.gitignore`: exclusión de archivos generados.

## Conceptos aplicados

- Clase abstracta `Vehiculo` con atributos privados.
- Herencia mediante `Automovil` y `Motocicleta`.
- Sobrescritura de `calcularCosto()` y `getTipo()`.
- Polimorfismo mediante referencias `Vehiculo`.
- `ArrayList<Vehiculo>` para almacenar los vehículos.
- `HashSet<String>` para impedir placas duplicadas.
- `HashMap<String, Double>` para calcular totales por tipo.
- Validaciones, `try`, `catch` y `finally` observable.
- PostgreSQL y operaciones SQL independientes del programa Java.

## Tarifas

- Automóvil: Q10.00 por hora.
- Motocicleta: Q6.00 por hora.
- Más de 5 horas: descuento del 10 % sobre el costo total.

## Abrir y ejecutar en IntelliJ IDEA

1. Abra la carpeta completa del proyecto.
2. Configure un JDK.
3. Marque la carpeta `src` como `Sources Root` si IntelliJ no la reconoce automáticamente.
4. Abra `src/Main.java`.
5. Ejecute el método `main`.

## Compilar desde una terminal

En Windows, desde la carpeta principal:

```text
javac -d out src\*.java
java -cp out Main
```

## PostgreSQL

1. Abra pgAdmin.
2. Ejecute `CREATE DATABASE parcial2_estacionamiento;` conectado a `postgres`.
3. Conéctese a `parcial2_estacionamiento`.
4. Ejecute el resto de `database/estacionamiento.sql`.
5. Para evidenciar las restricciones, quite los comentarios de una prueba a la vez y ejecútela.

## Autor

Walter Isai Villagran Garcia  
Programación II Sección C
