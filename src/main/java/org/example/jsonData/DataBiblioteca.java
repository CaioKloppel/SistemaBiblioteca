package org.example.jsonData;

import org.example.livro.Livro;

public final class DataBiblioteca {
    private volatile static DataBiblioteca dataBiblioteca;
    private final JsonDatabase<Livro> dbBiblioteca = new JsonDatabase<>("biblioteca.json", Livro.class);

    private DataBiblioteca(){}

    public static DataBiblioteca getInstance(){
        if (dataBiblioteca == null){
            synchronized (DataBiblioteca.class){
                if (dataBiblioteca == null) {
                    dataBiblioteca = new DataBiblioteca();
                }
            }
        }
        return dataBiblioteca;
    }

    public JsonDatabase<Livro> getDbBiblioteca() {
        return dbBiblioteca;
    }
}
