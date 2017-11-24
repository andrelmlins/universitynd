package br.ufrpe.universitynd.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
        this.rootView = inflater.inflate(R.layout.duvidas_form_fragment,container,false);
        getActivity().setTitle("Diga sua dúvida");
        return this.rootView;
    }
}
