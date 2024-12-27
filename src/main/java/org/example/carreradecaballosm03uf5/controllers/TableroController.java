package org.example.carreradecaballosm03uf5.controllers;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD;
import org.example.carreradecaballosm03uf5.bbdd.ConexionDB;
import org.example.carreradecaballosm03uf5.model.Juego;
import org.example.carreradecaballosm03uf5.Utils.MovimientosCaballo;
import org.example.carreradecaballosm03uf5.model.Card;
import org.example.carreradecaballosm03uf5.model.CardSuit;
import org.example.carreradecaballosm03uf5.model.Jugador;
import org.example.carreradecaballosm03uf5.model.Tablero;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.example.carreradecaballosm03uf5.bbdd.CarreraDeCaballosBBDD.nuevoIdPartida;

public class TableroController {


    private static final int LINEAS = 4;  // Número de lineas que representan caballos
    private static final int TAMANO_CELULA = 80; // Tamanño de los cuadrados de la linea de partida

    private static final int TAMANO_CUADRADO = 20; // Tamaño de los cuadrados rojos y blancos

    private HBox parentHBox; // Guardando referencia al container padre

    private Tablero Tablero; // Tablero del juego
    private Juego nuevoJuego;  // Nuevo juego emepzado
    private String nombreArchivo= null; // es el nombre del archivo json para que solo se inicie una vez
    private String rutaCarpeta = "Historico_cartas"; // Especifica aquí la ruta a tu carpeta

    @FXML
    private GridPane gridPane; // Referência al Tablero
    private Text mensajeTexto; // Mensaje sobre la carta sacada
    private Jugador[] jugadores; // Jugadores

    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    /*public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }*/

    @FXML
    public void initialize() {
        Tablero = new Tablero();
        // Posiciones de los caballos
        MovimientosCaballo movimientosCaballo = new MovimientosCaballo(Tablero);
        nuevoJuego = new Juego(movimientosCaballo);
    }

    //Iniciar la estructura del tablero dinamicamente

    public void iniciarTablero(Stage primaryStage) {

        HBox layoutPrincipal = new HBox();

        layoutPrincipal.setPadding(new Insets(0));
        layoutPrincipal.setAlignment(Pos.CENTER);


        HBox columnaInicio = crearColumnaLateral("Start", true);
        gridPane = crearTablero();
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        CardSuit[] palos = CardSuit.values();
        for (int i = 0; i < palos.length; i++) {
            CardSuit suit = palos[i];
            int posicion = Tablero.obtenerPosicion(suit);
            String caminoImagen = "/images/KNIGHT_" + suit + ".png";
            posicionarCaballo(gridPane, caminoImagen, i, posicion);
        }

        HBox columnaFinal = crearColumnaLateral("End", false);
        layoutPrincipal.getChildren().addAll(columnaInicio, gridPane, columnaFinal);


        Canvas bordaArriba = crearBordaAlternada(1015, 5);
        Canvas bordaAbajo = crearBordaAlternada(1015, 5);
        HBox botonesInferiores = crearBotonesInferiores();


        mensajeTexto = new Text("Presione el botón 'Jugar' para iniciar el juego.");
        mensajeTexto.setStyle("-fx-font-size: 14; -fx-fill: black; -fx-font-weight: bold;");
        HBox lineaTexto = new HBox(mensajeTexto);
        lineaTexto.setAlignment(Pos.CENTER);
        lineaTexto.setPadding(new Insets(0, 0, 30, 0));


        VBox containerBordas = new VBox();
        containerBordas.setSpacing(0);
        containerBordas.setAlignment(Pos.CENTER);
        containerBordas.getChildren().addAll(bordaArriba, layoutPrincipal, bordaAbajo, botonesInferiores, lineaTexto);


        ImageView backgroundImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/background.jpg")));
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setOpacity(0.5);


        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, containerBordas);


        Scene scene = new Scene(stackPane, primaryStage.getWidth(), primaryStage.getHeight());


        scene.getStylesheets().add(getClass().getResource("/org/example/carreradecaballosm03uf5/styles/tablero.css").toExternalForm());


        primaryStage.setTitle("Carrera de caballos");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    //Crear estructura de la pista
    private GridPane crearTablero() {
        GridPane gridPane = new GridPane();

        for (int linha = 0; linha < LINEAS; linha++) {
            for (int coluna = 0; coluna < Tablero.getLongitudDeLasPistas(); coluna++) {
                Rectangle celula = new Rectangle(TAMANO_CELULA, TAMANO_CELULA);
                celula.setFill(Color.BLACK);

                Line lineaPuntos = new Line(0, TAMANO_CELULA, TAMANO_CELULA, TAMANO_CELULA);
                lineaPuntos.setStroke(Color.WHITE);
                lineaPuntos.setStrokeWidth(3);
                lineaPuntos.getStrokeDashArray().addAll(10.0, 10.0);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(celula, lineaPuntos);
                StackPane.setAlignment(lineaPuntos, Pos.BOTTOM_CENTER);

                gridPane.add(stackPane, coluna, linha);
            }
        }

        return gridPane;
    }
    /*private HBox crearBotonesInferiores() {
        javafx.scene.control.Button salir = new javafx.scene.control.Button("Salir");
        javafx.scene.control.Button jugarRonda = new javafx.scene.control.Button("Jugar Ronda");
        javafx.scene.control.Button guardar = new javafx.scene.control.Button("Guardar y salir");


        jugarRonda.getStyleClass().add("button");
        salir.getStyleClass().add("button");
        guardar.getStyleClass().add("button");
        jugarRonda.setOnAction(e -> jugarRonda());
        salir.setOnAction(e -> System.exit(0));
        //guardar.setOnAction(e -> guardarRonda());


        HBox cajaBoton = new HBox(20);
        cajaBoton.setAlignment(Pos.CENTER);
        cajaBoton.setPadding(new Insets(20, 0, 20, 0));
        cajaBoton.getChildren().addAll(jugarRonda, salir, guardar);

        return cajaBoton;
    }*/

    /*private HBox crearBotonesInferiores() {
        javafx.scene.control.Button salir = new javafx.scene.control.Button("Salir");
        javafx.scene.control.Button jugarRonda = new javafx.scene.control.Button("Jugar Ronda");
        javafx.scene.control.Button guardar = new javafx.scene.control.Button("Guardar y salir");

        jugarRonda.getStyleClass().add("button");
        salir.getStyleClass().add("button");
        guardar.getStyleClass().add("button");

        // Acción de jugar ronda
        jugarRonda.setOnAction(e -> jugarRonda());

        // Acción de salir
        salir.setOnAction(e -> System.exit(0));

        // Acción de guardar posición y salir
        guardar.setOnAction(e -> {
            // Obtener las posiciones de los jugadores en el tablero antes de salir
            // Estos valores deben ser calculados de acuerdo a la lógica del tablero
            int[] idsJugadores = {1, 2, 3, 4};
            int[][] posiciones = {
                    {0, 1}, // Jugador 1
                    {0, 2}, // Jugador 2
                    {0, 3}, // Jugador 3
                    {0, 4}  // Jugador 4
            };
            CardSuit[] palos = {CardSuit.SWORDS, CardSuit.CLUBS, CardSuit.CUPS, CardSuit.GOLD};

// Guardar posiciones y palos
            guardarPosicionAntesDeSalir(idsJugadores, posiciones, palos);


            // Cerrar la aplicación después de guardar
            System.exit(0);
        });

        // Crear el contenedor de botones
        HBox cajaBoton = new HBox(20);
        cajaBoton.setAlignment(Pos.CENTER);
        cajaBoton.setPadding(new Insets(20, 0, 20, 0));
        cajaBoton.getChildren().addAll(jugarRonda, salir, guardar);

        return cajaBoton;
    }*/

    private HBox crearBotonesInferiores() {
        javafx.scene.control.Button salir = new javafx.scene.control.Button("Salir");
        javafx.scene.control.Button jugarRonda = new javafx.scene.control.Button("Jugar Ronda");
        javafx.scene.control.Button guardar = new javafx.scene.control.Button("Guardar y salir");

        jugarRonda.getStyleClass().add("button");
        salir.getStyleClass().add("button");
        guardar.getStyleClass().add("button");

        // Acción de jugar ronda
        jugarRonda.setOnAction(e -> jugarRonda());

        // Acción de salir
        salir.setOnAction(e -> System.exit(0));

        // Acción de guardar posición y salir
        guardar.setOnAction(e -> {
            // Crear instancia de la clase BBDD
            CarreraDeCaballosBBDD bbdd = new CarreraDeCaballosBBDD();

            // Obtener las posiciones de los jugadores en el tablero
            // Supongamos que estas posiciones se obtienen de un método o están definidas
            Map<String, Integer> posicionesCaballos = new HashMap<>();
            posicionesCaballos.put("Oros", 1);    // Primera línea
            posicionesCaballos.put("Espadas", 2); // Segunda línea
            posicionesCaballos.put("Copas", 3);   // Tercera línea
            posicionesCaballos.put("Bastos", 4);  // Cuarta línea

            // Guardar las posiciones en la base de datos para una partida específica
            int idPartida = obtenerIdPartidaActual(); // Método que retorna el ID de la partida actual
            bbdd.guardarPosicionesAntesDeSalir(idPartida, posicionesCaballos);

            // Cerrar la aplicación después de guardar
            System.exit(0);
        });

        // Crear el contenedor de botones
        HBox cajaBoton = new HBox(20);
        cajaBoton.setAlignment(Pos.CENTER);
        cajaBoton.setPadding(new Insets(20, 0, 20, 0));
        cajaBoton.getChildren().addAll(jugarRonda, salir, guardar);

        return cajaBoton;
    }

    // Método ficticio para obtener el ID de la partida actual
    private int obtenerIdPartidaActual() {
        // Reemplaza con la lógica para obtener el ID de la partida que está en curso
        return 34; // Ejemplo: Partida con ID 34
    }




    private Canvas crearBordaAlternada(double ancho, double alto) {
        Canvas canvas = new Canvas(ancho, alto);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double anchoRaya = 20;
        for (int i = 0; i < ancho / anchoRaya; i++) {
            gc.setFill(i % 2 == 0 ? Color.RED : Color.WHITE);
            gc.fillRect(i * anchoRaya, 0, anchoRaya, alto);
        }
        return canvas;
    }
    private HBox crearColumnaLateral(String label,boolean isStart) {
        Text textoColumna = new Text(label);
        textoColumna.setStyle("-fx-font-size: 25; -fx-fill: white; -fx-font-weight: bold;");
        textoColumna.setRotate(90);

        StackPane fondoColumna = new StackPane();
        fondoColumna.setStyle("-fx-background-color: black;");
        fondoColumna.setMinWidth(50);
        fondoColumna.setPrefWidth(50);


        StackPane.setAlignment(textoColumna, Pos.CENTER);
        fondoColumna.getChildren().add(textoColumna);


        GridPane lateralGrid = new GridPane();


        int altoTablero = LINEAS * TAMANO_CELULA;
        int numeroLineas = altoTablero / TAMANO_CUADRADO;

        for (int linha = 0; linha < numeroLineas; linha++) {
            for (int coluna = 0; coluna < 2; coluna++) {
                Rectangle celula = new Rectangle(TAMANO_CUADRADO, TAMANO_CUADRADO);
                celula.setFill((linha + coluna) % 2 == 0 ? Color.BLACK : Color.WHITE);
                lateralGrid.add(celula, coluna, linha);
            }
        }


        HBox hBox = new HBox();

        if(isStart){
            hBox.getChildren().addAll(fondoColumna, lateralGrid);
        }else{
            hBox.getChildren().addAll(lateralGrid,fondoColumna);
        }


        return hBox;
    }

    //Jugar
    private void jugarRonda() {

        if (nuevoJuego.getGanador() == null) {

            int ronda = (nuevoJuego.getRonda() );

            Card cartaSacada = nuevoJuego.jugarRonda();
            if (cartaSacada != null ) {

                Text nuevoMensajeTexto = new Text("Ronda " + ronda + ": El crupier ha sacado " + cartaSacada.getDescription());
                nuevoMensajeTexto.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: black;");

                if (nombreArchivo == null) {

                    File carpeta = new File(rutaCarpeta); if (!carpeta.exists()) { carpeta.mkdirs(); }

                    LocalDateTime ahora = LocalDateTime.now();
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

                    nombreArchivo = rutaCarpeta + "/cartas_sacadas_" + ahora.format(formato) + ".json";
                }

                try (FileWriter fw = new FileWriter(nombreArchivo, true);  // true indica que se agrega al final del archivo
                     PrintWriter pw = new PrintWriter(fw)) {
                    //pw.println("{Descripcion Carta: " +  cartaSacada.getDescription() + ", Numero de carta: " + cartaSacada.getValue()+ ", Palo de la carta: " + cartaSacada.getSuit() + "}");  // Escribe el mensaje en el archivo
                    pw.println("{\"Descripcion Carta\": \"" + cartaSacada.getDescription() + "\", \"Numero de carta\": " + cartaSacada.getValue() + ", \"Palo de la carta\": \"" + cartaSacada.getSuit() + "\"},");
                } catch (IOException e) {
                    e.printStackTrace();  // Imprime cualquier error que ocurra al escribir el archivo
                }

                String[] carta = cartaSacada.getDescription().split("de");
                String palo = carta[1];
                String valor = carta[0];
                System.out.println(ronda + palo + valor);

                CarreraDeCaballosBBDD.guardarRonda(ronda, valor, palo);


                // Obtener la ruta de la imagen de la carta
                String caminoImagen = cartaSacada.getImagePath();
                InputStream imagemStream = getClass().getResourceAsStream(caminoImagen);

                if (imagemStream != null) {

                    Image imagemCarta = new Image(imagemStream);
                    ImageView cartaView = new ImageView(imagemCarta);
                    cartaView.setFitHeight(120);
                    cartaView.setPreserveRatio(true);


                    HBox mensajeConImagen = new HBox(15);
                    mensajeConImagen.setAlignment(Pos.CENTER_LEFT);
                    mensajeConImagen.getChildren().addAll(cartaView, nuevoMensajeTexto);

                    String textoCaballo;
                    if ((nuevoJuego.getRonda() - 1) % 5 == 0) {
                        textoCaballo = "El caballo de " + cartaSacada.getSuit().getDescription() + " retrocede una posición";
                    } else {
                        textoCaballo = "El caballo de " + cartaSacada.getSuit().getDescription() + " avanza una posición";
                    }
                    Label labelCaballo = new Label(textoCaballo);
                    labelCaballo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
                    labelCaballo.setAlignment(Pos.CENTER_RIGHT);

                    HBox layoutFinal = new HBox(30);
                    layoutFinal.setAlignment(Pos.CENTER);
                    layoutFinal.getChildren().addAll(mensajeConImagen, labelCaballo);

                    if (parentHBox == null) {
                        parentHBox = (HBox) mensajeTexto.getParent();
                    }

                    if (parentHBox != null) {
                        parentHBox.getChildren().clear();
                        parentHBox.getChildren().add(layoutFinal);
                    }
                }
            }
            actualizarTablero();
        }else{
//            System.out.println("ya hay un ganador, llamar nueva pantalla aqui");
        }
    }


    // Método para actualizar el tablero y la posición de los caballos
    private void actualizarTablero() {
        // Eliminar elementos del gridPane que son de tipo StackPane con un hijo ImageView (para actualizar la visualización)
        gridPane.getChildren().removeIf(node -> node instanceof StackPane && ((StackPane) node).getChildren().get(0) instanceof ImageView);

        // Obtener todos los palos disponibles en el juego
        CardSuit[] palos = CardSuit.values();

        // Iterar sobre cada palo (caballo) para actualizar su posición
        for (int i = 0; i < palos.length; i++) {
            CardSuit suit = palos[i]; // Palo actual
            int posicion = Tablero.obtenerPosicion(suit); // Obtener la posición del caballo en el tablero

            // Construir la ruta de la imagen correspondiente al caballo
            String caminoImagen = "/images/KNIGHT_" + suit + ".png";

            // Posicionar el caballo en la celda correspondiente
            posicionarCaballo(gridPane, caminoImagen, i, posicion);

            // Verificar si un caballo ha alcanzado la meta
            if (posicion >= Tablero.getLongitudDeLasPistas()) {
                // Definir al ganador del juego y actualizar la información del bote final
                nuevoJuego.definirGanador(jugadores, suit);

                // Cambiar a la pantalla de resultados, mostrando el ganador y el bote final
                cambiarALaPantallaGanador(nuevoJuego.getGanador(), nuevoJuego.getBoteFinal());

                // Salir del ciclo, ya que se ha encontrado un ganador
                break;
            }
        }
    }

    // Método para posicionar un caballo en el tablero
    private void posicionarCaballo(GridPane gridPane, String caminoImagen, int linha, int coluna) {
        try {
            // Cargar la imagen del caballo desde el recurso especificado
            Image caballoImage = new Image(getClass().getResourceAsStream(caminoImagen));
            ImageView caballoView = new ImageView(caballoImage);

            // Ajustar el tamaño del ImageView para adaptarse a la celda
            caballoView.setFitWidth(TAMANO_CELULA - 10); // Ancho ajustado
            caballoView.setFitHeight(TAMANO_CELULA - 10); // Alto ajustado
            caballoView.setPreserveRatio(true); // Mantener la proporción de la imagen

            // Crear un StackPane para contener la imagen y permitir apilar elementos si es necesario
            StackPane celulaPane = new StackPane();
            celulaPane.getChildren().add(caballoView);

            // Añadir el StackPane al gridPane en la posición especificada
            gridPane.add(celulaPane, coluna, linha);
        } catch (Exception e) {
            // Manejar excepciones relacionadas con la carga de imágenes
          //  System.err.println("Error al cargar la imagen: " + caminoImagen);
            e.printStackTrace(); // Mostrar la traza del error para depuración
        }
    }

/*
    private void guardarPosicionAntesDeSalir(int[] idsJugadores, int[][] posiciones, CardSuit[] palos) {
        // Preparamos la consulta SQL para actualizar las posiciones y los palos de los jugadores
        // Usamos `nuevoIdPartida` para generar el nombre de la tabla correspondiente
        String sql = "UPDATE jugadores" + nuevoIdPartida + " SET posicion = ?, palo = ? WHERE idJugador = ?";

        try (
                // Obtenemos la conexión a la base de datos utilizando el método getConnection() de CarreraDeCaballosBBDD
                Connection conn = CarreraDeCaballosBBDD.getConnection()
        ) {
            // Verificamos si la conexión es nula o está cerrada, en cuyo caso lanzamos una excepción
            if (conn == null || conn.isClosed()) {
                throw new SQLException("La conexión a la base de datos no está disponible.");
            }

            // Creamos un PreparedStatement para ejecutar la consulta SQL de actualización
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Iteramos sobre los jugadores para actualizar su posición y palo
                for (int i = 0; i < idsJugadores.length; i++) {
                    // Obtenemos los valores necesarios para la consulta
                    int idJugador = idsJugadores[i]; // ID del jugador
                    int fila = posiciones[i][0];     // Fila de la posición
                    int columna = posiciones[i][1];  // Columna de la posición
                    String posicion = fila + "," + columna; // Convertimos la posición a una cadena "fila,columna"
                    String palo = palos[i].name();          // Obtenemos el nombre del palo (por ejemplo, "SWORDS")

                    // Asignamos los parámetros a la consulta SQL
                    stmt.setString(1, posicion); // Asignamos la posición
                    stmt.setString(2, palo);     // Asignamos el palo
                    stmt.setInt(3, idJugador);   // Asignamos el ID del jugador

                    // Ejecutamos la actualización en la base de datos
                    stmt.executeUpdate();

                    // Imprimimos un mensaje de éxito para cada jugador
                    System.out.println("Posición y palo guardados correctamente para el jugador ID: " + idJugador);
                }
            }
        } catch (SQLException e) {
            // Si ocurre una excepción durante el proceso, la capturamos y mostramos un mensaje de error
            System.out.println("Error al guardar las posiciones y palos de los caballos: " + e.getMessage());
        }
    }*/







    //Cambiar a la pantalla del ganador
    private void cambiarALaPantallaGanador(Jugador ganador, Integer bote) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/carreradecaballosm03uf5/views/resultado.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            ResultadoController resultadoController = fxmlLoader.getController();

            resultadoController.mostrarGanador(ganador, bote);

            Stage stage = (Stage) gridPane.getScene().getWindow();

            stage.setScene(scene);
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.setTitle("Carrera de Caballos - Resultado");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


