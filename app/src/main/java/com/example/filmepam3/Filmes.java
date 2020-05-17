package com.example.filmepam3;

public class Filmes {
    int id;
    String nome;
    String genero;
    String nota;
    String lançamento;
    String sinopse;

    public Filmes() {
    }

    public Filmes(int id, String nome, String genero, String nota, String lançamento , String sinopse) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.nota = nota;
        this.lançamento = lançamento;
        this.sinopse = sinopse;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public String getNota() {
        return nota;
    }

    public String getLançamento() {
        return lançamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setLançamento(String lançamento) {
        this.lançamento = lançamento;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}
