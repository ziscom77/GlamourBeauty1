-- Glamour Beauty – Módulo Finanzas (Ej. 1)
CREATE DATABASE IF NOT EXISTS GlamourBeautyFinanzas
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE GlamourBeautyFinanzas;

CREATE TABLE IF NOT EXISTS Cotizaciones (
                                            mercado VARCHAR(20) PRIMARY KEY,
    precio  DOUBLE
    );
