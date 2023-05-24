package com.mycompany.agendavlad;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
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

public class AgendaController {
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

    private final AgendaDAO agendaDAO; // Clase para acceder a la base de datos

    public AgendaController() {
        agendaDAO = new AgendaDAO(); // Inicializar la clase de acceso a la BD
    }

    @FXML
    public void initialize() {
        
        buscarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarContacto(newValue);
        });
        // Configurar las celdas de las columnas para mostrar los datos de los contactos
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        apellidosColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidosProperty());
        telefonoColumn.setCellValueFactory(cellData -> cellData.getValue().getTelefonoProperty());
        direccionColumn.setCellValueFactory(cellData -> cellData.getValue().getDireccionProperty());

        // Cargar los contactos desde la base de datos y mostrarlos en la tabla
        tablaContactos.setItems(agendaDAO.obtenerContactos());
        
        // Agregar el evento de clic a la tabla
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
        // Realizar la búsqueda de contactos según el valor del campo de búsqueda
        ObservableList<Contacto> resultados;

        try {
            resultados = agendaDAO.buscarContactosPorNombre(nombre);
            
            tablaContactos.setItems(resultados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void mostrarFormularioAgregar(ActionEvent event) throws SQLException {
        try {
            System.out.println("Proves:");
            ObservableList<Contacto> contactos = agendaDAO.buscarContactosPorNombre("a");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("formulario.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Contacto");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            tablaContactos.setItems(agendaDAO.obtenerContactos());
        } catch (IOException e) {
            e.printStackTrace();
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

            tablaContactos.refresh(); // Actualizar la tabla después de la edición
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
}
