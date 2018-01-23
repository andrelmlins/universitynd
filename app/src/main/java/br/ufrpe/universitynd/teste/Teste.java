package br.ufrpe.universitynd.teste;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;

import br.ufrpe.universitynd.LoginActivity;
import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by air on 22/01/18.
 */

public class Teste {

    private static ArrayList<Duvida> duvidas = new ArrayList<Duvida>();

    private static void initDuvidas(){
        String[] dis = {"dis1","dis2","dis3"};
        /* duvidas.add(new Duvida("nome1", new Date(), "conteudo1", "interessado1",dis, "assunto1"));
        duvidas.add(new Duvida("nome2", new Date(), "conteudo2", "interessado2",dis, "assunto2"));
        duvidas.add(new Duvida("nome3", new Date(), "conteudo3", "interessado3",dis, "assunto3")); */
    }

    public static void addDuv(Duvida duvida){
        duvidas.add(duvida);

    }

    public static void login(LoginActivity act, ProgressDialog progress){
        initDuvidas();
        SharedPreferences settings = act.getSharedPreferences("usuario", 0);
        SharedPreferences.Editor edit = settings.edit();
        try {
            edit.putString("username", "userteste");
            edit.putString("fullname", "fullnameteste");
            edit.putString("token", "123");
            edit.putString("email", "teste@gmail.com");
            edit.putString("departament", "departamentoteste");
            edit.putString("picture", "pictureteste");
        } catch (Exception e) {
            e.printStackTrace();
        }
        edit.commit();
        if (progress != null) progress.dismiss();
        Intent i = new Intent(act, Main.class);
        act.startActivity(i);
        act.finish();
    }
}
