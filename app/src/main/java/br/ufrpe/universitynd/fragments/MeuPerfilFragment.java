package br.ufrpe.universitynd.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by AndreLucas on 29/11/2017.
 */

public class MeuPerfilFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    private View rootView;
    private CircularImageView image;
    private TextView nome;
    private TextView email;
    private TextView departament;
    private TextView quant_respostas;
    private TextView quant_duvidas;
    private Requests requests;
    private ProgressDialog progress;

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
        this.rootView = inflater.inflate(R.layout.perfil_fragment,container,false);
        this.progress = ProgressDialog.show(getActivity(), "", getString(R.string.carregandoP), true);
        getActivity().setTitle(R.string.perfil);

        this.requests = Requests.getInstance(getActivity());
        ((Main)getActivity()).setColor();
        this.image = (CircularImageView) this.rootView.findViewById(R.id.image);
        this.nome = (TextView) this.rootView.findViewById(R.id.nome);
        this.email = (TextView) this.rootView.findViewById(R.id.email);
        this.quant_duvidas = (TextView) this.rootView.findViewById(R.id.quant_duvidas);
        this.quant_respostas = (TextView) this.rootView.findViewById(R.id.quant_respostas);
        this.departament = (TextView) this.rootView.findViewById(R.id.departament);
        SharedPreferences preferences = getActivity().getSharedPreferences("usuario",0);
        Picasso.with(getActivity()).load(preferences.getString("picture","")).into(image);
        this.nome.setText(preferences.getString("fullname",""));
        this.email.setText(preferences.getString("email",""));
        this.departament.setText(preferences.getString("departament",""));

        requests.getObject("usuarios/"+preferences.getString("token",""),this,this);
        return this.rootView;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), R.string.erroC, Toast.LENGTH_SHORT).show();
        if(this.progress!=null) this.progress.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            this.quant_duvidas.setText(response.getString("quant_duvidas"));
            this.quant_respostas.setText(response.getString("quant_respostas"));
            if(this.progress!=null) this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
