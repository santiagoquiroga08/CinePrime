package com.CinePrime.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CinePrime.app.documents.Pelicula;
import com.CinePrime.app.documents.Usuario;
import com.CinePrime.app.repository.PeliculaRepository;
import com.CinePrime.app.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdministradorController {

    @Autowired
    private PeliculaRepository peliculaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Verificar si el administrador está autenticado
        if (session.getAttribute("administrador") == null) {
            return "redirect:/login";
        }

        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "admin-dashboard"; // Nombre del archivo HTML para el panel de administrador
    }

    @GetMapping("/admin/crear-pelicula")
    public String crearPeliculaForm(HttpSession session) {
        // Verificar si el administrador está autenticado
        if (session.getAttribute("administrador") == null) {
            return "redirect:/login";
        }

        return "crear-pelicula"; // Nombre del archivo HTML para crear una película
    }

    @PostMapping("/admin/crear-pelicula")
    public String crearPelicula(@RequestParam String titulo, @RequestParam String descripcion, @RequestParam int maxAsientos, @RequestParam String horario, @RequestParam String director, @RequestParam String posterUrl, HttpSession session) {
        // Verificar si el administrador está autenticado
        if (session.getAttribute("administrador") == null) {
            return "redirect:/login";
        }

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(titulo);
        pelicula.setDescripcion(descripcion);
        pelicula.setMaxAsientos(maxAsientos);
        pelicula.setHorario(horario);
        pelicula.setDirector(director);
        pelicula.setPosterUrl(posterUrl);
        peliculaRepository.save(pelicula);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/editar-pelicula/{id}")
    public String editarPeliculaForm(@PathVariable String id, HttpSession session, Model model) {
        // Verificar si el administrador está autenticado
        if (session.getAttribute("administrador") == null) {
            return "redirect:/login";
        }

        Pelicula pelicula = peliculaRepository.findById(id).orElse(null);
        if (pelicula == null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("pelicula", pelicula);
        return "editar-pelicula"; // Nombre del archivo HTML para editar una película
    }

    @PostMapping("/admin/editar-pelicula/{id}")
    public String actualizarPelicula(@PathVariable String id, @RequestParam String titulo, @RequestParam String descripcion, @RequestParam int maxAsientos, @RequestParam String horario, @RequestParam String director, @RequestParam String posterUrl, HttpSession session) {
        // Verificar si el administrador está autenticado
        if (session.getAttribute("administrador") == null) {
            return "redirect:/login";
        }

        Pelicula pelicula = peliculaRepository.findById(id).orElse(null);
        if (pelicula == null) {
            return "redirect:/admin/dashboard";
        }

        pelicula.setTitulo(titulo);
        pelicula.setDescripcion(descripcion);
        pelicula.setMaxAsientos(maxAsientos);
        pelicula.setHorario(horario);
        pelicula.setDirector(director);
        pelicula.setPosterUrl(posterUrl);
        peliculaRepository.save(pelicula);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/eliminar-pelicula/{id}")
    public String eliminarPelicula(@PathVariable String id, HttpSession session) {
        // Verificar si el administrador está autenticado
        if (session.getAttribute("administrador") == null) {
            return "redirect:/login";
        }

        Pelicula pelicula = peliculaRepository.findById(id).orElse(null);
        if (pelicula != null) {
            peliculaRepository.delete(pelicula);
        }

        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/admin/reservas")
    public String verTodasReservas(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Pelicula> peliculas = peliculaRepository.findAll();

        List<ReservaDTO> reservas = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            for (String reservaId : usuario.getReservas()) {
                Pelicula pelicula = peliculaRepository.findById(reservaId).orElse(null);
                if (pelicula != null) {
                    reservas.add(new ReservaDTO(usuario, pelicula));
                }
            }
        }

        model.addAttribute("reservas", reservas);
        return "ver-todas-reservas"; // Nombre del archivo HTML para ver todas las reservas
    }

    // Clase DTO para agrupar la información de la reserva
    public static class ReservaDTO {
        private Usuario usuario;
        private Pelicula pelicula;

        public ReservaDTO(Usuario usuario, Pelicula pelicula) {
            this.usuario = usuario;
            this.pelicula = pelicula;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public Pelicula getPelicula() {
            return pelicula;
        }
    }
}
