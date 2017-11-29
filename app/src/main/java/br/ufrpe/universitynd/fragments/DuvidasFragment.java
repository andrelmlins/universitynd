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
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by AndreLucas on 24/11/2017.
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
        getActivity().setTitle("Últimas Dúvidas");//FALTA INTERNACIONALIZAR

        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaDuvidas);
//        this.myRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager llm = (LinearLayoutManager) myRecyclerView.getLayoutManager();
//                AdapterDuvidas adapter1 = (AdapterDuvidas) myRecyclerView.getAdapter();
//
//                //Verifica se tamanho da lista  é igual ao ultimo da posicao
//                if(duvidas.size() == llm.findLastCompletelyVisibleItemPosition() +1){
//
//                }
//            }
//        });
        this.myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lls = new LinearLayoutManager(getActivity());
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);

        this.duvidas = new ArrayList<Duvida>();
        duvidas.add(new Duvida("Danielly",new Date(),"Lorem ipsum integer aptent commodo pretium sit velit sagittis, vel vehicula tellus fusce inceptos facilisis ultrices porttitor venenatis, tempus eleifend felis laoreet tempus platea lacus. iaculis eleifend turpis sodales et "));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 2"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 3"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 4"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 5"));

        this.adapter = new AdapterDuvidas(getActivity(), duvidas);
        this.adapter.setRecyclerViewOnClickListenerHack(this);

        this.myRecyclerView.setAdapter(this.adapter);

//        this.adapter = new AdapterDuvidas(duvidas, getActivity());
        //this.listView.setAdapter(adapter);
//        this.myRecyclerView.setOnItemClickListener(this);

        return this.rootView;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Duvida duvida = this.duvidas.get(position);
//        DuvidaFragment fragment = new DuvidaFragment();
//        Bundle b = new Bundle();
//        b.putSerializable("duvida", duvida);
//        fragment.setArguments(b);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
//    }

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
