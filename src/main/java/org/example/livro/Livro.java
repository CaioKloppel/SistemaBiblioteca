package org.example.livro;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Livro {
    private final String nome;
    private final String autor;
    private final ArrayList<String> categoria;
    private int quantidadeTotal;
    private int quantidadeDisponivel;
    private double preco;
    private boolean alugado;

    @JsonCreator
    public Livro(@JsonProperty("nome") String nome,
                 @JsonProperty("autor") String autor,
                 @JsonProperty("preco") double preco,
                 @JsonProperty("categoria") ArrayList<String> categoria,
                 @JsonProperty("quantidadeDisponivel") int quantidadeTotal) {
        this.nome = nome;
        this.autor = autor;
        this.preco = preco;
        this.quantidadeTotal = quantidadeTotal;
        quantidadeDisponivel = quantidadeTotal;
        this.categoria = categoria;
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

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
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

    public void editQuantidadeTotal(int quantidade){
        if (quantidadeTotal + quantidade < 0){
            System.out.println("Operação impossível de ser realizada pois você não pode remover mais livros do que constam disponíveis.");
        } else {
            quantidadeTotal += quantidade;
            editQuantidadeDisponivel(quantidade);
        }
    }

    public void editQuantidadeDisponivel(int quantidade){
        quantidadeDisponivel += quantidade;
        alugado = quantidadeDisponivel < 1;
    }
}
