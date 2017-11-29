package br.ufrpe.universitynd.models;

import java.io.Serializable;

/**
 * Created by air on 22/11/17.
 */

public class Duvida implements Serializable {
    private String texto;
    private String titulo;
    private String interessado;
    private String[] disciplinas;
    private String assunto;

    public Duvida(String texto){
        this.texto =texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInteressado() {
        return interessado;
    }

    public void setInteressado(String interessado) {
        this.interessado = interessado;
    }

    public String[] getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(String[] disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }
}
