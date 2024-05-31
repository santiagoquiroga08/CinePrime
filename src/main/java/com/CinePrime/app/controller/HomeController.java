package com.CinePrime.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";  // nombre de tu archivo index.html en templates
    }

    @GetMapping("/funciones")
    public String funciones() {
        return "funciones";  // nombre de tu archivo funciones.html en templates
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";  // nombre de tu archivo nosotros.html en templates
    }

    @GetMapping("/preguntas")
    public String preguntas() {
        return "preguntas";  // nombre de tu archivo preguntas.html en templates
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";  // nombre de tu archivo contacto.html en templates
    }

    @GetMapping("/servicios")
    public String servicios() {
        return "servicios";  // nombre de tu archivo servicios.html en templates
    }
}