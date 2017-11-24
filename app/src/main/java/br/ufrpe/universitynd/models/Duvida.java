package br.ufrpe.universitynd.models;

import java.io.Serializable;

/**
 * Created by air on 22/11/17.
 */

public class Duvida implements Serializable {
    private String texto;

    public Duvida(String texto){
        this.texto =texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
