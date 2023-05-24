package com.mycompany.agendavlad;

import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarContactoController {
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private TextField direccionTextField;
    
    private Contacto contacto;
    private final AgendaDB agendaDB;

    public EditarContactoController() {
        agendaDB = new AgendaDB();
    }
    
    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
        
        nombreTextField.setText(contacto.getNombre());
        apellidosTextField.setText(contacto.getApellidos());
        telefonoTextField.setText(contacto.getTelefono());
        direccionTextField.setText(contacto.getDireccion());
    }

    @FXML
    public void guardarCambios(ActionEvent event) {
        String nombre = nombreTextField.getText();
        String apellidos = apellidosTextField.getText();
        String telefono = telefonoTextField.getText();
        String direccion = direccionTextField.getText();

        if (nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son requeridos.");
            return;
        }

        contacto.setNombre(nombre);
        contacto.setApellidos(apellidos);
        contacto.setTelefono(telefono);
        contacto.setDireccion(direccion);

       
        agendaDB.actualizarContacto(contacto);
        mostrarAlerta("Éxito", "Contacto actualizado correctamente.");

        cerrarVentana();
    }

    @FXML
    public void eliminarContacto(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas eliminar este contacto?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            agendaDB.eliminarContacto(contacto);

            mostrarAlerta("Éxito", "Contacto eliminado correctamente.");

            cerrarVentana();
        }
    }

    
    
    @FXML
    public void cancelarEdicion(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) nombreTextField.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
