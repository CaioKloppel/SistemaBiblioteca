package org.example.livro;

import java.util.ArrayList;
import java.util.Arrays;

public class Livro {
    private String nome;
    private String autor;
    private ArrayList<String> categoria;
    private double preco;
    private boolean alugado;


    public Livro(String nome, String autor, double preco, ArrayList<String> categorias){
        this.nome = nome;
        this.autor = autor;
        this.preco = preco;
        categoria = categorias;
    }

    public Livro(){}

    public String getNome() {
        return nome;
    }

    public String getAutor() {
        return autor;
    }

    public ArrayList<String> getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void changePreco(double preco) {
        this.preco = preco;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }
}
