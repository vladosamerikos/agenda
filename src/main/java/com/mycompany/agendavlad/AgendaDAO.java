
package com.mycompany.agendavlad;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AgendaDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public ObservableList<Contacto> buscarContactosPorNombre(String nombre) throws SQLException {
        ObservableList<Contacto> contactos = FXCollections.observableArrayList();
        
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM contactos WHERE nombre LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + nombre + "%"); // Utilizar LIKE con % para buscar coincidencias parciales
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombreContacto = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                Contacto contacto = new Contacto(nombreContacto, apellidos, telefono, direccion);
                contactos.add(contacto);
            }
        }
        
        return contactos;
    }
    
      public void agregarContacto(Contacto contacto) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "INSERT INTO Contactos (nombre, apellidos, telefono, direccion) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, contacto.getNombre());
            statement.setString(2, contacto.getApellidos());
            statement.setString(3, contacto.getTelefono());
            statement.setString(4, contacto.getDireccion());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public ObservableList<Contacto> obtenerContactos() {
        ObservableList<Contacto> contactos = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT nombre, apellidos, telefono, direccion FROM Contactos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");

                Contacto contacto = new Contacto(nombre, apellidos, telefono, direccion);
                contactos.add(contacto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactos;
    }
     
 public void actualizarContacto(Contacto contacto) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "UPDATE contactos SET apellidos = ?, telefono = ?, direccion = ? WHERE nombre = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, contacto.getApellidos());
            statement.setString(2, contacto.getTelefono());
            statement.setString(3, contacto.getDireccion());
            statement.setString(4, contacto.getNombre());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
