package com.aluracuros.literalura_challenge.repository;

import com.aluracuros.literalura_challenge.model.Idiomas;
import com.aluracuros.literalura_challenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findLibroBytitulo(String titulo);
    List<Libro> findLibrosByidioma(Idiomas idioma);

    @Query("SELECT l FROM Libro l ORDER BY l.descargas DESC LIMIT 10")
    List<Libro>top10Libros();


}
