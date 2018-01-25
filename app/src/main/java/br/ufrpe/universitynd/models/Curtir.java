package br.ufrpe.universitynd.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by AndreLucas on 24/01/2018.
 */

public class Curtir implements Serializable {
    private Date date;
    private Usuario usuario;

    public Curtir(Date date, Usuario usuario) {
        this.date = date;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDataFormatada(){
        return DateFormat.getInstance().format(this.date);
    }
}
