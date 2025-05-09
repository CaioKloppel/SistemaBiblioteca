package org.example.livro;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Livro {
    private final String nome;
    private final String autor;
    private final ArrayList<String> categoria;
    private double preco;
    private boolean alugado;

    @JsonCreator
    public Livro(@JsonProperty("nome") String nome,
                 @JsonProperty("autor") String autor,
                 @JsonProperty("preco") double preco,
                 @JsonProperty("categorias") ArrayList<String> categorias){
        this.nome = nome;
        this.autor = autor;
        this.preco = preco;
        categoria = categorias;
    }

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
