package com.egg.biblioteca.service;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.entity.Editorial;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.repository.EditorialRepository;
import com.sun.source.tree.OpensTree;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EditorialService {
    @Autowired
    EditorialRepository editorialRepository;

    public EditorialService(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    @Transactional
    public Editorial crearEditorial(String nombre) throws MyException {
        try{
            validar(nombre);
            Editorial editorial = new Editorial(nombre);
            return editorialRepository.save(editorial);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException("Error al crear el editorial: " + e.getMessage());
        }
    }

    public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = new ArrayList<>();
        editoriales = editorialRepository.findAll();
        return editoriales;
    }

    @Transactional
    public Editorial modificarEditorial(String id, String nombre) throws MyException {
        Editorial editorial;
        try{
            editorial = buscarPorId(id);
            validar(nombre);
            editorial.setNombre(nombre);
            editorialRepository.save(editorial);
        }catch (MyException e){
            e.printStackTrace();
            throw e;
        }
        return editorial;
    }

    private void validar(String nombre) throws MyException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("el nombre de la editorial no puede ser nulo o vacio");
        }
    }

    public Editorial buscarPorId(String id) throws MyException {
        if(id == null){
            throw new MyException("Editorial no puede ser nulo");
        }
        Integer editorialId = Integer.valueOf(id);
        Optional<Editorial> editorialResult = editorialRepository.findById(editorialId);
        if(editorialResult.isPresent()){
            return editorialResult.get();
        }
        throw new MyException("Editorial no se encontró");

    }
}
