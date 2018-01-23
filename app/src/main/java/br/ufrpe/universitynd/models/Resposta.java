package br.ufrpe.universitynd.models;

import java.io.Serializable;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class Resposta implements Serializable {
    private String conteudo;
    private Usuario usuario;

    public Resposta(String conteudo, Usuario usuario) {
        this.conteudo = conteudo;
        this.usuario = usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
