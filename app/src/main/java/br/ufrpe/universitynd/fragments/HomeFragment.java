package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;

/**
 * Created by AndreLucas on 14/11/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private CardView publique_duvida;
    private CardView ultimas_duvidas;
    private CardView busca_avancada;
    private CardView meu_perfil;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.like).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.home_fragment,container,false);
        getActivity().setTitle(R.string.app_name);
        ((Main)getActivity()).setColor();
        this.publique_duvida = (CardView) this.rootView.findViewById(R.id.publique_duvida);
        this.ultimas_duvidas = (CardView) this.rootView.findViewById(R.id.ultimas_duvidas);
        this.busca_avancada = (CardView) this.rootView.findViewById(R.id.busca_avancada);
        this.meu_perfil = (CardView) this.rootView.findViewById(R.id.meu_perfil);
        this.busca_avancada.setOnClickListener(this);
        this.publique_duvida.setOnClickListener(this);
        this.ultimas_duvidas.setOnClickListener(this);
        this.meu_perfil.setOnClickListener(this);
        return this.rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.publique_duvida){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new PublicarDuvidaFragment()).addToBackStack("").commit();
        } else if(v.getId()==R.id.ultimas_duvidas){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new DuvidasFragment()).addToBackStack("").commit();
        } else if (v.getId() == R.id.busca_avancada){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new BuscaAvancadaFragment()).addToBackStack("").commit();
        } else if (v.getId() == R.id.meu_perfil){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new MeuPerfilFragment()).addToBackStack("").commit();
        }
    }
}
