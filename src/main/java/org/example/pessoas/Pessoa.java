package org.example.pessoas;

import org.example.livro.Livro;
import org.example.util.Funcoes;
import org.example.util.Access;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Pessoa {
    private String nome;
    private String nickAcesso;
    private String senha;
    private String email;


    public String getNome() {
        return nome;
    }

    public String getNickAcesso() {
        return nickAcesso;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
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

    void setEmail(String email) {
        this.email = email;
        while (!this.email.contains("@")) {
            System.out.println("Email incorreto, digite novamente [Necessário conter '@']: ");
            this.email = Access.getInstance().getScanner().nextLine();
        }
    }


    public void changeNickAcesso(){
        String novoNick = Funcoes.pergunta("Novo nick de acesso: ");
        setNickAcesso(novoNick);
        System.out.println("Nick de acesso atualizado com sucesso!");
    }

    public void changeSenha(){
        String novaSenha = Funcoes.pergunta("Nova senha: ");
        setSenha(novaSenha);
        System.out.println("Nova senha cadastrada com sucesso!");
    }

    public void changeEmail(){
        String novoEmail = Funcoes.pergunta("Novo email: ");
        setEmail(novoEmail);
        System.out.println("Novo email cadastrado com sucesso!");
    }

    public void consultarLivro() throws IOException {
        List<Livro> livros = Access.getInstance().getDbBiblioteca().loadData();
        String pesquisa = Funcoes.pergunta("Pesquisar livro: ").toLowerCase().trim();
        boolean encontrado = false;
        for (Livro livro : livros) {
            if (livro.getNome().contains(pesquisa) || livro.getAutor().contains(pesquisa) || livro.getCategoria().contains(pesquisa)) {
                encontrado = true;
                System.out.println("\nNome do livro: " + livro.getNome().toUpperCase() +
                        "\nAutor: " + livro.getAutor().toUpperCase() +
                        "\nPreço: R$" + livro.getPreco() +
                        "\nCategorias relevantes: " + String.join(" | ", livro.getCategoria()) +
                        "\nDisponibilidade: " + (livro.isAlugado() ? "Indisponível" : "Disponível\n")
                );
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
