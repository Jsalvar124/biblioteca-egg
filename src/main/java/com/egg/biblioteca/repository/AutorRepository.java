package com.egg.biblioteca.repository;

import com.egg.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface AutorRepository extends JpaRepository<Autor, UUID> {


}
