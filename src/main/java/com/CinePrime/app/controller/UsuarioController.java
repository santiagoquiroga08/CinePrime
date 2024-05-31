package com.CinePrime.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.CinePrime.app.documents.Pelicula;
import com.CinePrime.app.documents.Usuario;
import com.CinePrime.app.repository.PeliculaRepository;
import com.CinePrime.app.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PeliculaRepository peliculaRepository;

    @GetMapping("/usuario/perfil")
    public String perfilUsuario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "perfil";  // Nombre del archivo HTML para el perfil del usuario
    }

    @GetMapping("/usuario/editar")
    public String editarPerfilUsuario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "editar-perfil";  // Nombre del archivo HTML para editar el perfil del usuario
    }

    @PostMapping("/usuario/editar")
    public String actualizarPerfilUsuario(@RequestParam String nombre, @RequestParam String email,
                                          @RequestParam String contraseña, @RequestParam String fechaNacimiento,
                                          @RequestParam String telefonoCelular, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContraseña(contraseña);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setTelefonoCelular(telefonoCelular);
        usuarioRepository.save(usuario);
        model.addAttribute("usuario", usuario);
        return "redirect:/usuario/perfil";
    }

    @GetMapping("/usuario/eliminar")
    public String eliminarPerfilUsuario(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        usuarioRepository.delete(usuario);
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/usuario/reservas")
    public String verReservasUsuario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        List<Pelicula> reservas = new ArrayList<>();
        for (String reservaId : usuario.getReservas()) {
            Pelicula pelicula = peliculaRepository.findById(reservaId).orElse(null);
            if (pelicula != null) {
                reservas.add(pelicula);
            }
        }

        model.addAttribute("reservas", reservas);
        return "ver-reservas"; // Nombre del archivo HTML para ver las reservas del usuario
    }

    @PostMapping("/usuario/reservas/eliminar/{id}")
    public String eliminarReservaUsuario(@PathVariable String id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        usuario.eliminarReserva(id);
        usuarioRepository.save(usuario);

        Pelicula pelicula = peliculaRepository.findById(id).orElse(null);
        if (pelicula != null) {
            pelicula.eliminarReserva(usuario.getId());
            peliculaRepository.save(pelicula);
        }

        return "redirect:/usuario/reservas";
    }
}
