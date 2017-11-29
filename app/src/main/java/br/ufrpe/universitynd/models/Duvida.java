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
    public  String getDataFormatada(Date data){
        Date dataFormatada = data;
        return DateFormat.getInstance().format(dataFormatada);

    }


}
