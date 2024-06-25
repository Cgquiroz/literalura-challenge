package com.aluracuros.literalura_challenge.repository;

import com.aluracuros.literalura_challenge.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AutorRepository extends JpaRepository<Autor,Long>{
    Optional<Autor> findBynombre(String nombre);
    @Query("SELECT a FROM Autor a WHERE  a.fechaNacimiento < :Year")
    List<Autor> mostrarAutoresVivosEnUnaDeterminadaFecha(Integer Year);
}
