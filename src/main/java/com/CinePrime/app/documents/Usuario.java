package com.CinePrime.app.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "usuario")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String email;
    private String contraseña;
    private String fechaNacimiento; 
    private String telefonoCelular;
    private List<String> reservas;

    public Usuario() {
        super();
        this.reservas = new ArrayList<>();
    }

    public Usuario(String id, String nombre, String email, String contraseña, String fechaNacimiento, String telefonoCelular) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoCelular = telefonoCelular;
        this.reservas = new ArrayList<>();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public List<String> getReservas() {
        return reservas;
    }

    public void setReservas(List<String> reservas) {
        this.reservas = reservas;
    }

    public void agregarReserva(String reservaId) {
        this.reservas.add(reservaId);
    }

    public void eliminarReserva(String reservaId) {
        this.reservas.remove(reservaId);
    }
}
