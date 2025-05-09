package org.example.pessoas;

import org.example.jsonData.DataBiblioteca;
import org.example.livro.Livro;
import org.example.util.Funcoes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Usuario extends Pessoa{
    private double saldo;
    private ArrayList<Livro> livrosAlugados;

    public Usuario(String nome, String senha, String nick, double saldo){
        setNome(nome); setSenha(senha); setNickAcesso(nick); this.saldo = saldo; livrosAlugados = new ArrayList<>();
    }

    public Usuario() {}

    public void addLivrosAlugado(Livro livroAlugado) {
        livrosAlugados.add(livroAlugado);
    }

    public ArrayList<Livro> getLivrosAlugados() {
        return livrosAlugados;
    }

    public void alugarLivro(String nomeLivro) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        boolean livroEncontrado = false;
        List<Livro> livros = DataBiblioteca.getInstance().getDbBiblioteca().loadData();
        for (Livro livro : livros){
            if (livro.getNome().equals(nomeLivro) && livro.isAlugado()){
                livroEncontrado = true;
                System.out.println("O livro já se encontra alugado.");
                break;
            } else if (livro.getNome().equals(nomeLivro) && !livro.isAlugado()){
                livroEncontrado = true;
                String resposta = Funcoes.perguntaComVerificacao("O livro está disponivel!\nConfirme o aluguel do livro (y) ou caso desista (n): ", new ArrayList<>(Arrays.asList("y", "n")));
                if (resposta.equals("y")){
                    saldo -= livro.getPreco();
                    System.out.println("Você finalizou o aluguel do livro " + livro.getNome() + " do autor " + livro.getAutor() + "!" +
                            "\nAgora ele é seu!" +
                            "\nValor descontado de seu saldo: " + livro.getPreco() +
                            "\nSeu saldo atual é de: " + saldo);
                    livrosAlugados.add(livro);
                    livro.setAlugado(true);
                    DataBiblioteca.getInstance().getDbBiblioteca().updateItem(livro.getNome(), livro);
                } else {
                    System.out.println("Você não confirmou o aluguel, voltando para o menu inicial.");
                }
                break;
            }
        }
        if (!livroEncontrado){
            System.out.println("O nome do livro que você buscou não se encotra em nosso sistema, caso tenha alguma dúvida, pode tentar buscar por categoria em nosso sistema.");
        }
    }
}
