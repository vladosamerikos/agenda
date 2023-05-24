package com.mycompany.agendavlad;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CrearContactoController {


    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private TextField direccionTextField;
    
    private final AgendaDB agendaDB;

    public CrearContactoController() {
        agendaDB = new AgendaDB();
    }
    
    @FXML
    private void crearContacto(ActionEvent event) {
        String nombre = nombreTextField.getText();
        String apellidos = apellidosTextField.getText();
        String telefono = telefonoTextField.getText();
        String direccion = direccionTextField.getText();

        if (nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("¡Todos los campos son obligatorios!");

            alert.showAndWait();
            return;
        }
        
        Contacto contacto = new Contacto(nombre, apellidos, telefono, direccion);
        if (agendaDB.comprobarDuplicado(apellidos)){
            agendaDB.agregarContacto(contacto);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("¡El contacto con este apellido ya existe!");

            apellidosTextField.setStyle("-fx-text-fill: red;");

            alert.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
