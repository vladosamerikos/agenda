package com.mycompany.agendavlad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormularioController {


    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private TextField direccionTextField;
    
    private final AgendaDAO agendaDAO;

    public FormularioController() {
        agendaDAO = new AgendaDAO();
    }
    
    @FXML
    private void crearContacto(ActionEvent event) {
        String nombre = nombreTextField.getText();
        String apellidos = apellidosTextField.getText();
        String telefono = telefonoTextField.getText();
        String direccion = direccionTextField.getText();

        Contacto contacto = new Contacto(nombre, apellidos, telefono, direccion);

        // Llamar al método de AgendaController para guardar el contacto
        agendaDAO.agregarContacto(contacto);

        // Cerrar la ventana del formulario
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana del formulario sin guardar cambios
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
