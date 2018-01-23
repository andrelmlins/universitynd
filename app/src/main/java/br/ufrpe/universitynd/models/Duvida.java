package br.ufrpe.universitynd.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Danielly Queiroz on 22/11/17.
 */

public class Duvida implements Serializable {
    private String id;
    private String nome;
    private Date data;
    private String conteudo;
    private String interessado;
    private String[] disciplinas;
    private String assunto;
    private Usuario usuario;

    public Duvida(String id, String nome, Date data, String conteudo, String interessado, String[] disciplinas, String assunto, Usuario usuario) {
        this.nome = nome;
        this.data = data;
        this.conteudo = conteudo;
        this.interessado = interessado;
        this.disciplinas = disciplinas;
        this.assunto = assunto;
        this.usuario = usuario;
        this.id = id;
    }

    public Duvida(String nome, Date data, String conteudo){
        this.nome = nome;
        this.data = data;
        this.conteudo = conteudo;
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

    public String[] getColor(){
        if(this.assunto.equals("Disciplina") || this.assunto.equals("Discipline")){
            return new String[]{"#3f51b5", "#3949ab"};
        } else if(this.assunto.equals("Dispensa de Disciplina") || this.assunto.equals("Exemption from Discipline")){
            return new String[]{"#009688", "#00897b"};
        } else if(this.assunto.equals("Estágio") || this.assunto.equals("Intership")){
            return new String[]{"#673ab7", "#5e35b1"};
        } else if(this.assunto.equals("Matrícula") || this.assunto.equals("Registration")){
            return new String[]{"#795548", "#6d4c41"};
        } else if(this.assunto.equals("SECOMP")){
            return new String[]{"#4caf50", "#43a047"};
        } else {
            return new String[]{"#ca2129", "#a11a20"};
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
