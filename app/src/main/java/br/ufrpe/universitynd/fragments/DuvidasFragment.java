package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class DuvidasFragment extends Fragment implements RecyclerViewOnClickListenerHack {
    private View rootView;
    private RecyclerView myRecyclerView;
    private AdapterDuvidas adapter;
    private List<Duvida> duvidas;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){

        this.rootView = inflater.inflate(R.layout.duvidas_fragment,container,false);
        getActivity().setTitle(R.string.ultDuvias);
        ((Main)getActivity()).setColor();

        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaDuvidas);
        this.myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lls = new LinearLayoutManager(getActivity());
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);

        this.duvidas = new ArrayList<Duvida>();
        duvidas.add(new Duvida("Danielly",new Date(),"Lorem ipsum integer aptent commodo pretium sit velit sagittis, vel vehicula tellus fusce inceptos facilisis ultrices porttitor venenatis, tempus eleifend felis laoreet tempus platea lacus. iaculis eleifend turpis sodales et ","Professor",null,"Disciplina"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 2","Aluno",null,"Estágio"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 3","Coordenação",null,"Matrícula"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 4","Professor",null,"SECOMP"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 5","Secretária",null,"Dispensa de Disciplina"));

        this.adapter = new AdapterDuvidas(getActivity(), duvidas);
        this.adapter.setRecyclerViewOnClickListenerHack(this);

        this.myRecyclerView.setAdapter(this.adapter);



        return this.rootView;
    }



    @Override
    public void onClickListener(View view, int posicao) {
        Duvida duvida = this.duvidas.get(posicao);
        DuvidaFragment fragment = new DuvidaFragment();
        Bundle b = new Bundle();
        b.putSerializable("duvida", duvida);
        fragment.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();

    }
}
