package com.mycompany.agendavlad;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contacto {
    private StringProperty nombre;
    private StringProperty apellidos;
    private StringProperty telefono;
    private StringProperty direccion;

    public Contacto(String nombre, String apellidos, String telefono, String direccion) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.telefono = new SimpleStringProperty(telefono);
        this.direccion = new SimpleStringProperty(direccion);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty getNombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getApellidos() {
        return apellidos.get();
    }

    public StringProperty getApellidosProperty() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos.set(apellidos);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty getTelefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty getDireccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getNombreCompleto() {
        return nombre.get() + " " + apellidos.get();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Contacto contacto = (Contacto) obj;
        return nombre.get().equals(contacto.getNombre());
    }

    @Override
    public int hashCode() {
        return nombre.get().hashCode();
    }

    @Override
    public String toString() {
        return nombre.get();
    }
}
