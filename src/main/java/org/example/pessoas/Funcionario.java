package org.example.pessoas;

import org.example.jsonData.DataBiblioteca;
import org.example.livro.Livro;
import org.example.util.Funcoes;

import java.io.IOException;
import java.util.ArrayList;

public class Funcionario extends Pessoa{

    public Funcionario(String nome, String senha, String nick){
        setNome(nome); setSenha(senha); setNickAcesso(nick);
    }

    public void addLivro() throws IOException {
        String nome = null;
        String autor = null;
        ArrayList<String> categorias = new ArrayList<>();
        double preco = 0;
        boolean cadastrado = false;
        boolean interromperOperacao = false;

        while (!interromperOperacao){
            System.out.println("CASO DESEJE ENCERRAR A OPERAÇÃO, DIGITE SUA SENHA.");
            nome = Funcoes.pergunta("Nome do livro: ").toLowerCase().trim();
            if (getSenha().equals(nome)){interromperOperacao = true; continue;}
            autor = Funcoes.pergunta("Autor do livro: ").toLowerCase().trim();
            if (getSenha().equals(autor)){interromperOperacao = true; continue;}
            categorias = new ArrayList<>();
            String categoria = "";
            while (!categoria.equals("stop")){
                categoria = Funcoes.pergunta("""
                        Adicione as categorias relacionadas ao livro (Para facilitar a busca por categoria.)
                        Para sair da adição de categorias digite 'stop'.
                        Caso deseje apagar a última Categoria digite 'remove'
                        Categoria:\s""").toLowerCase().trim();
                if (categorias.contains(categoria)){
                    System.out.println("Você já adicionou essa categoria!");
                } else if (categoria.equals("remove") && !categorias.isEmpty()){
                    System.out.println("Última categoria digitada removida.");
                    categorias.removeLast();
                } else if (categoria.equals("remove")){
                    System.out.println("Todas as categorias já foram removidas ou nenhuma foi adicionada.");
                } else if (categoria.equals(getSenha())){
                    interromperOperacao = true;
                    categoria = "stop";
                } else if (!categoria.equals("stop")){
                    System.out.println("Categoria adicionada");
                    categorias.add(categoria);
                }
            }
            if (getSenha().equals(categoria)){continue;}
            preco = Funcoes.getValorMonetario("Preço do livro: ");
            interromperOperacao = true;
            cadastrado = true;
        }

        if (cadastrado){
            Livro livro = new Livro(nome, autor, preco, categorias);
            DataBiblioteca.getInstance().getDbBiblioteca().addItem(livro);
            System.out.println("Cadastro de livro " + livro.getNome() + " realizado com sucesso!");
        } else {
            System.out.println("Cadastro cancelado!");
        }

    }
}
