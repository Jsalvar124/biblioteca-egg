package com.egg.biblioteca.controller;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping("/registro") // localhost:8080/autor/registro
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            autorService.crearAutor(nombre);
            modelo.put("exito", "El autor fue registrado con éxito!");
        } catch (MyException ex) {
            Logger.getLogger(AutorController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        return "index.html";
    }


    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {


        List<Autor> autores = autorService.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        try{
            Autor autor = autorService.buscarPorId(id);
            modelo.put("autor", autor);
        }catch (MyException e){
            modelo.put("error", e.getMessage());
            return "autor_lista.html";
        }
        return "autor_modificar.html";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,@RequestParam String nombre, ModelMap modelo) {
        try {
            autorService.modificarAutor(id, nombre);
            return "redirect:../lista";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            try{
                Autor autor = autorService.buscarPorId(id);
                modelo.put("autor", autor);
            }catch (MyException e){
                modelo.put("error", e.getMessage());
                return "autor_lista.html";
            }
            return "autor_modificar.html";
        }
    }
}
