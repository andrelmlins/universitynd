package br.ufrpe.universitynd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.Adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by air on 22/11/17.
 */

public class DuvidasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duvidas_activity);
        ListView lista = (ListView) findViewById(R.id.listaDuvidas);

        List<Duvida> ld = new ArrayList<Duvida>();
        ld.add(new Duvida("texto 1"));
        ld.add(new Duvida("texto 1"));
        ld.add(new Duvida("texto 1"));
        ld.add(new Duvida("texto 1"));
        ld.add(new Duvida("texto 1"));

        AdapterDuvidas adapter = new AdapterDuvidas(ld, this);

        lista.setAdapter(adapter);

    }
}