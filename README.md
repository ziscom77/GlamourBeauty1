# Glamour Beauty - Módulo Finanzas (Ejercicio 1)

Gestión de cotizaciones bursátiles e indicadores financieros de Glamour Beauty.

## 📋 Requisitos

| Herramienta | Versión recomendada |
|-------------|---------------------|
| JDK         | 17+                 |
| MySQL       | 8.x (o MariaDB 10+) |
| Maven/Gradle| Opcional            |

## ⚙️ Instalación

1. **Clonar** el repositorio y cambiar a la rama:

   ```bash
   git checkout feature/ej1-finanzas
Base de datos
mysql -u root -p < db/finanzas_schema.sql
Esto crea el esquema GlamourBeautyFinanzas y la tabla Cotizaciones.

Configurar conexión
Edita src/dao/DatabaseConnection.java:
private static final String URL  =
    "jdbc:mysql://localhost:3306/GlamourBeautyFinanzas";
private static final String USER = "root";
private static final String PASS = "TU_PASSWORD";
Compilar y ejecutar
# Maven
mvn clean compile exec:java -Dexec.mainClass="ui.SwingFinanzas"

# o, desde IntelliJ:
Run ▶ ui.SwingFinanzas
Abre la ventana Finanzas.

Introduce Mercado (NYSE, BME…) y Precio → Guardar / Actualizar.

La tabla muestra los precios; debajo se recalculan:

Valor de mercado = precio medio × acciones (50M)

P / Valor en Libros = valor de mercado ÷ 200 M €
src/
  model/    Cotizacion.java
  dao/      CotizacionDao.java, DatabaseConnection.java
  financial/GlamourFinancial.java, GlamourFinancialImpl.java
  ui/       SwingFinanzas.java  ← main()
