-- Glamour Beauty – Módulo Formación (Ej. 2)
CREATE DATABASE IF NOT EXISTS GlamourBeautyFormacion
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE GlamourBeautyFormacion;

CREATE TABLE IF NOT EXISTS Asignaturas (
                                           id       INT AUTO_INCREMENT PRIMARY KEY,
                                           nombre   VARCHAR(100) NOT NULL,
    creditos INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS Cursos (
                                      id            INT AUTO_INCREMENT PRIMARY KEY,
                                      nombre        VARCHAR(100) NOT NULL,
    asignatura_id INT NOT NULL,
    periodo       VARCHAR(20),
    FOREIGN KEY (asignatura_id) REFERENCES Asignaturas(id)
    );

CREATE TABLE IF NOT EXISTS Empleados (
                                         id     INT AUTO_INCREMENT PRIMARY KEY,
                                         nombre VARCHAR(100) NOT NULL,
    depto  VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS Matriculas (
                                          empleado_id INT,
                                          curso_id    INT,
                                          PRIMARY KEY (empleado_id, curso_id),
    FOREIGN KEY (empleado_id) REFERENCES Empleados(id)
    ON DELETE CASCADE,
    FOREIGN KEY (curso_id)    REFERENCES Cursos(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Notas (
                                     empleado_id INT,
                                     curso_id    INT,
                                     valor       DOUBLE,
                                     PRIMARY KEY (empleado_id, curso_id),
    FOREIGN KEY (empleado_id, curso_id) REFERENCES Matriculas(empleado_id, curso_id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Certificados (
                                            id          INT AUTO_INCREMENT PRIMARY KEY,
                                            empleado_id INT,
                                            descripcion VARCHAR(255),
    fecha       DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (empleado_id) REFERENCES Empleados(id)
    ON DELETE CASCADE
    );
