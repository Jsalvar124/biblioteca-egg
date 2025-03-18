package com.egg.biblioteca.controller;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.entity.Editorial;
import com.egg.biblioteca.entity.Libro;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.service.AutorService;
import com.egg.biblioteca.service.EditorialService;
import com.egg.biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("libro")
public class LibroController {
    @Autowired
    private final LibroService libroService;

    @Autowired
    private final AutorService autorService;

    @Autowired
    private final EditorialService editorialService;

    public LibroController(LibroService libroService, AutorService autorService, EditorialService editorialService) {
        this.libroService = libroService;
        this.autorService = autorService;
        this.editorialService = editorialService;
    }

    @GetMapping("/registrar")//localhost:8080/libro/registrar
    public String registrar(ModelMap model){ // se añade model map al controlador de el template.
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam(required = false) String titulo,
                           @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String idAutor,
                           @RequestParam(required = false) String idEditorial, ModelMap modelo) {
        try {

            libroService.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);

            //Success
            modelo.put("exito", "Libro cargado correctamente!");

        } catch (MyException ex) {
            Logger.getLogger(LibroController.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("error", ex.getMessage());
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.listarEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            return "libro_form.html"; // volvemos a cargar el formulario.
        }
        return "index.html";
    }


    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Libro> libros = libroService.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }
}
