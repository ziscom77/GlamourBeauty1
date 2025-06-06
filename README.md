# Glamour Beauty - Módulo Formación (Ejercicio 2)

Sistema académico para capacitar a empleados de cara a la IPO.

## Requisitos

| Herramienta | Versión |
|-------------|---------|
| JDK         | 17+     |
| MySQL       | 8.x     |

## Instalación

1. Cambia a la rama:

   ```bash
   git checkout feature/ej2-formacion
2.Ejecuta el script:
mysql -u root -p < db/formacion_schema.sql
Crea el esquema GlamourBeautyFormacion y 6 tablas (Asignaturas, Cursos…).
3.Configura DatabaseConnection.java:
private static final String URL =
    "jdbc:mysql://localhost:3306/GlamourBeautyFormacion";
4.Ejecuta la GUI:
# Desde IDE
Run ▶ ui.SwingFormacion
src/
  model/    Asignatura.java, Curso.java, Empleado.java, ...
  dao/      AsignaturaDao.java, CursoDao.java, ...
  service/  GestorFormacion.java
  ui/       SwingFormacion.java  ← main()
