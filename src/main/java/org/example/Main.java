package org.example;

import org.example.pessoas.Funcionario;
import org.example.pessoas.Usuario;

public class Main {
    public static void main(String[] args){
        Funcionario funcionario = new Funcionario("Caio", "8210", "CaioK9");
        Usuario usuario = new Usuario("Caio", "000", "CaioK9", "caio@email", 100);
        usuario.consultarSaldo();
    }
}