package com.egg.biblioteca.repository;

import com.egg.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre LIKE %:autor%")
    public List<Libro> buscarPorAutor(@Param("autor") String autor);

    // Alternativas con JPA
    Libro findByTitulo(String titulo);
    List<Libro> findByAutor_Nombre(String nombre); //Coincidencia Exacta
}
