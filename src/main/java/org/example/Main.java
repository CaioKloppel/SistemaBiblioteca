package org.example;

import org.example.pessoas.Diretor;
import org.example.pessoas.Funcionario;
import org.example.pessoas.Usuario;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Diretor diretor = new Diretor("Caio", "8210", "CaioK9", "caio@");
        diretor.excludeFuncionario();
    }
}