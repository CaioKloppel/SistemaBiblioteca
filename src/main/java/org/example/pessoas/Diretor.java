package org.example.pessoas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Diretor extends Funcionario{

    public Diretor(@JsonProperty("nome") String nome,
                   @JsonProperty("senha") String senha,
                   @JsonProperty("nickAcesso") String nick) {
        super(nome, senha, nick);
    }



}
