# -_serie_de_tres_en_Tres_con_Java-_swing_y_oracle-._  

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/b93d2905-babd-45ef-967f-77786518f44c" /> 

📌 Serie de Tres en Tres con Java, Swing y Oracle :
Este proyecto es un ejemplo completo desarrollado en Java con IntelliJ IDEA, que combina :  

Swing para la interfaz gráfica .
JDBC para la conexión a Oracle .
Procedimiento almacenado en Oracle para registrar los números generados .

El programa genera una serie de números de 0 hasta 100 en pasos de 3 , los muestra en una interfaz gráfica con estilo (verde sobre fondo gris) , y al mismo tiempo los registra en una tabla de Oracle mediante un procedimiento almacenado .

📂 **Estructura del proyecto**

```
serie/
 ├── src/
 │   ├── main/
 │   │   ├── java/
 │   │   │   └── com/
 │   │   │       └── example/
 │   │   │           └── serie/
 │   │   │               ├── DBConnection.java   # Clase para conexión JDBC y llamada al procedimiento
 │   │   │               └── SerieGUI.java       # Interfaz gráfica con Swing
 │   │   └── resources/                          # (Opcional) Archivos de configuración, logs, etc.
 │   └── test/                                   # (Opcional) Pruebas unitarias
 │
 ├── pom.xml                                     # Archivo Maven con dependencias (Oracle JDBC)
 └── README.md                                   # Documentación del proyecto
 
```
## ⚙️ 1. Procedimiento almacenado en Oracle

Primero, crea la **tabla** y el **procedimiento almacenado** en Oracle:

```sql
CREATE TABLE serie_numeros (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    numero NUMBER NOT NULL
);

CREATE OR REPLACE PROCEDURE insertar_numero(p_numero IN NUMBER) AS
BEGIN
    INSERT INTO serie_numeros(numero) VALUES(p_numero);
END;
/
```

---

## 💻 2. Clase de conexión a Oracle y ejecución del procedimiento

**Archivo:** `DBConnection.java`

```java
package com.example.serie;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USER = "tu_usuario";
    private static final String PASSWORD = "tu_password";

    // Método para obtener la conexión a Oracle
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para ejecutar el procedimiento almacenado
    public static void insertarNumero(int numero) {
        String sql = "{ call insertar_numero(?) }";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, numero);
            stmt.execute();
            System.out.println("Número insertado en Oracle: " + numero);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```
## 🎨 3. Interfaz gráfica con Swing

**Archivo:** `SerieGUI.java`

```java
package com.example.serie;

import javax.swing.*;
import java.awt.*;

public class SerieGUI extends JFrame {

    private JTextArea textArea;

    public SerieGUI() {
        setTitle("Serie de Tres en Tres");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Área de texto con estilos
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.BOLD, 18));
        textArea.setForeground(new Color(0, 128, 0)); // Texto verde
        textArea.setBackground(Color.LIGHT_GRAY);     // Fondo gris
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para generar la serie
        JButton btnGenerar = new JButton("Generar Serie");
        btnGenerar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnGenerar.addActionListener(e -> generarSerie());
        add(btnGenerar, BorderLayout.SOUTH);
    }

    // Método que genera la serie de 0 a 100 en pasos de 3
    private void generarSerie() {
        textArea.setText(""); // Limpiar área de texto
        for (int i = 0; i <= 100; i += 3) {
            textArea.append(i + "\n");
            DBConnection.insertarNumero(i); // Guardar en Oracle
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SerieGUI gui = new SerieGUI();
            gui.setVisible(true);
        });
    }
}

```
## 📦 4. Dependencias (pom.xml)
Este proyecto utiliza **Maven** para la gestión de dependencias .
**Archivo:** `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>serie</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Driver JDBC de Oracle -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc8</artifactId>
            <version>21.9.0.0</version>
        </dependency>

        <!-- Swing ya está incluido en Java, no es necesario agregar dependencia -->
    </dependencies>
</project>

```
🚀 Ejecución del proyecto :
Clona el proyecto en IntelliJ IDEA .
Configura el archivo DBConnection.java con tu usuario y contraseña de Oracle .
Ejecuta en Oracle los scripts para crear la tabla y el procedimiento .

En IntelliJ, compila y ejecuta SerieGUI.java .
Presiona el botón "Generar Serie" :
Verás la serie de números en la interfaz .

Cada número se insertará en Oracle :.
✅ Resultado esperado :
La ventana muestra la serie 0, 3, 6, ..., 99 en un área de texto con fuente Arial, 18px, verde sobre gris .
Cada número aparece también registrado en la tabla serie_numeros en Oracle .
En consola se imprime la confirmación de cada inserción .

🛠️ Requisitos previos :
Java 17 o superior
IntelliJ IDEA (o cualquier IDE compatible con Maven) .
Oracle Database 19c o superior
Maven 3.8+

📚 Notas :
Swing ya está incluido en el JDK, no necesitas agregar dependencias extras .
El ojdbc8.jar debe estar en tu repositorio Maven (se descarga automáticamente) .
Si usas un SID o servicio distinto en Oracle, ajusta la cadena de conexión en DBConnection.java :
jdbc:oracle:thin:@localhost:1521:orcl

✍️ Autor: Giovanny Alejandro Tapiero C - Ejemplo de integración Java + Oracle con Swing y JDBC .
