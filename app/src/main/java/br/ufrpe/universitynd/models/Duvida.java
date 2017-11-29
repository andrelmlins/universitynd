package br.ufrpe.universitynd.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by air on 22/11/17.
 */

public class Duvida extends Date implements Serializable {
    private String nome;
    private Date data;
    private String texto;
    private String titulo;
    private String interessado;
    private String[] disciplinas;
    private String assunto;

    public Duvida(String nome, Date data, String texto){
        this.nome = nome;
        this.data = data;
        this.texto =texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public String getTexto() {
        return texto;
    }
    public String getDataFormatada(Date data){
        Date dataFormatada = data;
        return DateFormat.getInstance().format(dataFormatada);

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
