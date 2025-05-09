package org.example.util;

import java.util.Scanner;

public final class Input {
    private final Scanner scanner = new Scanner(System.in);
    private volatile static Input input;

    private Input(){}

    public static Input getInstance(){
        if (input == null){
            synchronized (Input.class){
                if (input == null) {
                    input = new Input();
                }
            }
        }
        return input;
    }

    public String scanNextLine() {
        return scanner.nextLine();
    }
    public int scanNextInt() { return scanner.nextInt(); }
    public double scanNextDouble() {return scanner.nextDouble();}
    public void closeScan() { scanner.close(); }
}