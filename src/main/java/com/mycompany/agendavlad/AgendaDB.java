
package com.mycompany.agendavlad;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AgendaDB {
    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public ObservableList<Contacto> buscarContactosPorNombre(String nombre) throws SQLException {
        ObservableList<Contacto> contactos = FXCollections.observableArrayList();
        
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM contactos WHERE nombre LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + nombre + "%"); 
            
            ResultSet resultQuery = statement.executeQuery();
            while (resultQuery.next()) {
                String nombreContacto = resultQuery.getString("nombre");
                String apellidos = resultQuery.getString("apellidos");
                String telefono = resultQuery.getString("telefono");
                String direccion = resultQuery.getString("direccion");
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
        }
    }
    
    public ObservableList<Contacto> obtenerContactos() {
        ObservableList<Contacto> contactos = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT nombre, apellidos, telefono, direccion FROM Contactos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultQuery = statement.executeQuery();

            while (resultQuery.next()) {
                String nombre = resultQuery.getString("nombre");
                String apellidos = resultQuery.getString("apellidos");
                String telefono = resultQuery.getString("telefono");
                String direccion = resultQuery.getString("direccion");

                Contacto contacto = new Contacto(nombre, apellidos, telefono, direccion);
                contactos.add(contacto);
            }
        } catch (SQLException e) {
        }

        return contactos;
    }
     
    public void actualizarContacto(Contacto contacto) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "UPDATE contactos SET nombre = ?, apellidos = ?, telefono = ?, direccion = ? WHERE apellidos = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, contacto.getNombre());
            statement.setString(2, contacto.getApellidos());
            statement.setString(3, contacto.getTelefono());
            statement.setString(4, contacto.getDireccion());
            statement.setString(5, contacto.getApellidos());
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void eliminarContacto(Contacto contacto) throws SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "DELETE FROM contactos WHERE apellidos = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, contacto.getApellidos());
            statement.executeUpdate();

            System.out.println("Contacto eliminado correctamente: " + contacto);
        } catch (SQLException e) {
            System.out.println("Error al eliminar el contacto: " + contacto);
        }
    }

    public boolean comprobarDuplicado(String apellido) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM Contactos WHERE apellidos LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + apellido + "%");
            ResultSet resultQuery = statement.executeQuery();

            return !resultQuery.next();
        } catch (SQLException e) {
            return false;
        }  
    }
}
