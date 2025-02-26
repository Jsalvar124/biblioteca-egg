package com.egg.biblioteca.service;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AutorService{
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor crearAutor(Autor autor){
        try{
            return autorRepository.save(autor);
        }catch (Exception e){
            System.err.println("Error al crear el autor: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Autor> listarAutores(){
        return autorRepository.findAll();
    }
}
