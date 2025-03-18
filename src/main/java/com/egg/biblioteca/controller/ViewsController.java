package com.egg.biblioteca.controller;

import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ViewsController {
    @Autowired
    UsuarioService usuarioService;

    public ViewsController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String index() {
        return "index.html";   // Acá es que retornamos con el método.
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap model)  {
        try{
            usuarioService.registrar(nombre, email, password, password2);
            model.put("exito", "Usuario registrado con éxito");
            return "index.html";
        }catch (MyException e){
            model.put("error", e.getMessage());
            model.put("nombre", nombre);
            model.put("email", email);

            return "registrar.html";
        }
    }
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}