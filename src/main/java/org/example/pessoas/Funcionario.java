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

public class Funcionario extends Pessoa{

    public Funcionario(@JsonProperty("nome") String nome,
                       @JsonProperty("senha") String senha,
                       @JsonProperty("nickAcesso") String nick,
                       @JsonProperty("email") String email){
        setNome(nome); setSenha(senha); setNickAcesso(nick); setEmail(email);
    }

    @Override
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
                        "\nDisponibilidade: " + (livro.isAlugado() ? "Indisponível" : "Disponível\n") +
                        "\nQuantidade total de livros: " + livro.getQuantidadeTotal() +
                        "\nQuantidade de livros alugados: " + (livro.getQuantidadeTotal() - livro.getQuantidadeDisponivel())
                );
            }
        }
        if (!encontrado){
            System.out.println("Não há nada correspondente a sua pesquisa em nossa biblioteca.");
        }
    }

    public void addLivro() throws IOException {
        List<Livro> livros = Access.getInstance().getDbBiblioteca().loadData();
        String nome = null;
        String autor = null;
        ArrayList<String> categorias = new ArrayList<>();
        double preco = 0;
        int quantidade = 0;
        boolean cadastrado = false;
        boolean interromperOperacao = false;

        while (!interromperOperacao){
            System.out.println("CASO DESEJE ENCERRAR A OPERAÇÃO, DIGITE SUA SENHA.");
            nome = Funcoes.pergunta("Título do livro (Não poderá ser editado futuramente): ").toLowerCase().trim();
            for (Livro livro : livros){
                while (livro.getNome().equals(nome)){
                    nome = Funcoes.pergunta("Este título já consta em nossa biblioteca.\nForneça outro título ou digite sua senha para encerrar a operação de cadastro: ");
                }
            }
            if (getSenha().equals(nome)){interromperOperacao = true; continue;}
            autor = Funcoes.pergunta("Autor do livro (Não poderá ser editado futuramente): ").toLowerCase().trim();
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
            quantidade = Funcoes.getInt("Quantidade de Livros: ");
            interromperOperacao = true;
            cadastrado = true;
        }

        if (cadastrado){
            Livro livro = new Livro(nome, autor, preco, categorias, quantidade);
            Access.getInstance().getDbBiblioteca().addItem(livro);
            System.out.println("Cadastro de livro " + livro.getNome() + " realizado com sucesso!");
        } else {
            System.out.println("Cadastro cancelado!");
        }
    }

    public void editLivro() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Livro> livros = Access.getInstance().getDbBiblioteca().loadData();
        String livroBusca = Funcoes.pergunta("Qual livro você gostaria de editar as informações: ").toLowerCase().trim();
        boolean encontrado = false;
        for (Livro livro : livros){
            if (livro.getNome().equals(livroBusca)){
                encontrado = true;
                String alteracao = Funcoes.perguntaComVerificacao("Qual alteração você gostaria de fazer ['preço', 'categoria', 'quantidade']: ", new ArrayList<>(Arrays.asList("preco", "categoria", "quantidade")));
                if (alteracao.equals("preço")){
                    System.out.println("Preço atual: R$" + livro.getPreco());
                    double novoPreco = Funcoes.getValorMonetario("Novo preço: ");
                    livro.changePreco(novoPreco);
                    System.out.println("Preço alterado com sucesso. Preço atual: R$" + livro.getPreco());
                } else if (alteracao.equals("categoria")){
                    System.out.println("Categorias atuais: " + String.join(" | ", livro.getCategoria()));
                    String addOrRemove = Funcoes.perguntaComVerificacao("Você deseja adicionar ou remover uma categoria['add' or 'rm']: ", new ArrayList<>(Arrays.asList("add", "rm")));
                    if (addOrRemove.equals("add")){
                        String novaCategoria = Funcoes.pergunta("Qual nova categoria você gostaria de adicionar: ");
                        livro.getCategoria().add(novaCategoria);
                        System.out.println("Categoria adicionada com sucesso.");
                    }else{
                        String rmCategoria = Funcoes.perguntaComVerificacao("Qual categoria você gostaria de remover: ", livro.getCategoria());
                        livro.getCategoria().remove(rmCategoria);
                        System.out.println("Categoria removida com sucesso");
                    }
                }else{
                    System.out.println("Quantidade de livros disponíveis em estoque: " + livro.getQuantidadeDisponivel());
                    int adicao = Funcoes.getInt("Digite o número de livros desse título que gostaria de adicionar ou remover: ");
                    if (adicao + livro.getQuantidadeDisponivel() >= 0){
                        if (adicao < 0){
                            livro.editQuantidadeTotal(adicao);
                            System.out.println("Quantidade removida: " + adicao * -1 + "\nQuantidade de livros disponíveis atual: " + livro.getQuantidadeDisponivel());
                        } else {
                            livro.editQuantidadeTotal(adicao);
                            System.out.println("Quantidade adicionada: " + adicao * -1 + "\nQuantidade de livros disponíveis atual: " + livro.getQuantidadeDisponivel());
                        }
                    }
                } Access.getInstance().getDbBiblioteca().updateItem(livro.getNome(), livro);
            }
        }
        if (!encontrado){
            System.out.println("O livro não foi encontrado no sistema.");
        }
    }

    public void rmLivro() throws IOException {
        List<Livro> livros = Access.getInstance().getDbBiblioteca().loadData();
        String livroBusca = Funcoes.pergunta("Qual livro você gostaria de remover do sistema: ").toLowerCase().trim();
        boolean encontrado = false;
        for (Livro livro : livros){
            if (livro.getNome().equals(livroBusca)){
                encontrado = true;
                String confirmacao = Funcoes.perguntaComVerificacao("Livro encontrado, confirme a operação com [y] ou encerre com [n]: ", new ArrayList<>(Arrays.asList("y", "n")));
                if (confirmacao.equals("n")){
                    System.out.println("Operação encerrada");
                } else {
                    if (livro.getQuantidadeTotal() == livro.getQuantidadeDisponivel()){
                        Access.getInstance().getDbBiblioteca().removeItem(livro.getNome());
                        System.out.println("Livro removido com sucesso.");
                    } else {
                        System.out.println("Não é possível retirar o livro do sistema pois ainda existem livros alugados com esse título.");
                    }
                }
            }
        }
        if (!encontrado){
            System.out.println("livro não encontrado em nosso sistema.");
        }
    }
}
