package br.ufrpe.universitynd.models;

import java.io.Serializable;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class Usuario implements Serializable{
    public String nomeCompleto;
    public String foto;
    public String token;
    public Integer respostas;

    public Usuario(String nomeCompleto, String foto, String token) {
        this.nomeCompleto = nomeCompleto;
        this.foto = foto;
        this.token = token;
    }

    public Usuario(String nomeCompleto, String foto, Integer respostas) {
        this.nomeCompleto = nomeCompleto;
        this.foto = foto;
        this.respostas = respostas;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getRespostas() {
        return respostas;
    }

    public void setRespostas(Integer respostas) {
        this.respostas = respostas;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
