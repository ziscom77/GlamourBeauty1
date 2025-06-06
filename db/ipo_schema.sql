-- Glamour Beauty – Módulo IPO (Ej. 3)
CREATE DATABASE IF NOT EXISTS GlamourBeautyIPO
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE GlamourBeautyIPO;

CREATE TABLE IF NOT EXISTS Proveedores (
                                           id        INT AUTO_INCREMENT PRIMARY KEY,
                                           nombre    VARCHAR(100) NOT NULL,
    contacto  VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS Inversionistas (
                                              id      INT AUTO_INCREMENT PRIMARY KEY,
                                              nombre  VARCHAR(100) NOT NULL,
    capital DOUBLE
    );

CREATE TABLE IF NOT EXISTS DocumentosIPO (
                                             id               INT AUTO_INCREMENT PRIMARY KEY,
                                             tipo             VARCHAR(50) NOT NULL,
    descripcion      VARCHAR(255),
    proveedor_id     INT,
    inversionista_id INT,
    FOREIGN KEY (proveedor_id)     REFERENCES Proveedores(id)
    ON DELETE SET NULL,
    FOREIGN KEY (inversionista_id) REFERENCES Inversionistas(id)
    ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS HitosIPO (
                                        id           INT AUTO_INCREMENT PRIMARY KEY,
                                        nombre       VARCHAR(100) NOT NULL,
    descripcion  VARCHAR(255),
    estado       VARCHAR(30),
    fecha        DATE,
    documento_id INT NOT NULL,
    FOREIGN KEY (documento_id) REFERENCES DocumentosIPO(id)
    ON DELETE CASCADE
    );
