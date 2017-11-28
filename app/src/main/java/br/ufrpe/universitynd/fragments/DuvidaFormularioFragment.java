package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufrpe.universitynd.R;

/**
 * Created by AndreLucas on 24/11/2017.
 */

public class DuvidaFormularioFragment extends Fragment {
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvida_form_fragment,container,false);
        getActivity().setTitle("Diga sua dúvida");
        return this.rootView;
    }
}
