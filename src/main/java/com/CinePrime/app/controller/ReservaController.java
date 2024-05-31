package com.CinePrime.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import com.CinePrime.app.documents.Pelicula;
import com.CinePrime.app.documents.Usuario;
import com.CinePrime.app.repository.PeliculaRepository;
import com.CinePrime.app.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReservaController {

    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/peliculas")
    public String listarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "listar-peliculas"; // Nombre del archivo HTML para listar películas
    }

    @GetMapping("/peliculas/reservar/{id}")
    public String reservarPeliculaForm(@PathVariable String id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Pelicula pelicula = peliculaRepository.findById(id).orElse(null);
        if (pelicula == null) {
            return "redirect:/peliculas";
        }

        model.addAttribute("pelicula", pelicula);
        return "reservar-pelicula"; // Nombre del archivo HTML para reservar una película
    }

    @PostMapping("/peliculas/reservar/{id}")
    public String reservarPelicula(@PathVariable String id, @RequestParam int cantidad, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Pelicula pelicula = peliculaRepository.findById(id).orElse(null);
        if (pelicula != null && pelicula.agregarReservas(usuario.getId(), cantidad)) {
            peliculaRepository.save(pelicula);
            for (int i = 0; i < cantidad; i++) {
                usuario.agregarReserva(id);
            }
            usuarioRepository.save(usuario);
            model.addAttribute("mensaje", "Reserva exitosa");
        } else {
            model.addAttribute("error", "No se pudo realizar la reserva, asientos insuficientes.");
        }

        model.addAttribute("pelicula", pelicula);
        return "reservar-pelicula"; // Nombre del archivo HTML para reservar una película
    }

    

}
