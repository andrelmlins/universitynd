package br.ufrpe.universitynd.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by AndreLucas on 24/11/2017.
 */

public class DuvidaFragment extends Fragment {
    private View rootView;
    private Duvida duvida;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvidas_form_fragment,container,false);
        this.duvida = (Duvida) this.getArguments().get("duvida");
        getActivity().setTitle(this.duvida.getTexto());
        return this.rootView;
    }
}
