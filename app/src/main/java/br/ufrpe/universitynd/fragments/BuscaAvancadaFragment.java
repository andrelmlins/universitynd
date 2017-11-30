package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;


/**
 * Created by Tamires on 25/11/2017.
 */

public class BuscaAvancadaFragment extends Fragment implements  RecyclerViewOnClickListenerHack {
    private View rootView;
    private Button btnBuscar, btnLimpar;
    private AdapterDuvidas adapter;
    private List<Duvida> duvidas;
    private RecyclerView myRecyclerView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState) {

        this.rootView = inflater.inflate(R.layout.busca_avancada_fragment, container, false);
        getActivity().setTitle(R.string.busca_avancada);

        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaDuvidas);

        this.myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lls = new LinearLayoutManager(getActivity());
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);


        this.duvidas = new ArrayList<Duvida>();
        duvidas.add(new Duvida("Nome", new Date(),"Pergunta 1"));
        duvidas.add(new Duvida("Nome", new Date(),"Pergunta 2"));

        this.adapter = new AdapterDuvidas(getActivity(), duvidas);
        this.adapter.setRecyclerViewOnClickListenerHack(this);

        this.myRecyclerView.setAdapter(this.adapter);

        this.btnBuscar = (Button) this.rootView.findViewById(R.id.btnBuscar);
        this.btnLimpar = (Button) this.rootView.findViewById(R.id.btnLimpar);

        BuscaAvancadaFragment.this.myRecyclerView.setVisibility(View.GONE);
        this.btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscaAvancadaFragment.this.myRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        this.btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscaAvancadaFragment.this.myRecyclerView.setVisibility(View.GONE);
            }
        });
        return this.rootView;
    }

    @Override
    public void onClickListener(View view, int position) {
            Duvida duvida = this.duvidas.get(position);
            DuvidaFragment fragment = new DuvidaFragment();
            Bundle b = new Bundle();
            b.putSerializable("duvida",duvida);
            fragment.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
    }


}
