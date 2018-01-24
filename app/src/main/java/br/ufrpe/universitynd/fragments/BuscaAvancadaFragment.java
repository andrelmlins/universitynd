package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;


/**
 * Created by Tamires on 25/11/2017.
 */

public class BuscaAvancadaFragment extends Fragment implements  RecyclerViewOnClickListenerHack, SearchView.OnQueryTextListener {
    private View rootView;
    private Button btnBuscar, btnLimpar;
    private AdapterDuvidas adapter;
    private List<Duvida> duvidas;
    private RecyclerView myRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.like).setVisible(false);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState) {

        this.rootView = inflater.inflate(R.layout.busca_avancada_fragment, container, false);
        getActivity().setTitle(R.string.busca_avancada);
        ((Main)getActivity()).setColor();

        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaDuvidas); // Acessar o RecyclerView
        this.myRecyclerView.setHasFixedSize(true); // otimizando dizendo que o tamanho do RecyclerView nao irá mudar

        LinearLayoutManager lls = new LinearLayoutManager(getActivity());//Gerenciar a apresentacao dos itens
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);//setando o linearLayout dentro do RecyclerView

        this.duvidas = new ArrayList<Duvida>();
        /*duvidas.add(new Duvida("Danielly",new Date(),"Lorem ipsum integer aptent commodo pretium sit velit sagittis, vel vehicula tellus fusce inceptos facilisis ultrices porttitor venenatis, tempus eleifend felis laoreet tempus platea lacus. iaculis eleifend turpis sodales et ","Professor",null,"Disciplina"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 2","Aluno",null,"Estágio"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 3","Coordenação",null,"Matrícula"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 4","Professor",null,"SECOMP"));
        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 5","Secretária",null,"Dispensa de Disciplina"));*/

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


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.adapter.filter(newText);
        return true;
    }
}
