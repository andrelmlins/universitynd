package br.ufrpe.universitynd;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.Adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by air on 22/11/17.
 */

public class DuvidasActivity extends ListActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duvidas_activity);

        ListView lista = (ListView) findViewById(R.id.listaDuvidas);

        List<Duvida> ld = new ArrayList<Duvida>();
        ld.add(new Duvida("Pergunta 1"));
        ld.add(new Duvida("Pergunta 1"));
        ld.add(new Duvida("Pergunta 1"));
        ld.add(new Duvida("Pergunta 1"));
        ld.add(new Duvida("Pergunta 1"));

        AdapterDuvidas adapter = new AdapterDuvidas(ld, this);

//        lista.setAdapter(adapter);
//
//        ListView lv = getListView();
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // selected item
//                String product = ((TextView) view).getText().toString();
//
//                // Launching new Activity on selecting single List Item
//                Intent in = new Intent(getApplicationContext(), DuvidasFormularioActivity.class);
//                // sending data to new activity
//                in.putExtra("product", product);
//                startActivity(in);
//
//            }
//        });
//
//        // listening to single list item on click
//
//
//
            }



}