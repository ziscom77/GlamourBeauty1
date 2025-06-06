# Glamour Beauty - Gestión IPO (Ejercicio 3)

Gestión de documentos, proveedores, inversionistas e hitos para el proceso de salida a bolsa.

## Requisitos

* JDK 17+  
* MySQL 8.x

## Instalación

```bash
git checkout feature/ej3-ipo
mysql -u root -p < db/ipo_schema.sql
El script crea el esquema GlamourBeautyIPO con las tablas:

Proveedores

Inversionistas

DocumentosIPO

HitosIPO

Edita DatabaseConnection.java:
private static final String URL =
    "jdbc:mysql://localhost:3306/GlamourBeautyIPO";
Run ▶ ui.SwingIPO   # GUI con pestañas Proveedores, Inversionistas, Documentos, Hitos
src/
  model/    Proveedor.java, Inversionista.java, DocumentoIPO.java, HitoIPO.java
  dao/      ProveedorDao.java, DocumentoDao.java, ...
  service/  GestorIPO.java
  ui/       SwingIPO.java  ← main()
