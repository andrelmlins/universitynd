package br.ufrpe.universitynd.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.ufrpe.universitynd.R;

/**
 * Created by AndreLucas on 14/11/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private CardView publique_duvida;
    private CardView ultimas_duvidas;
    private View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.home_fragment,container,false);
        getActivity().setTitle("UniversityNd");
        this.publique_duvida = (CardView) this.rootView.findViewById(R.id.publique_duvida);
        this.ultimas_duvidas = (CardView) this.rootView.findViewById(R.id.ultimas_duvidas);
        this.publique_duvida.setOnClickListener(this);
        this.ultimas_duvidas.setOnClickListener(this);
        return this.rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.publique_duvida){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new DuvidaFormularioFragment()).addToBackStack("").commit();
        } else if(v.getId()==R.id.ultimas_duvidas){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new DuvidasFragment()).addToBackStack("").commit();
        }
    }
}
