package br.ufrpe.universitynd.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.Adapters.AdapterDuvidas;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by AndreLucas on 24/11/2017.
 */

public class DuvidasFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View rootView;
    private ListView listView;
    private AdapterDuvidas adapter;
    private List<Duvida> duvidas;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvidas_fragment,container,false);
        getActivity().setTitle("Últimas Dúvidas");
        this.listView = (ListView) this.rootView.findViewById(R.id.listaDuvidas);

        this.duvidas = new ArrayList<Duvida>();
        duvidas.add(new Duvida("Pergunta 1"));
        duvidas.add(new Duvida("Pergunta 2"));
        duvidas.add(new Duvida("Pergunta 3"));
        duvidas.add(new Duvida("Pergunta 4"));
        duvidas.add(new Duvida("Pergunta 5"));

        this.adapter = new AdapterDuvidas(duvidas, getActivity());
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(this);

        return this.rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Duvida duvida = this.duvidas.get(position);
        DuvidaFragment fragment = new DuvidaFragment();
        Bundle b = new Bundle();
        b.putSerializable("duvida",duvida);
        fragment.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
    }
}
