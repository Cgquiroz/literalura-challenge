package com.aluracuros.literalura_challenge.principal;

import com.aluracuros.literalura_challenge.model.*;
import com.aluracuros.literalura_challenge.repository.AutorRepository;
import com.aluracuros.literalura_challenge.repository.LibroRepository;
import com.aluracuros.literalura_challenge.service.ConsumoAPI;
import com.aluracuros.literalura_challenge.service.ConvierteDatos;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<Libro> libros;
    private List<Autor> autores;
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository){
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        String menu = """
                ********** Bienvenidos a LiterAlura **********
                1.- Buscar libro por titulo
                2.- Mostrar libros registrados
                3.- Mostrar autores registrados
                4.- Mostrar Autores Vivos en una determinada fecha
                5.- Mostrar libros por idioma
                6.- Top 10 libros mas descargados
                0.- Salir
                """;
        while (opcion != 0) {
            System.out.println(menu);
            try {
                opcion = lectura.nextInt();
                lectura.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Solo se permiten las opciones del 1 al 5.");
                lectura.nextLine();
                continue;
            }
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosEnUnaDeterminadaFecha();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 6:
                    top10Libros();
                    break;
            }
        }
    }

    private String consultarLibro (){
        System.out.println("Escribe el nombre del libro a buscar: ");
        var nombreLibro = lectura.nextLine();
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "%20");
        String respuesta = consumoApi.obtenerDatosApi(url);
        return respuesta;
    }

    private void buscarLibroPorTitulo() {
        String respuesta = consultarLibro();
        DatosConsultaAPI datosConsultaAPI =convierteDatos.obtenerDatos(respuesta, DatosConsultaAPI.class);
        if (datosConsultaAPI.numeroLibros() !=0) {
            DatosLibro primerLibro = datosConsultaAPI.resultado().get(0);
            Autor autorLibro = new Autor(primerLibro.autores().get(0));
            Optional<Libro> libroBase = libroRepository.findLibroBytitulo(primerLibro.titulo());
            if (libroBase.isPresent()) {
                System.out.println("No se puede registrar el mismo líbro ");
                //System.out.println(libroBase);
            } else {
                Optional<Autor> autorDeBase = autorRepository.findBynombre(autorLibro.getNombre());
                if (autorDeBase.isPresent()) {
                    autorLibro = autorDeBase.get();
                } else {
                    autorRepository.save(autorLibro);
                }

                Libro libro = new Libro(primerLibro);
                libro.setAutor(autorLibro);
                libroRepository.save(libro);
                System.out.println(libro);
            }
        } else {
            System.out.println("Líbro no encontrado.");
        }
}
    private void mostrarLibrosRegistrados(){
        libros=libroRepository.findAll();
        libros.stream().forEach(System.out::println);
    }
    private void mostrarAutoresRegistrados(){
        autores=autorRepository.findAll();
        autores.stream().forEach(System.out::println);
    }
    private void mostrarAutoresVivosEnUnaDeterminadaFecha(){
        System.out.println("Introduce el año a buscar: ");
        try {
            Integer year = lectura.nextInt();
            lectura.nextLine();
            autores = autorRepository.mostrarAutoresVivosEnUnaDeterminadaFecha(year);
            if (autores.isEmpty()){
                System.out.println("No se encontraron autores en esa fecha");
            } else {
                autores.stream().forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println(e);
        }
    }
    private void mostrarLibrosPorIdioma(){
        String menuIdioma = """
                Ingrese el idioma para buscar los libros: 
                es = Español
                en = Ingles
                fr = Frances 
                pt = Portugues
                """;
        System.out.println(menuIdioma);
        String idiomaBuscado = lectura.nextLine();
        Idiomas idioma = null;
        switch (idiomaBuscado) {
            case "es":
                idioma = Idiomas.fromEspanol("Español");
                libros = libroRepository.findLibrosByidioma(idioma);
                if (libros.isEmpty()){
                    System.out.println("No hay libros registrados en "+idioma);
                } else {
                    libros.stream().forEach(System.out::println);
                }
                break;
            case "en":
                idioma = Idiomas.fromEspanol("Ingles");
                libros = libroRepository.findLibrosByidioma(idioma);
                if (libros.isEmpty()){
                    System.out.println("No hay libros registrados en "+ idioma);
                } else {
                    libros.stream().forEach(System.out::println);
                }
                break;
            case "fr":
                idioma = Idiomas.fromEspanol("Frances");
                libros = libroRepository.findLibrosByidioma(idioma);
                if (libros.isEmpty()){
                    System.out.println("No hay libros registrados en "+ idioma);
                } else {
                    libros.stream().forEach(System.out::println);
                }
                break;
            case "pt":
                idioma = Idiomas.fromEspanol("Portugues");
                libros = libroRepository.findLibrosByidioma(idioma);
                if (libros.isEmpty()){
                    System.out.println("No hay libros registrados en "+idioma);
                } else {
                    libros.stream().forEach(System.out::println);
                }
                break;
            default:
                System.out.println("Entrada inválida.");
                return;
        }
    }
    private void top10Libros(){
        libros=libroRepository.top10Libros();
        libros.stream().forEach(System.out::println);
    }

}
