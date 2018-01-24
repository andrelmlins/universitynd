package br.ufrpe.universitynd.models;

import java.io.Serializable;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class Usuario implements Serializable{
    public String nomeCompleto;
    public String foto;
    public String token;
    public Integer pontuacao;

    public Usuario(String nomeCompleto, String foto, String token) {
        this.nomeCompleto = nomeCompleto;
        this.foto = foto;
        this.token = token;
    }

    public Usuario(String nomeCompleto, String foto, Integer pontuacao) {
        this.nomeCompleto = nomeCompleto;
        this.foto = foto;
        this.pontuacao = pontuacao;
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

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
