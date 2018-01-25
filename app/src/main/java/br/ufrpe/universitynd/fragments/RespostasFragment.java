package br.ufrpe.universitynd.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.Interface.EndlessRecyclerViewScrollListener;
import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterRespostas;
import br.ufrpe.universitynd.adapters.AdapterUsuarios;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.models.Resposta;
import br.ufrpe.universitynd.models.Usuario;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by AndreLucas on 24/01/2018.
 */

public class RespostasFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>, SearchView.OnQueryTextListener {
    private View rootView;
    private AdapterRespostas adapter;
    private List<Resposta> respostas;
    private Requests requests;
    private ProgressDialog progress;
    private RecyclerView myRecyclerView;
    private Duvida duvida;

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
        this.rootView = inflater.inflate(R.layout.duvidas_fragment,container,false);
        this.duvida = (Duvida) this.getArguments().get("duvida");
        getActivity().setTitle(getString(R.string.respostas)+" - "+this.duvida.getNome());
        ((Main)getActivity()).setColor();
        this.requests = Requests.getInstance(getActivity());

        this.respostas = new ArrayList<>();
        this.adapter = new AdapterRespostas(getActivity(),this.respostas);
        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaDuvidas);
        this.myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lls = new LinearLayoutManager(getActivity());
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);
        this.myRecyclerView.setAdapter(this.adapter);

        this.progress = ProgressDialog.show(getActivity(), "","Carregando respostas...", true);
        requests.getObject("duvidas/"+(this.duvida.getId())+"/respostas",this,this);
        return this.rootView;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "Erro de Conexão :)", Toast.LENGTH_SHORT).show();
        if(this.progress!=null) this.progress.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            rootView.findViewById(R.id.progress).setVisibility(View.GONE);
            JSONArray arrayRespostas = response.getJSONArray("data");
            JSONObject jsonResposta;
            for(int i = 0; i< arrayRespostas.length(); i++){
                jsonResposta = arrayRespostas.getJSONObject(i);
                this.respostas.add(new Resposta(jsonResposta.getString("conteudo"),new Usuario(jsonResposta.getString("username"),jsonResposta.getString("userimage"),"")));
            }
            if(response.getString("from")!="null") {
                this.adapter.reload();
                loadPage(response.getInt("from"), ((int) Math.ceil((float) response.getInt("total") / response.getInt("per_page"))));
                this.adapter.notifyDataSetChanged();
            }
            if(this.progress!=null) this.progress.dismiss();
        } catch (JSONException e) {
            Log.e("TESTE",e.getMessage());
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

    public void loadPage(int page,int totalPages){
        if(page==1) {
            this.myRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(totalPages, myRecyclerView) {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    rootView.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                    requests.getObject("duvidas/"+(duvida.getId())+"/respostas?page=" + page, RespostasFragment.this, RespostasFragment.this);
                    return true;
                }
            });
        }
    }
}
