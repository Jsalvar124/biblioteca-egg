package com.egg.biblioteca.service;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.entity.Editorial;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.repository.AutorRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AutorService{

    @Autowired // Optional, since we are using constructor based inyection.
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository; //Here Spring automatically instantiates the repo, (new Repository())
    }

    @Transactional
    public Autor crearAutor(String nombre){

        try{
            validar(nombre);
            Autor autor = new Autor(nombre);
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

    public Optional<Autor> buscarPorId(UUID id){
        return autorRepository.findById(id);
    }

    public Autor modificarAutor(UUID id, String nombre) throws MyException {
        Optional<Autor> autorResult = autorRepository.findById(id);
        Autor autorModificado;
        if(autorResult.isPresent()){
            autorModificado = autorResult.get();
        } else {
            throw new IllegalArgumentException("El autor no se encontró");
        }
        try{
            validar(nombre);
            autorModificado.setNombre(nombre);
            autorRepository.save(autorModificado);
        }catch (MyException e){
            e.printStackTrace();
            throw e;
        }
        return autorModificado;
    }

    private void validar(String nombre) throws MyException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("el nombre del autor no puede ser nulo o vacio");
        }}
}
