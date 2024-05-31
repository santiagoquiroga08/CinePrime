package com.CinePrime.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CinePrime.app.documents.Administrador;
import com.CinePrime.app.documents.Usuario;
import com.CinePrime.app.repository.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";  // Nombre del archivo HTML para la página de login
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        Administrador administrador = administradorRepository.findByEmail(email);

        if (usuario != null && usuario.getContraseña().equals(password)) {
            session.setAttribute("usuario", usuario);
            return "redirect:/usuario/perfil";
        } else if (administrador != null && administrador.getPassword().equals(password)) {
            session.setAttribute("administrador", administrador);
            return "redirect:/admin/dashboard";  // Ajusta esta ruta según sea necesario
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
