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
import java.util.Locale;
import java.util.stream.Collectors;

public class Usuario extends Pessoa{
    private double saldo;
    private final ArrayList<Livro> livrosAlugados;

    public Usuario(@JsonProperty("nome") String nome,
                   @JsonProperty("senha") String senha,
                   @JsonProperty("nickAcesso") String nick,
                   @JsonProperty("email") String email,
                   @JsonProperty("saldo") double saldo){
        setNome(nome); setSenha(senha); setEmail(email); setNickAcesso(nick); this.saldo = saldo; livrosAlugados = new ArrayList<>();
    }

    public void addLivrosAlugado(Livro livroAlugado) {
        livrosAlugados.add(livroAlugado);
    }

    public ArrayList<Livro> getLivrosAlugados() {
        return livrosAlugados;
    }

    public void addSaldo(double saldo){
        this.saldo += saldo;
    }

    public double getSaldo() {
        return saldo;
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
                                "\nSeu saldo atual é de: " + saldo);
                        livrosAlugados.add(livro);
                        livro.editQuantidade(-1);
                        livro.setAlugado();
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
            System.out.println("O nome do livro que você buscou não se encotra em nosso sistema, caso tenha alguma dúvida, pode tentar buscar por categoria em nosso sistema.");
        }
    }

    public void consultarSaldo(){
        System.out.println("O seu saldo atual é de R$" + saldo);
    }

    public void consultarLivrosAlugados(){
        System.out.println("Os seus livros alugados são: " + livrosAlugados.stream().map(Livro::getNome).collect(Collectors.joining(" | ")));
    }
}
