package br.ufrpe.universitynd.models;

/**
 * Created by air on 22/11/17.
 */

public class Duvida {
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
