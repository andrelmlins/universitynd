package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import br.ufrpe.universitynd.R;


/**
 * Created by Tamires on 25/11/2017.
 */

public class BuscaAvancadaFragment extends Fragment implements  AdapterView.OnItemSelectedListener{
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState) {
        this.rootView = inflater.inflate(R.layout.busca_avancada_fragment, container, false);
        getActivity().setTitle("Busca Avançada");
        return this.rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
