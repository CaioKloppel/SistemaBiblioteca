package org.example;

import org.example.pessoas.Funcionario;
import org.example.pessoas.Usuario;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Funcionario funcionario = new Funcionario("Caio", "8210", "CaioK9");
        Usuario usuario = new Usuario("Caio", "000", "CaioK9", "caio@email", 100);
        usuario.alugarLivro();
    }
}