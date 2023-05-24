package com.mycompany.agendavlad;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrincipalController {
    @FXML
    private TextField buscarTextField;
    @FXML
    private TableView<Contacto> tablaContactos;
    @FXML
    private TableColumn<Contacto, String> nombreColumn;
    @FXML
    private TableColumn<Contacto, String> apellidosColumn;
    @FXML
    private TableColumn<Contacto, String> telefonoColumn;
    @FXML
    private TableColumn<Contacto, String> direccionColumn;

    private final AgendaDB agendaDB; 

    public PrincipalController() {
        agendaDB = new AgendaDB(); 
    }

    @FXML
    public void initialize() {
        
        buscarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarContacto(newValue);
        });
        
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        apellidosColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidosProperty());
        telefonoColumn.setCellValueFactory(cellData -> cellData.getValue().getTelefonoProperty());
        direccionColumn.setCellValueFactory(cellData -> cellData.getValue().getDireccionProperty());

        tablaContactos.setItems(agendaDB.obtenerContactos());
        
        tablaContactos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();
                if (contactoSeleccionado != null) {
                    abrirVentanaEdicion(contactoSeleccionado);
                }
            }
        });
    }

    public void buscarContacto(String nombre) {
        ObservableList<Contacto> resultados;

        try {
            resultados = agendaDB.buscarContactosPorNombre(nombre);
            
            tablaContactos.setItems(resultados);
        } catch (SQLException e) {
        }
    }
    
    @FXML
    public void mostrarFormularioAgregar(ActionEvent event) throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("crearContacto.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Contacto");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            tablaContactos.setItems(agendaDB.obtenerContactos());
        } catch (IOException e) {
        }
    }
    
    private void abrirVentanaEdicion(Contacto contacto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editarContacto.fxml"));
            Parent root = loader.load();

            EditarContactoController controller = loader.getController();
            controller.setContacto(contacto);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Contacto");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            tablaContactos.setItems(agendaDB.obtenerContactos());
        } catch (IOException e) {
        }
    }
    
}
