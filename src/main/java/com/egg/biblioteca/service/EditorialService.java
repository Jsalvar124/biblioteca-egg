package com.egg.biblioteca.service;

import com.egg.biblioteca.entity.Editorial;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.repository.EditorialRepository;
import com.sun.source.tree.OpensTree;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {
    @Autowired
    EditorialRepository editorialRepository;

    public EditorialService(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    @Transactional
    public Editorial crearEditorial(String nombre){
        try{
            validar(nombre);
            Editorial editorial = new Editorial(nombre);
            return editorialRepository.save(editorial);
        }catch (Exception e){
            System.err.println("Error al crear el editorial: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Editorial> listarEditoriales(){
        return editorialRepository.findAll();
    }

    public Editorial modificarEditorial(Integer id, String nombre) throws MyException {
        Optional<Editorial> editorialResult = editorialRepository.findById(id);
        Editorial editorialModificada;
        if(editorialResult.isPresent()){
            editorialModificada = editorialResult.get();
        } else {
            throw new IllegalArgumentException("El editorial no se encontró");
        }
        try{
            validar(nombre);
            editorialModificada.setNombre(nombre);
            editorialRepository.save(editorialModificada);
        }catch (MyException e){
            e.printStackTrace();
            throw e;
        }
        return editorialModificada;
    }

    private void validar(String nombre) throws MyException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MyException("el nombre de la editorial no puede ser nulo o vacio");
        }
    }
}
