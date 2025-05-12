package org.example.pessoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.livro.Livro;
import org.example.util.Access;
import org.example.util.Funcoes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Usuario extends Pessoa{
    private double saldo;
    private final ArrayList<String> livrosAlugados;

    public Usuario(@JsonProperty("nome") String nome,
                   @JsonProperty("senha") String senha,
                   @JsonProperty("nickAcesso") String nick,
                   @JsonProperty("email") String email,
                   @JsonProperty("saldo") double saldo,
                   @JsonProperty("livrosAlugados") ArrayList<String> livrosAlugados){
        setNome(nome); setSenha(senha); setEmail(email); setNickAcesso(nick); this.saldo = saldo; this.livrosAlugados = livrosAlugados;
    }


    public ArrayList<String> getLivrosAlugados() {
        return livrosAlugados;
    }

    public double getSaldo() {return saldo;}

    public void addSaldo(){
        double saldo = Funcoes.getValorMonetario("Valor a ser adicionado como crédito: ");
        this.saldo += saldo;
        System.out.println("Saldo adicionado com sucesso!");
        consultarSaldo();
    }

    public void alugarLivro() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        boolean livroEncontrado = false;
        List<Livro> livros = Access.getInstance().getDbBiblioteca().loadData();
        String nomeLivro = Funcoes.pergunta("Título do livro que gostaria de alugar: ").toLowerCase().trim();
        for (Livro livro : livros){
            if (livro.getNome().equals(nomeLivro) && livro.isAlugado()){
                livroEncontrado = true;
                System.out.println("Todos os livros desse título já se encontram alugados.");
                break;
            } else if (livro.getNome().equals(nomeLivro) && !livro.isAlugado()){
                livroEncontrado = true;
                String resposta = Funcoes.perguntaComVerificacao("O livro " + livro.getNome().toUpperCase() + " do autor " + livro.getAutor() + ".\nNo valor de: R$" + livro.getPreco() + " está disponivel!\nConfirme o aluguel do livro (y) ou caso desista (n): ", new ArrayList<>(Arrays.asList("y", "n")));
                if (resposta.equals("y")){
                    if (saldo >= livro.getPreco()) {
                        saldo = (double) Math.round((saldo - livro.getPreco()) * 100) / 100;
                        System.out.println("Você finalizou o aluguel do livro " + livro.getNome().toUpperCase() + " do autor " + livro.getAutor().toUpperCase() + "!" +
                                "\nAgora ele é seu!" +
                                "\nValor descontado de seu saldo: " + livro.getPreco() +
                                "\nSeu saldo atual é de: " + saldo + "\n");
                        livrosAlugados.add(livro.getNome());
                        consultarLivrosAlugados();
                        livro.editQuantidadeDisponivel(-1);
                        Access.getInstance().getDbBiblioteca().updateItem(livro.getNome(), livro);
                    } else {
                        System.out.println("Você não possui saldo suficiente para realizar o aluguel desse livro.");
                    }
                } else {
                    System.out.println("Você não confirmou o aluguel, voltando para o menu inicial.");
                }
                break;
            }
        }
        if (!livroEncontrado){
            System.out.println("O nome do livro que você buscou não se encontra em nosso sistema, caso tenha alguma dúvida, pode tentar buscar por categoria em nosso sistema.");
        }
    }

    public void devolverLivro() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (livrosAlugados.isEmpty()){
            System.out.println("Você não possui nenhum livro alugado.");
            return;
        }
        List<Livro> livros = Access.getInstance().getDbBiblioteca().loadData();
        String livroDevolvido = Funcoes.pergunta("Qual livro você gostaria de devolver: ");
        boolean encontrado = false;
        for (String livro : livrosAlugados){
            if (livro.equals(livroDevolvido)){
                encontrado = true;
                String confirmacao = Funcoes.perguntaComVerificacao("Livro " + livro.toUpperCase() + " encontrado, confirme a operação com [y] ou encerre com [n]: ", new ArrayList<>(Arrays.asList("y", "n")));
                if (confirmacao.equals("n")){
                    System.out.println("Encerrando operação.");
                } else {
                    for (Livro livroAtualizado : livros){
                        if (livroAtualizado.getNome().equals(livroDevolvido)){
                            livroAtualizado.editQuantidadeDisponivel(+1);
                            Access.getInstance().getDbBiblioteca().updateItem(livro, livroAtualizado);
                        }
                    }
                    livrosAlugados.remove(livro);
                    System.out.println("Livro devolvido com sucesso!");
                }
                break;
            }
        }
        if (!encontrado){
            System.out.println("O livro não foi encontrado entre seus livros alugados!");
        }
    }

    public void consultarSaldo(){
        System.out.println("O seu saldo atual é de R$" + getSaldo());
    }

    public void consultarLivrosAlugados(){
        if (livrosAlugados.isEmpty()){
            System.out.println("Você não possui nenhum livro alugado.");
            return;
        }
        System.out.println("Os seus livros alugados atualmente são: " + String.join(" | ", getLivrosAlugados()).toUpperCase());
    }
}
