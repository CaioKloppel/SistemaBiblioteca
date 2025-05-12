package org.example.sistema;

import org.example.pessoas.Diretor;
import org.example.pessoas.Funcionario;
import org.example.pessoas.Pessoa;
import org.example.pessoas.Usuario;
import org.example.util.Access;
import org.example.util.Funcoes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login {
    static Pessoa autenticar(String nickOrEmail, String senha) throws IOException {
        List<Diretor> diretores = Access.getInstance().getDbDiretor().loadData();
        List<Funcionario> funcionarios = Access.getInstance().getDbFuncionarios().loadData();
        List<Usuario> usuarios = Access.getInstance().getDbUsuarios().loadData();

        for (Diretor diretor : diretores){
            if (diretor.getSenha().equals(senha) && (diretor.getEmail().equals(nickOrEmail) || diretor.getNickAcesso().equals(nickOrEmail))){
                System.out.println("\nLOGIN REALIZADO COM SUCESSO, BEM-VINDO DIRETOR " + diretor.getNome() + "!");
                return diretor;
            }
        }
        for (Funcionario funcionario : funcionarios){
            if (funcionario.getSenha().equals(senha) && (funcionario.getEmail().equals(nickOrEmail) || funcionario.getNickAcesso().equals(nickOrEmail))){
                System.out.println("\nLOGIN REALIZADO COM SUCESSO, BEM-VINDO FUNCIONÁRIO(A) " + funcionario.getNome() + "!");
                return funcionario;
            }
        }
        for (Usuario usuario : usuarios){
            if (usuario.getSenha().equals(senha) && (usuario.getEmail().equals(nickOrEmail) || usuario.getNickAcesso().equals(nickOrEmail))){
                System.out.println("\nLOGIN REALIZADO COM SUCESSO, BEM-VINDO " + usuario.getNome() + "!");
                return usuario;
            }
        }

        System.out.println("Email/nick ou senha incorretos. Tente novamente.");

        return null;
    }

    static Usuario newUsuario() throws IOException {
        String nome = null;
        String nickAcesso = null;
        String email = null;
        String senha = null;
        double saldo = 0;
        ArrayList<String> livrosAlugados = new ArrayList<>();
        boolean cadastrado = false;
        boolean interromperOperacao = false;

        while (!interromperOperacao){
            System.out.println("CASO DESEJE ENCERRAR O CADASTRO, DIGITE STOP;");
            nome = Funcoes.pergunta("Nome completo (Não podera ser alterado futuramente): ").toLowerCase().trim();
            if (nome.equals("stop")){interromperOperacao = true; continue;}
            nickAcesso = Funcoes.pergunta("Nick de acesso: ");
            if (nickAcesso.equals("stop") || nickAcesso.equals("STOP")){interromperOperacao = true; continue;}
            email = Funcoes.pergunta("Email: ");
            if (email.equals("stop") || email.equals("STOP")){interromperOperacao = true; continue;}
            senha = Funcoes.pergunta("Senha: ");
            if (senha.equals("stop") || senha.equals("STOP")){interromperOperacao = true; continue;}
            saldo = Funcoes.getValorMonetario("Saldo inicial (Poderá adicionar saldo futuramente): ");
            cadastrado = true;
            interromperOperacao = true;
        }
        if (!cadastrado){
            System.out.println("CADASTRO ENCERRADO.");
            return null;
        } else {
            Usuario usuario = new Usuario(nome, senha, nickAcesso, email, saldo, livrosAlugados);
            Access.getInstance().getDbUsuarios().addItem(usuario);
            return usuario;
        }
    }
}
