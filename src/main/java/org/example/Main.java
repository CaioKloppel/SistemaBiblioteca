package org.example;

import org.example.pessoas.Diretor;
import org.example.pessoas.Funcionario;
import org.example.pessoas.Pessoa;
import org.example.pessoas.Usuario;
import org.example.sistema.Sistema;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Sistema sistema = new Sistema();
        Pessoa pessoa = sistema.loginSistema();
        if (pessoa instanceof Diretor diretor){
            sistema.sistemaDiretor(diretor);
        } else if (pessoa instanceof Funcionario funcionario){
            sistema.sistemaFuncionario(funcionario);
        } else if (pessoa instanceof Usuario usuario){
            sistema.sistemaUsuario(usuario);
        }
    }
}