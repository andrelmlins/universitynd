package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;


/**
 * Created by Tamires on 25/11/2017.
 */

public class BuscaAvancadaFragment extends Fragment implements  AdapterView.OnItemClickListener{
    private View rootView;
    private Button btnBuscar, btnLimpar;
    private AdapterDuvidas adapter;
    private List<Duvida> duvidas;
    private ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState) {
        this.rootView = inflater.inflate(R.layout.busca_avancada_fragment, container, false);
        getActivity().setTitle("Busca Avançada");

        this.listView = (ListView) this.rootView.findViewById(R.id.lista);

        this.duvidas = new ArrayList<Duvida>();
        duvidas.add(new Duvida("Pergunta 1"));

        this.adapter = new AdapterDuvidas(duvidas, getActivity());
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(this);


        this.btnBuscar = (Button) this.rootView.findViewById(R.id.btnBuscar);
        this.btnLimpar = (Button) this.rootView.findViewById(R.id.btnLimpar);

        BuscaAvancadaFragment.this.listView.setVisibility(View.GONE);
        this.btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscaAvancadaFragment.this.listView.setVisibility(View.VISIBLE);
            }
        });
        this.btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscaAvancadaFragment.this.listView.setVisibility(View.GONE);
            }
        });
        return this.rootView;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Duvida duvida = this.duvidas.get(position);
        DuvidaFragment fragment = new DuvidaFragment();
        Bundle b = new Bundle();
        b.putSerializable("duvida",duvida);
        fragment.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
    }

}
