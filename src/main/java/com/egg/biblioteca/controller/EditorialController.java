package com.egg.biblioteca.controller;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.entity.Editorial;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private final EditorialService editorialService;

    public EditorialController(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    @PostMapping("/registro") // localhost:8080/editorial/registro
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            editorialService.crearEditorial(nombre);
            modelo.put("exito", "Editorial regsitrada con éxito!");
        } catch (MyException ex) {
            Logger.getLogger(EditorialController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }
        return "index.html";
    }

    @GetMapping("/registrar") // localhost:8080/editorial/registrar
    public String registrar() {
        return "editorial_form.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Editorial> editoriales = editorialService.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        try{
            Editorial editorial = editorialService.buscarPorId(id);
            modelo.put("editorial", editorial);
        }catch (MyException e){
            modelo.put("error", e.getMessage());
            return "editorial_lista.html";
        }
        return "editorial_modificar.html";
    }


    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,@RequestParam String nombre, ModelMap modelo) {
        try {
            editorialService.modificarEditorial(id, nombre);
            return "redirect:../lista";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            try{
                Editorial editorial = editorialService.buscarPorId(id);
                modelo.put("editorial", editorial);
            }catch (MyException e){
                modelo.put("error", e.getMessage());
                return "editorial_lista.html";
            }
            return "editorial_modificar.html";
        }
    }
}
