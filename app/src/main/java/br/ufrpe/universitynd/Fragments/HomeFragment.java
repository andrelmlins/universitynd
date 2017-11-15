package br.ufrpe.universitynd.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufrpe.universitynd.R;

/**
 * Created by AndreLucas on 14/11/2017.
 */

public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        View rootView = inflater.inflate(R.layout.home_fragment,container,false);
        getActivity().setTitle("UniversityNd");
        return rootView;
    }
}
