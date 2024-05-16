package com.example.ajustes;

import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileInputStream;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Perfil implements Initializable {

    private String nombreUsuario = "Pablo";
    @javafx.fxml.FXML
    private Text inf;
    @javafx.fxml.FXML
    private Text actualizarImagen;
    @javafx.fxml.FXML
    private ImageView fotoPerfilImageView;
    @javafx.fxml.FXML
    private Text modinf;
    @javafx.fxml.FXML
    private Text cambContr;
    @javafx.fxml.FXML
    private Text elimCuenta;
    private Button eliminarCuentaButton;
    @javafx.fxml.FXML
    private Label bienvenidaLabel;
    @javafx.fxml.FXML
    private Text gruposPertenezco;
    @javafx.fxml.FXML
    private Text cerrarSesion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bienvenidaLabel.setText(nombreUsuario);
    }

    @javafx.fxml.FXML
    private void mostrarInformacion(MouseEvent event) {
        // Crear un VBox para contener la información detallada
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Label("Nombre completo: "),
                new Label("Correo electrónico: "));

        // Crear un Alert con un mensaje personalizado
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información detallada");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(vBox);
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void actualizarImagenClick() {
        // Configurar el diálogo de selección de archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de perfil");

        // Filtrar para mostrar solo archivos de imagen
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el cuadro de diálogo de selección de archivo
        File imagenSeleccionada = fileChooser.showOpenDialog(null);

        // Verificar si el usuario seleccionó una imagen
        if (imagenSeleccionada != null) {
            try {
                // Convertir la imagen seleccionada a un array de bytes
                FileInputStream fis = new FileInputStream(imagenSeleccionada);
                byte[] imagenBytes = new byte[(int) imagenSeleccionada.length()];
                fis.read(imagenBytes);
                fis.close();

                // Guardar la imagen en la base de datos
                guardarImagenEnBD(imagenBytes);

                // Cargar la imagen seleccionada en el ImageView del perfil
                Image imagenPerfil = new Image(new ByteArrayInputStream(imagenBytes));
                fotoPerfilImageView.setImage(imagenPerfil);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void guardarImagenEnBD(byte[] imagenBytes) throws SQLException {
        // Establecer conexión con la base de datos
        Connection conn = DBUtil.getConnection();

        // Crear la consulta SQL para actualizar la imagen en la base de datos
        String sql = "UPDATE usuarios SET imagen_Perfil = ? ";

        // Crear una sentencia preparada con la consulta SQL
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecer los parámetros de la sentencia preparada
            stmt.setBytes(1, imagenBytes);


            // Ejecutar la consulta SQL
            stmt.executeUpdate();
        } finally {
            // Cerrar la conexión con la base de datos
            DBUtil.closeConnection(conn);
        }
    }

    @javafx.fxml.FXML
    public void modificarInformacion(Event event) {

    }

    @javafx.fxml.FXML
    public void cambiarContrasena(Event event) {

    }

    @javafx.fxml.FXML
    private void eliminarCuenta() {
        // Crear un VBox para contener el texto y el checkbox
        VBox vbox = new VBox();
        CheckBox confirmarCheckBox = new CheckBox("Confirmar eliminación de la cuenta");
        vbox.getChildren().addAll(confirmarCheckBox);

        // Crear la alerta
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Cuenta");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar la cuenta?");
        alert.getDialogPane().setContent(vbox);

        // Mostrar la alerta y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();

        // Verificar si el usuario hizo clic en Aceptar y marcó el checkbox
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (confirmarCheckBox.isSelected()) {
                    // Eliminar la cuenta si el usuario confirma y marca el checkbox
                    eliminarCuentaDeBD();
                } else {
                    // Mostrar una alerta de error si el usuario no marca el checkbox
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("No has confirmado la eliminación de tu cuenta.");
                    errorAlert.showAndWait();
                }
            }
        });
    }
    private void eliminarCuentaDeBD() {
        // Establecer conexión con la base de datos
        try (Connection conn = DBUtil.getConnection()) {
            // Crear la consulta SQL para eliminar la cuenta del usuario
            String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

            // Crear una sentencia preparada con la consulta SQL
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Establecer el parámetro del ID del usuario (debes obtener este valor de tu sesión)
                int tuIdDeUsuario = 1;
                stmt.setInt(1, tuIdDeUsuario); // Reemplaza tuIdDeUsuario con el ID del usuario activo

                // Ejecutar la consulta SQL
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar errores de conexión o consulta SQL
        }
    }

    private void cerrarSesion() {
        // Código específico para cerrar sesión en tu aplicación (por ejemplo, limpiar la información de la sesión)
    }

    @javafx.fxml.FXML
    public void mostrarGruposPertenece(Event event) {
    }

    @javafx.fxml.FXML
    public void cerrarSesión(Event event) {
    }
}
