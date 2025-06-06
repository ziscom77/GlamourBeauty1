# Glamour Beauty – Gestión IPO (Ejercicio 3)

Aplicación para administrar proveedores, inversionistas, documentos y hitos del proceso de salida a bolsa de **Glamour Beauty**.

---

## 📋 Requisitos

| Herramienta | Versión recomendada |
|-------------|---------------------|
| **JDK**     | 17 o superior       |
| **MySQL**   | 8.x (o MariaDB 10+) |
| **Driver JDBC** | `mysql-connector-j 8.x` (Maven / Gradle o añadida como .jar) |

---

## ⚙️ Instalación

1. **Cambiar de rama**

   ```bash
   git checkout feature/ej3-ipo
2. Crear la base de datos
mysql -u root -p < db/ipo_schema.sql
Se crea el esquema GlamourBeautyIPO con las tablas
Proveedores, Inversionistas, DocumentosIPO, HitosIPO.
3. Configurar la conexión
Edita src/dao/DatabaseConnection.java:
private static final String URL  =
    "jdbc:mysql://localhost:3306/GlamourBeautyIPO";
private static final String USER = "root";
private static final String PASS = "TU_PASSWORD";
4. Compilar y ejecutar
# IntelliJ: Run ▶ ui.SwingIPO
# Maven:
mvn clean compile exec:java -Dexec.mainClass="ui.SwingIPO"
# Gradle:
gradle run --args="ui.SwingIPO"
src/
  model/
    Proveedor.java
    Inversionista.java
    DocumentoIPO.java
    HitoIPO.java
  dao/
    DatabaseConnection.java
    ProveedorDao.java
    InversionistaDao.java
    DocumentoDao.java
    HitoDao.java
  service/
    GestorIPO.java
  ui/
    SwingIPO.java   ← contiene el método main()
