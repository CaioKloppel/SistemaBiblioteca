package org.example.util;

import org.example.jsonData.JsonDatabase;
import org.example.livro.Livro;
import org.example.pessoas.Diretor;
import org.example.pessoas.Funcionario;
import org.example.pessoas.Usuario;

import java.util.Scanner;

public final class Access {
    private volatile static Access access;
    private final Scanner scanner;
    private final JsonDatabase<Livro> dbBiblioteca;
    private final JsonDatabase<Usuario> dbUsuarios;
    private final JsonDatabase<Funcionario> dbFuncionarios;
    private final JsonDatabase<Diretor> dbDiretor;

    private Access(){
        scanner = new Scanner(System.in);
        dbBiblioteca = new JsonDatabase<>("biblioteca.json", Livro.class);
        dbUsuarios = new JsonDatabase<>("usuarios.json", Usuario.class);
        dbFuncionarios = new JsonDatabase<>("funcionarios.json", Funcionario.class);
        dbDiretor = new JsonDatabase<>("diretor.json", Diretor.class);
    }

    public static Access getInstance(){
        if (access == null){
            synchronized (Access.class){
                if (access == null) {
                    access = new Access();
                }
            }
        }
        return access;
    }

    public Scanner getScanner(){return scanner;}
    public JsonDatabase<Livro> getDbBiblioteca() {return dbBiblioteca;}
    public JsonDatabase<Usuario> getDbUsuarios() {return dbUsuarios;}
    public JsonDatabase<Funcionario> getDbFuncionarios() {return dbFuncionarios;}
}