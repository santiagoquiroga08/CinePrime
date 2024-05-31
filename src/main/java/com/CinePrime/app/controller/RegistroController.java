package com.CinePrime.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.CinePrime.app.documents.Usuario;
import com.CinePrime.app.repository.UsuarioRepository;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String nombre, @RequestParam String email, 
                                   @RequestParam String contraseña, @RequestParam String fechaNacimiento, 
                                   @RequestParam String telefonoCelular, Model model) {
        // Verificar si el email ya está registrado
        if (usuarioRepository.findByEmail(email) != null) {
            model.addAttribute("error", "El email ya está registrado.");
            return "registro";
        }

        // Crear un nuevo usuario y guardarlo en la base de datos
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContraseña(contraseña);
        nuevoUsuario.setFechaNacimiento(fechaNacimiento);
        nuevoUsuario.setTelefonoCelular(telefonoCelular);

        usuarioRepository.save(nuevoUsuario);
        return "redirect:/login";  // Redirigir al login después de registrar
    }
}
