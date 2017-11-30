package br.ufrpe.universitynd.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by AndreLucas on 29/11/2017.
 */

public class MeuPerfilFragment extends Fragment {
    private View rootView;
    private CircularImageView image;
    private TextView nome;
    private TextView email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.perfil_fragment,container,false);
        getActivity().setTitle(R.string.perfil);
        ((Main)getActivity()).setColor();
        image = (CircularImageView) this.rootView.findViewById(R.id.image);
        nome = (TextView) this.rootView.findViewById(R.id.nome);
        email = (TextView) this.rootView.findViewById(R.id.email);
        SharedPreferences preferences = getActivity().getSharedPreferences("usuario",0);
        Picasso.with(getActivity()).load(preferences.getString("picture","")).into(image);
        this.nome.setText(preferences.getString("fullname",""));
        this.email.setText(preferences.getString("email",""));

        return this.rootView;
    }
}
