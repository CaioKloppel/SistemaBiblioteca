package org.example.pessoas;

import org.example.livro.Livro;
import org.example.util.Funcoes;
import org.example.util.Access;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

abstract class Pessoa {
    private String nome;
    private String nickAcesso;
    private String senha;


    public String getNome() {
        return nome;
    }

    public String getNickAcesso() {
        return nickAcesso;
    }

    public String getSenha() {
        return senha;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    void setNickAcesso(String nickAcesso) {
        this.nickAcesso = nickAcesso;
    }

    void setSenha(String senha) {
        this.senha = senha;
    }

    public void changeNickAcesso(String nickAcesso){
        this.nickAcesso = nickAcesso;
    }

    public void changeSenha(String senha){
        this.senha = senha;
    }

    public void consultarLivro() throws IOException {
        List<Livro> livros = Access.getInstance().getDbBiblioteca().loadData();
        String pesquisa = Funcoes.pergunta("Pesquisar livro: ").toLowerCase().trim();
        boolean encontrado = false;
        for (Livro livro : livros) {
            if (livro.getNome().equals(pesquisa) || livro.getAutor().equals(pesquisa) || livro.getCategoria().contains(pesquisa)) {
                encontrado = true;
                System.out.println("\nNome do livro: " + livro.getNome() +
                        "\nAutor: " + livro.getAutor() +
                        "\nPreço: R$" + livro.getPreco() +
                        "\nCategorias relevantes: " + String.join(" | ", livro.getCategoria()) +
                        "\nDisponibilidade: " + (livro.isAlugado() ? "Indisponível" : "Disponível\n"));
            }
        }
        if (!encontrado){
            System.out.println("Não há nada correspondente a sua pesquisa em nossa biblioteca.");
        }
    }

    public boolean encerrarPrograma(){
        try {
            System.out.println("Encerrando programa...");
            Access.getInstance().getScanner().close();
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return true;
    }
}
