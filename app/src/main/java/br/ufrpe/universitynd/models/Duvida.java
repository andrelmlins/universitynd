package br.ufrpe.universitynd.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Danielly Queiroz on 22/11/17.
 */

public class Duvida implements Serializable {
    private String nome;
    private Date data;
    private String conteudo;
    private String interessado;
    private String[] disciplinas;
    private String assunto;

    public Duvida(String nome, Date data, String conteudo, String interessado, String[] disciplinas, String assunto) {
        this.nome = nome;
        this.data = data;
        this.conteudo = conteudo;
        this.interessado = interessado;
        this.disciplinas = disciplinas;
        this.assunto = assunto;
    }

    public Duvida(String nome, Date data, String texto){
        this.nome = nome;
        this.data = data;
        this.conteudo =conteudo;
    }


    public void setConteudo(String conteudo) {
        this.conteudo= conteudo;
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

    public String getConteudo() {
        return conteudo;
    }
    public String getDataFormatada(Date data){
        Date dataFormatada = data;
        return DateFormat.getInstance().format(dataFormatada);

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
