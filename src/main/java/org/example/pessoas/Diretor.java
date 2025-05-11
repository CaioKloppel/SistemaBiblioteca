package org.example.pessoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.livro.Livro;
import org.example.util.Access;
import org.example.util.Funcoes;

import java.io.IOException;
import java.util.List;

public class Diretor extends Funcionario{

    public Diretor(@JsonProperty("nome") String nome,
                   @JsonProperty("senha") String senha,
                   @JsonProperty("nickAcesso") String nick,
                   @JsonProperty("email") String email) {
        super(nome, senha, nick, email);
    }

    public void addFuncionario() throws IOException {
        List<Funcionario> funcionarios = Access.getInstance().getDbFuncionarios().loadData();
        String nome = null;
        String email = null;
        String senha = null;
        String nick = null;
        boolean cadastrado = false;
        boolean interromperOperacao = false;

        while (!interromperOperacao){
            System.out.println("CASO DESEJE ENCERRAR A OPERAÇÃO, DIGITE SUA SENHA.");
            nome = Funcoes.pergunta("Nome completo do novo funcionário: ").toLowerCase().trim();
            if (getSenha().equals(nome)){interromperOperacao = true; continue;}
            email = Funcoes.pergunta("Email do novo funcionário: ").toLowerCase().trim();
            for (Funcionario funcionario : funcionarios){
                while (funcionario.getEmail().equals(email)){
                    email = Funcoes.pergunta("Este email já está cadastrado no sistema.\nForneça outro email ou digite sua senha para encerrar a operação de cadastro: ");
                }
            }
            if (getSenha().equals(email)){interromperOperacao = true; continue;}
            nick = Funcoes.pergunta("Digite a senha do usuário: ");
            for (Funcionario funcionario : funcionarios){
                while (funcionario.getNickAcesso().equals(nick)){
                    nick = Funcoes.pergunta("Este nick já está cadastrado no sistema.\nForneça outro nick ou digite sua senha para encerrar a operação de cadastro: ");
                }
            }
            if (getSenha().equals(nick)){interromperOperacao = true; continue;}
            senha = Funcoes.pergunta("Informe a senha do novo usuário: ");
            cadastrado = true;
            interromperOperacao = true;
        }

        if (cadastrado){
            Funcionario funcionario = new Funcionario(nome, senha, nick, email);
            Access.getInstance().getDbFuncionarios().addItem(funcionario);
            System.out.println("Funcionário cadastrado com sucesso.");
        } else {
            System.out.println("Operação encerrada.");
        }
    }

    public void excludeFuncionario() throws IOException {
        List<Funcionario> funcionarios = Access.getInstance().getDbFuncionarios().loadData();
        String nick = Funcoes.pergunta("Informe o nick do funcionário que você gostaria de excluir: ");
        boolean encontrado = false;
        for (Funcionario funcionario : funcionarios){
            if (funcionario.getNickAcesso().equals(nick)){
                encontrado = true;
                String senha = Funcoes.pergunta("Para confirmar a exclusão do funcionário do sistema, digite sua senha: ");
                if (senha.equals(getSenha())){
                    Access.getInstance().getDbFuncionarios().removeItem(funcionario.getNome());
                    System.out.println("Funcionário removido do sistema.");
                } else {
                    System.out.println("Senha incorreta, encerando operação.");
                }
            }
        }
        if (!encontrado){
            System.out.println("Funcionário não encontrado no sistema.");
        }
    }

    public void excludeUsuario() throws IOException {
        List<Usuario> usuarios = Access.getInstance().getDbUsuarios().loadData();
        String nick = Funcoes.pergunta("Informe o nick do usuário que você gostaria de excluir: ");
        boolean encontrado = false;
        for (Usuario usuario : usuarios){
            if (usuario.getNickAcesso().equals(nick)){
                encontrado = true;
                String senha = Funcoes.pergunta("Para confirmar a exclusão do usuário do sistema, digite sua senha: ");
                if (senha.equals(getSenha())){
                    Access.getInstance().getDbFuncionarios().removeItem(usuario.getNome());
                    System.out.println("Usuário removido do sistema.");
                } else {
                    System.out.println("Senha incorreta, encerando operação.");
                }
            }
        }
        if (!encontrado){
            System.out.println("Usuário não encontrado no sistema.");
        }
    }
}
