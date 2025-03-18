package com.egg.biblioteca.service;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.entity.Editorial;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.repository.AutorRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Autor crearAutor(String nombre) throws MyException {

        try{
            validar(nombre);
            Autor autor = new Autor(nombre);
            return autorRepository.save(autor);
        }catch (MyException e){
            e.printStackTrace();
            throw new MyException("Error al crear el autor: " + e.getMessage());
        }
    }

    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList<>();
        autores = autorRepository.findAll();
        return autores;
    }

    public Autor buscarPorId(String id) throws MyException {
        if(id == null){
            throw new MyException("Autor no puede ser nulo");
        }
        UUID uuid = UUID.fromString(id);
        Optional<Autor> autorResult = autorRepository.findById(uuid);
        if(autorResult.isPresent()){
            return autorResult.get();
        }
        return null;
    }

    @Transactional
    public void modificarAutor(String id, String nombre) throws MyException {
        Autor autorModificado = buscarPorId(id);
        try{
            validar(nombre);
            autorModificado.setNombre(nombre);
            autorRepository.save(autorModificado);
        }catch (MyException e){
            e.printStackTrace();
            throw e;
        }
        System.out.println(autorModificado);
    }

    private void validar(String nombre) throws MyException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("el nombre del autor no puede ser nulo o vacio");
        }}
}
