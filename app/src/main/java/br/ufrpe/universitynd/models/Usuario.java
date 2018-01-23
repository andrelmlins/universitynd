package br.ufrpe.universitynd.models;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class Usuario {
    public String nomeCompleto;
    public String foto;
    public int respostas;

    public Usuario(String nomeCompleto, String foto) {
        this.nomeCompleto = nomeCompleto;
        this.foto = foto;
    }

    public Usuario(String nomeCompleto, String foto, int respostas) {
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

    public int getRespostas() {
        return respostas;
    }

    public void setRespostas(int respostas) {
        this.respostas = respostas;
    }
}
