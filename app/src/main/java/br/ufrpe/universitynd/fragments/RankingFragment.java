package br.ufrpe.universitynd.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterUsuarios;
import br.ufrpe.universitynd.models.Usuario;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class RankingFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>, SearchView.OnQueryTextListener{
    private View rootView;
    private AdapterUsuarios adapter;
    private List<Usuario> usuarios;
    private Requests requests;
    private ProgressDialog progress;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.like).setVisible(false);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.list,container,false);
        getActivity().setTitle(R.string.ranking);
        ((Main)getActivity()).setColor();
        this.requests = Requests.getInstance(getActivity());

        this.usuarios = new ArrayList<>();
        this.listView = (ListView) rootView.findViewById(R.id.list);

        this.progress = ProgressDialog.show(getActivity(), "","Carregando ranking...", true);
        requests.getObject("usuarios/ranking",this,this);
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
            JSONArray usuarios = response.getJSONArray("usuarios");
            JSONObject jsonDuvida;
            for(int i = 0; i< usuarios.length(); i++){
                jsonDuvida = usuarios.getJSONObject(i);
                this.usuarios.add(new Usuario(jsonDuvida.getString("fullname"),jsonDuvida.getString("picture"),jsonDuvida.getInt("count")));
            }
            this.adapter = new AdapterUsuarios(getActivity(), this.usuarios);
            this.listView.setAdapter(adapter);
            if(this.progress!=null) this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.adapter.filter(newText);
        return true;
    }
}
