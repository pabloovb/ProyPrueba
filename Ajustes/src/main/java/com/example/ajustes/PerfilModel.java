package com.example.ajustes;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class PerfilModel {
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
            // Cargar la imagen seleccionada en el ImageView del perfil
            Image imagenPerfil = new Image(imagenSeleccionada.toURI().toString());

        }
    }

    @javafx.fxml.FXML
    public void modificarInformacion(Event event) {

    }
}
