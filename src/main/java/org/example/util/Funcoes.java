package org.example.util;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Funcoes {
    public static int getInt(String pergunta)
    {
        int numeroInt = 0;
        boolean entrada_valida = false;
        while (!entrada_valida){
            System.out.print(pergunta);
            try {
                numeroInt = Access.getInstance().getScanner().nextInt();
                Access.getInstance().getScanner().nextLine();
                entrada_valida = true;
            } catch (InputMismatchException e){
                System.out.println("Entrada inválida, aceita apenas números inteiros!");
                Access.getInstance().getScanner().nextLine();
            }
        }
        return numeroInt;
    }
    public static double getValorMonetario(String pergunta)
    {
        double numeroDouble = 0;
        boolean entradaValida = false;

        do {
            System.out.print(pergunta);
            try {
                numeroDouble = Access.getInstance().getScanner().nextDouble();
                Access.getInstance().getScanner().nextLine(); // consome o resto da linha
                if (numeroDouble < 0) {
                    System.out.println("Entrada inválida, aceita apenas números positivos!");
                } else {
                    entradaValida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida, formato correto [10,99].");
                Access.getInstance().getScanner().nextLine();
            }
        } while (!entradaValida);

        return numeroDouble;
    }
    public static String perguntaComVerificacao(String pergunta, ArrayList<String> verificao){
        System.out.print(pergunta);
        String resposta = Access.getInstance().getScanner().nextLine();
        while (!verificao.contains(resposta)){
            System.out.println("Resposta incorreta, aceita somente as respostas indicadas.");
            System.out.print(pergunta);
            resposta = Access.getInstance().getScanner().nextLine();
        }
        return resposta;
    }
    public static String pergunta(String pergunta){
        System.out.print(pergunta);
        return Access.getInstance().getScanner().nextLine();
    }
}
