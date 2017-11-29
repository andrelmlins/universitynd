package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by AndreLucas on 29/11/2017.
 */

public class MeuPerfilFragment extends Fragment {
    private View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.perfil_fragment,container,false);
        getActivity().setTitle("Meu Perfil");
        return this.rootView;
    }
}
