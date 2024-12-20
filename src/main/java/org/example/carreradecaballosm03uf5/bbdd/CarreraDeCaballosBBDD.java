/*package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CarreraDeCabballosBBDD {
}*/

/*
package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CarreraDeCaballosBBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    // Método para crear la base de datos si no existe
    public void crearBaseDeDatos() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS carreradecaballos";
            stmt.executeUpdate(sql);
            System.out.println("Base de datos creada o ya existe.");
        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    // Método para obtener la conexión (ahora con la base de datos específica)
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL + "carreradecaballos", USER, PASSWORD);
        }
        return connection;
    }

    // Método para ejecutar una consulta SQL
    public void ejecutarConsulta(String sql) {
        try (Connection conn = getConnection()) {
            conn.createStatement().executeUpdate(sql);
            System.out.println("Consulta ejecutada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    // Método para conectar a la base de datos y manejarlo en un hilo separado
    public void conectarYEjecutar() {
        // Crear un hilo para la conexión a la base de datos
        Thread dbThread = new Thread(() -> {
            try {
                // Intentamos realizar la conexión a la base de datos
                try (Connection conn = getConnection()) {
                    System.out.println("Conexión exitosa a la base de datos");
                    // Puedes realizar otras operaciones con la base de datos aquí
                } catch (SQLException e) {
                    System.out.println("Error al conectar a la base de datos: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        });

        dbThread.setDaemon(true);  // Esto asegura que el hilo se cierre cuando la aplicación termine
        dbThread.start();  // Iniciar el hilo
    }

    public static void main(String[] args) {
        CarreraDeCaballosBBDD bd = new CarreraDeCaballosBBDD();
        bd.crearBaseDeDatos(); // Crear la base de datos si no existe
        bd.conectarYEjecutar(); // Conectar a la base de datos y realizar la consulta
    }
}*/


/*
package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CarreraDeCaballosBBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "carreradecaballos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    // Método para crear la base de datos si no existe
    public void crearBaseDeDatos() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            stmt.executeUpdate(sql);
            System.out.println("Base de datos creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    // Método para establecer la conexión con la base de datos
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
        }
        return connection;
    }

    // Método para ejecutar una consulta SQL
    public void ejecutarConsulta(String sql) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Consulta ejecutada con éxito: " + sql);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    // Método para conectar y ejecutar lógica en un hilo separado
    public void conectarYEjecutar() {
        Thread dbThread = new Thread(() -> {
            try {
                Connection conn = getConnection();
                System.out.println("Conexión exitosa a la base de datos: " + DATABASE_NAME);
                // Aquí podrías ejecutar consultas adicionales si es necesario
            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        });

        dbThread.setDaemon(true); // Permite que el hilo se detenga cuando termine la aplicación
        dbThread.start();
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        CarreraDeCaballosBBDD bd = new CarreraDeCaballosBBDD();
        bd.crearBaseDeDatos(); // Crear la base de datos si no existe
        bd.conectarYEjecutar(); // Conectar a la base de datos y realizar operaciones
    }
}*/



/*
package org.example.carreradecaballosm03uf5.bbdd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CarreraDeCaballosBBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "carreradecaballos";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String SCRIPT_PATH = "src/main/resources/script.sql"; // Ruta del archivo SQL

    private Connection connection;

    // Método para establecer la conexión con la base de datos
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
        }
        return connection;
    }

    // Método para ejecutar un script SQL desde un archivo
    public void ejecutarScriptSQL() {
        StringBuilder script = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(SCRIPT_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                script.append(line).append("\n");
            }

            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(script.toString());
                System.out.println("Script SQL ejecutado con éxito.");
            } catch (SQLException e) {
                System.err.println("Error al ejecutar el script SQL: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo SQL: " + e.getMessage());
        }
    }

    // Método principal
    public static void main(String[] args) {
        CarreraDeCaballosBBDD bd = new CarreraDeCaballosBBDD();
        bd.ejecutarScriptSQL(); // Ejecutar el script SQL
    }
}*/package org.example.carreradecaballosm03uf5.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class CarreraDeCaballosBBDD {

    // Cargar el controlador JDBC de MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Asegúrate de usar el controlador correcto
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String URL = "jdbc:mysql://localhost:3306/carreradecaballos";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    // Método para obtener la conexión
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // Método para ejecutar un script SQL
    public void ejecutarScript(String script) {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            // Comprobar si el script es un SELECT, SHOW TABLES, o cualquier consulta que devuelva un conjunto de resultados
            if (script.trim().toUpperCase().startsWith("SELECT") || script.trim().toUpperCase().startsWith("SHOW")) {
                // Ejecutar consulta que devuelve resultados con executeQuery
                ResultSet rs = stmt.executeQuery(script);
                while (rs.next()) {
                    System.out.println(rs.getString(1));  // Imprimir los resultados
                }
            } else {
                // Ejecutar las consultas que no devuelven resultados con executeUpdate
                stmt.executeUpdate(script);
                System.out.println("Script ejecutado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar el script: " + e.getMessage());
        }
    }

    // Método para ejecutar un script desde un archivo
    public void ejecutarScriptDesdeArchivo(String path) {
        StringBuilder script = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Eliminar espacios extra
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;  // Saltar líneas vacías y comentarios
                }
                script.append(line).append("\n");
            }

            // Separar el script en líneas individuales y ejecutarlas
            String[] queries = script.toString().split(";");
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    System.out.println("Ejecutando consulta: " + query.trim());
                    ejecutarScript(query.trim());
                }
            }

            verificarTablas();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo del script: " + e.getMessage());
        }
    }

    // Método para verificar las tablas en la base de datos
    private void verificarTablas() {
        String query = "SHOW TABLES"; // Consulta para mostrar las tablas
        ejecutarScript(query);  // Usar ejecutarScript para obtener el listado de tablas
    }

    // Método para crear la base de datos (si no existe)
    public void crearBaseDeDatos() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS carreradecaballos";
            stmt.executeUpdate(sql);  // Usar executeUpdate para crear la base de datos
            System.out.println("Base de datos 'carreradecaballos' creada con éxito (si no existía).");
        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    // Método para crear las tablas en la base de datos
    public void crearTablas() {
        String createTablePartidas = "CREATE TABLE IF NOT EXISTS partidas (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "idPartida INT NOT NULL, " +
                "nombre VARCHAR(255) NOT NULL, " +
                "bote INT NOT NULL, " +
                "palo VARCHAR(50) NOT NULL, " +
                "posicion INT NOT NULL" +
                ");";

        String createTableRondas = "CREATE TABLE IF NOT EXISTS rondas (" +
                "idPartida INT NOT NULL, " +
                "numRonda INT NOT NULL, " +
                "numCarta INT NOT NULL, " +
                "paloCarta VARCHAR(50) NOT NULL, " +
                "PRIMARY KEY (idPartida, numRonda), " +
                "FOREIGN KEY (idPartida) REFERENCES partidas(idPartida)" +
                ");";

        // Ejecutar las sentencias para crear las tablas
        ejecutarScript(createTablePartidas);
        ejecutarScript(createTableRondas);
    }


    // Método para agregar la columna 'posicion' en la tabla 'partidas' si no existe
    private void agregarColumnaPosicion() {
        String alterTable = "ALTER TABLE partidas ADD COLUMN IF NOT EXISTS posicion INT NOT NULL";
        ejecutarScript(alterTable);  // Ejecutar el ALTER TABLE para agregar la columna
    }

    // Método para conectar y ejecutar la consulta en un hilo separado
    public void conectarYEjecutar() {
        // Crear un hilo para la conexión a la base de datos
        Thread dbThread = new Thread(() -> {
            try {
                // Intentamos realizar la conexión a la base de datos
                try (Connection conn = getConnection()) {
                    System.out.println("Conexión exitosa a la base de datos");
                    // Puedes realizar otras operaciones con la base de datos aquí
                } catch (SQLException e) {
                    System.out.println("Error al conectar a la base de datos: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        });

        dbThread.setDaemon(true);  // Esto asegura que el hilo se cierre cuando la aplicación termine
        dbThread.start();  // Iniciar el hilo
    }

    // Método principal para probar la conexión y ejecutar el script
    public static void main(String[] args) {
        CarreraDeCaballosBBDD bd = new CarreraDeCaballosBBDD();
        bd.crearBaseDeDatos();  // Crea la base de datos si no existe
        bd.crearTablas();  // Crear las tablas
        bd.ejecutarScriptDesdeArchivo("src/main/java/org/example/carreradecaballosm03uf5/scriptbbdd.sql"); // Ejecuta el script desde un archivo
        bd.conectarYEjecutar(); // Conectar a la base de datos en un hilo separado
    }
}
