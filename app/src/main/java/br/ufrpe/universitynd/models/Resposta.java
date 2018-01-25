package br.ufrpe.universitynd.models;

import java.io.Serializable;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class Resposta implements Serializable {
    private String conteudo;
    private Usuario usuario;
    private String id;

    public Resposta(String conteudo, Usuario usuario, String id) {
        this.conteudo = conteudo;
        this.usuario = usuario;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
