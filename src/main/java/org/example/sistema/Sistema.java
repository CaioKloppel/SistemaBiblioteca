package org.example.sistema;

import org.example.pessoas.Diretor;
import org.example.pessoas.Funcionario;
import org.example.pessoas.Pessoa;
import org.example.pessoas.Usuario;
import org.example.util.Access;
import org.example.util.Funcoes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Sistema {
    public Pessoa loginSistema() throws IOException {
        Pessoa pessoa;
        do{
            String createOrLog = Funcoes.perguntaComVerificacao("Criar usuário / Entrar na conta!\n[criar, entrar]: ", new ArrayList<>(Arrays.asList("criar", "entrar")));
            if (createOrLog.equals("criar")){
                pessoa = Login.newUsuario();
            } else {
                String nickOrEmail = Funcoes.pergunta("Entre com o seu email ou nick de acesso: ");
                String senha = Funcoes.pergunta("Digite sua senha: ");
                pessoa = Login.autenticar(nickOrEmail, senha);
            }
        } while (pessoa == null);

        return pessoa;
    }

    public void sistemaDiretor(Diretor diretor) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        boolean encerrarPrograma = false;
        do {
            System.out.println("\nO QUE VOCÊ DESEJA FAZER?");
            String desejo = Funcoes.perguntaComVerificacao("[consultar livro, adicionar livro, editar livro, remover livro, adicionar funcionário, excluir funcionário, excluir usuário, editar conta, fechar programa]: ", new ArrayList<>(Arrays.asList("consultar livro", "adicionar livro", "editar livro", "remover livro", "adicionar funcionário", "excluir funcionário", "excluir usuário", "editar conta", "fechar programa")));
            switch (desejo) {
                case "fechar programa" -> encerrarPrograma = diretor.encerrarPrograma();
                case "consultar livro" -> diretor.consultarLivro();
                case "adicionar livro" -> diretor.addLivro();
                case "editar livro" -> diretor.editLivro();
                case "remover livro" -> diretor.rmLivro();
                case "adicionar funcionário" -> diretor.addFuncionario();
                case "excluir funcionário" -> diretor.excludeFuncionario();
                case "excluir usuário" -> diretor.excludeUsuario();
                case "editar conta" -> {
                    boolean finalizar = false;
                    do {
                      String desejoEdicao = Funcoes.perguntaComVerificacao("O QUE VOCÊ DESEJA EDITAR?\n[nickAcesso, email, senha, encerrar]: ", new ArrayList<>(Arrays.asList("nickAcesso", "email", "senha", "encerrar")));
                      switch (desejoEdicao){
                          case "nickAcesso" -> diretor.changeNickAcesso();
                          case "email" -> diretor.changeEmail();
                          case "senha" -> diretor.changeSenha();
                          case "encerrar" -> {
                              Access.getInstance().getDbDiretor().updateItem(diretor.getNome(), diretor);
                              finalizar = true;
                          }
                      }
                    } while (!finalizar);
                }
            }
        } while (!encerrarPrograma);
    }

    public void sistemaFuncionario(Funcionario funcionario) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        boolean encerrarPrograma = false;
        do {
            System.out.println("\nO QUE VOCÊ DESEJA FAZER?");
            String desejo = Funcoes.perguntaComVerificacao("[consultar livro, adicionar livro, editar livro, remover livro, editar conta, fechar programa]: ", new ArrayList<>(Arrays.asList("consultar livro", "adicionar livro", "editar livro", "remover livro", "editar conta", "fechar programa")));
            switch (desejo) {
                case "fechar programa" -> encerrarPrograma = funcionario.encerrarPrograma();
                case "consultar livro" -> funcionario.consultarLivro();
                case "adicionar livro" -> funcionario.addLivro();
                case "editar livro" -> funcionario.editLivro();
                case "remover livro" -> funcionario.rmLivro();
                case "editar conta" -> {
                    boolean finalizar = false;
                    do {
                        String desejoEdicao = Funcoes.perguntaComVerificacao("O QUE VOCÊ DESEJA EDITAR?\n[nickAcesso, email, senha, encerrar]: ", new ArrayList<>(Arrays.asList("nickAcesso", "email", "senha", "encerrar")));
                        switch (desejoEdicao){
                            case "nickAcesso" -> funcionario.changeNickAcesso();
                            case "email" -> funcionario.changeEmail();
                            case "senha" -> funcionario.changeSenha();
                            case "encerrar" -> {
                                Access.getInstance().getDbFuncionarios().updateItem(funcionario.getNome(), funcionario);
                                finalizar = true;
                            }
                        }
                    } while (!finalizar);
                }
            }
        } while (!encerrarPrograma);
    }

    public void sistemaUsuario(Usuario usuario) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        boolean encerrarPrograma = false;
        do {
            System.out.println("\nO QUE VOCÊ DESEJA FAZER?");
            String desejo = Funcoes.perguntaComVerificacao("[consultar livro, alugar livro, devolver livro, adicionar saldo, verificar saldo, verificar livros alugados, editar conta, fechar programa]: ", new ArrayList<>(Arrays.asList("consultar livro", "alugar livro", "devolver livro", "adicionar saldo", "verificar saldo", "verificar livros alugados", "editar conta", "fechar programa")));
            switch (desejo){
                case "fechar programa" -> {
                    Access.getInstance().getDbUsuarios().updateItem(usuario.getNome(), usuario);
                    encerrarPrograma = usuario.encerrarPrograma();
                }
                case "consultar livro" -> usuario.consultarLivro();
                case "alugar livro" -> usuario.alugarLivro();
                case "devolver livro" -> usuario.devolverLivro();
                case "adicionar saldo" -> usuario.addSaldo();
                case "verificar saldo" -> usuario.consultarSaldo();
                case "verificar livros alugados" -> usuario.consultarLivrosAlugados();
                case "editar conta" -> {
                    boolean finalizar = false;
                    do {
                        String desejoEdicao = Funcoes.perguntaComVerificacao("O QUE VOCÊ DESEJA EDITAR?\n[nickAcesso, email, senha, encerrar]: ", new ArrayList<>(Arrays.asList("nickAcesso", "email", "senha", "encerrar")));
                        switch (desejoEdicao) {
                            case "nickAcesso" -> usuario.changeNickAcesso();
                            case "email" -> usuario.changeEmail();
                            case "senha" -> usuario.changeSenha();
                            case "encerrar" -> {
                                Access.getInstance().getDbUsuarios().updateItem(usuario.getNome(), usuario);
                                finalizar = true;
                            }
                        }
                    } while (!finalizar);
                }
            }
        } while (!encerrarPrograma);
    }
}
