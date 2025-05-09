package org.example.util;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;

public class Funcoes {
    private static final Random random = new Random();

    public static int getInt(String pergunta)
    {
        int numeroInt = 0;
        boolean entrada_valida = false;
        while (!entrada_valida){
            System.out.print(pergunta);
            try {
                numeroInt = Input.getInstance().scanNextInt();
                Input.getInstance().scanNextLine();
                entrada_valida = true;
            } catch (InputMismatchException e){
                System.out.println("Entrada inválida, aceita apenas números inteiros!");
                Input.getInstance().scanNextLine();
            }
        }

        return numeroInt;
    }
    public static double getValorMonetario(String pergunta)
    {
        double numeroDouble = 0;
        boolean entrada_valida = false;
        while (!entrada_valida){
            System.out.print(pergunta);
            try {
                numeroDouble = Input.getInstance().scanNextDouble();
                Input.getInstance().scanNextLine();
                if (numeroDouble < 0){
                    System.out.println("Entrada inválida, aceita apenas números positivos!");
                } else entrada_valida = true;
            } catch (InputMismatchException e){
                System.out.println("Entrada inválida, aceita apenas números positivos!");
                Input.getInstance().scanNextLine();
            }
        }

        return numeroDouble;
    }
    public static String perguntaComVerificacao(String pergunta, ArrayList<String> verificao){
        System.out.print(pergunta);
        String resposta = Input.getInstance().scanNextLine();
        while (!verificao.contains(resposta)){
            System.out.println("Resposta incorreta, aceita somente as respostas indicadas.");
            System.out.print(pergunta);
            resposta = Input.getInstance().scanNextLine();
        }
        return resposta;
    }
    public static String pergunta(String pergunta){
        System.out.print(pergunta);
        return Input.getInstance().scanNextLine();
    }
}
