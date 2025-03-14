package com.egg.biblioteca.service;

import com.egg.biblioteca.entity.Autor;
import com.egg.biblioteca.entity.Editorial;
import com.egg.biblioteca.entity.Libro;
import com.egg.biblioteca.exeptions.MyException;
import com.egg.biblioteca.repository.AutorRepository;
import com.egg.biblioteca.repository.EditorialRepository;
import com.egg.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibroService {

    @Autowired
    LibroRepository libroRepository;
    @Autowired
    AutorRepository autorRepository;
    @Autowired
    EditorialRepository editorialRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository, EditorialRepository editorialRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.editorialRepository = editorialRepository;
    }

    public Libro crearLibro(Long isbn, String titulo, int ejemplares, UUID autorId, int editorialId) throws MyException {
        Optional<Autor> autorResult = autorRepository.findById(autorId);
        Autor autor;
        if(autorResult.isPresent()){
            autor = autorResult.get();
        } else {
            throw new IllegalArgumentException("El Autor no se encontró.");
        }

        Optional<Editorial> editorialResult = editorialRepository.findById(editorialId);
        Editorial editorial;
        if(editorialResult.isPresent()){
            editorial = editorialResult.get();
        } else {
            throw new IllegalArgumentException("La Editorial no se encontró.");
        }
        LocalDate alta = LocalDate.now(); // recibe la fecha de creación del libro.
        try{
            validar(isbn, titulo, ejemplares,autorId, editorialId);
            Libro nuevoLibro = new Libro(
                    isbn,
                    titulo,
                    ejemplares,
                    alta,
                    autor,
                    editorial
            );

            return libroRepository.save(nuevoLibro);
        }catch (MyException e){
            e.printStackTrace();
            throw e;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<Libro> listarLibros(){
        List<Libro> libros = new ArrayList<>();
        libros = libroRepository.findAll();
        return libros;
    }

    public Libro modificarLibro(Long isbn, String titulo, Integer ejemplares, UUID autorId, Integer editorialId){
        Optional<Libro> libroResult = libroRepository.findById(isbn);
        Libro libroModificado;
        if(libroResult.isPresent()){
            libroModificado = libroResult.get();
        }else {
            throw new IllegalArgumentException("El Libro no se encontró.");
        }

        if(titulo != null){
            libroModificado.setTitulo(titulo);
        }
        if(ejemplares != null){
            libroModificado.setEjemplares(ejemplares);
        }
        if(autorId != null){
            Optional<Autor> autorResult = autorRepository.findById(autorId);
            if(autorResult.isPresent()){
                Autor autor = autorResult.get();
                libroModificado.setAutor(autor);
            } else {
                throw new IllegalArgumentException("El Autor no se encontró.");
            }

        }
        if(editorialId != null){
            Optional<Editorial> editorialResult = editorialRepository.findById(editorialId);
            if(editorialResult.isPresent()){
                Editorial editorial = editorialResult.get();
                libroModificado.setEditorial(editorial);
            } else {
                throw new IllegalArgumentException("La Editorial no se encontró.");
            }
        }

        libroRepository.save(libroModificado);
        return libroModificado;
    }

    private void validar(Long isbn, String titulo, int ejemplares, UUID autorId, int editorialId) throws MyException {
        if (isbn == null || isbn <= 0) {
            throw new MyException("El ISBN no puede ser nulo o negativo.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new MyException("El título no puede ser nulo o vacío.");
        }
        if (ejemplares < 0) {
            throw new MyException("La cantidad de ejemplares no puede ser negativa.");
        }
        if (autorId == null) {
            throw new MyException("El ID del autor no puede ser nulo.");
        }
        if (editorialId <= 0) {
            throw new MyException("El ID de la editorial debe ser mayor que cero.");
        }
    }
}

