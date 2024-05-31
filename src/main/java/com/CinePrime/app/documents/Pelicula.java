package com.CinePrime.app.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "pelicula")
public class Pelicula {
    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private int maxAsientos;
    private String horario;
    private String director;
    private String posterUrl;
    private List<String> reservas;

    public Pelicula() {
        this.reservas = new ArrayList<>();
    }

    public Pelicula(String id, String titulo, String descripcion, int maxAsientos, String horario, String director, String posterUrl) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.maxAsientos = maxAsientos;
        this.horario = horario;
        this.director = director;
        this.posterUrl = posterUrl;
        this.reservas = new ArrayList<>();
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

    public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMaxAsientos() {
        return maxAsientos;
    }

    public void setMaxAsientos(int maxAsientos) {
        this.maxAsientos = maxAsientos;
    }

    public List<String> getReservas() {
        return reservas;
    }

    public void setReservas(List<String> reservas) {
        this.reservas = reservas;
    }

    public boolean agregarReservas(String usuarioId, int cantidad) {
        if (reservas.size() + cantidad <= maxAsientos) {
            for (int i = 0; i < cantidad; i++) {
                reservas.add(usuarioId);
            }
            return true;
        }
        return false;
    }

    public void eliminarReserva(String usuarioId) {
        reservas.remove(usuarioId);
    }

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
    
    
}
