package com.aluracuros.literalura_challenge.model;



public enum Idiomas {
    ESPANOL("[es]", "Español"),
    INGLES("[en]", "Ingles"),
    FRANCES("[fr]", "Frances"),
    PORTUGUES("[pt]", "Portugues");

    private String idiomaGutendex;
    private String idiomaEspanol;

    Idiomas(String categoriaGutendex, String categoriaEspanol){
        this.idiomaEspanol = categoriaEspanol;
        this.idiomaGutendex = categoriaGutendex;
    }
    public static Idiomas fromString(String text){
        for (Idiomas categoriaIdioma : Idiomas.values()){
            if (categoriaIdioma.idiomaGutendex.equalsIgnoreCase(text)){
                return categoriaIdioma;
            }
        }
        throw new IllegalArgumentException("Ningun Idioma Encontrado: " + text);
    }

    public static Idiomas fromEspanol (String text){
        for (Idiomas categoriaIdioma : Idiomas.values()){
            if (categoriaIdioma.idiomaEspanol.equalsIgnoreCase(text)){
                return categoriaIdioma;
            }
        }
        throw new IllegalArgumentException("Ningun Idioma Encontrado: " + text);
    }
}