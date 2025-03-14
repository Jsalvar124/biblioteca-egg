package com.egg.biblioteca.controller;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/autor")
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public String crearAutor(@RequestParam String nombre) {
        try {
            autorService.crearAutor(nombre);
        } catch (MyException ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "autor_form.html";
        }
        return "index.html";
    }


    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable UUID id){
        Optional<Autor> resultado = autorService.buscarPorId(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    };

    @GetMapping
    public ResponseEntity<List<Autor>> listarAutores() {
        List<Autor> resultado = autorService.listarAutores();
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
