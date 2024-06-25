package com.aluracuros.literalura_challenge.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer fechaFallecimiento;
    private Integer fechaNacimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor (DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
    }

    @Override
    public String toString() {
        return  "\nAutor: " + nombre + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Fecha de fallecimiento: " +  fechaFallecimiento + "\n" +
                "Libros: " + libros.stream().map(d -> d.getTitulo()).toList();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getfechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setfechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public Integer getfechaNacimiento() {
        return fechaNacimiento;
    }

    public void setfechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(e -> e.setAutor(this));
        this.libros = libros;
    }
}