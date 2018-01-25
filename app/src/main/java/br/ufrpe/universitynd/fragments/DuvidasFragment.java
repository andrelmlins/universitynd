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

import br.ufrpe.universitynd.Interface.EndlessRecyclerViewScrollListener;
import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.models.Usuario;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class DuvidasFragment extends Fragment implements RecyclerViewOnClickListenerHack, Response.ErrorListener, Response.Listener<JSONObject>, SearchView.OnQueryTextListener{
    private View rootView;
    private RecyclerView myRecyclerView;
    private AdapterDuvidas adapter;
    private List<Duvida> duvidas;
    private Requests requests;
    private ProgressDialog progress;

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
        getActivity().setTitle(R.string.ultimas_duvidas);
        ((Main)getActivity()).setColor();
        this.requests = Requests.getInstance(getActivity());

        this.duvidas = new ArrayList<Duvida>();
        this.adapter = new AdapterDuvidas(getActivity(), this.duvidas);
        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaDuvidas);
        this.myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lls = new LinearLayoutManager(getActivity());
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);
        this.adapter.setRecyclerViewOnClickListenerHack(this);
        this.myRecyclerView.setAdapter(this.adapter);

        this.progress = ProgressDialog.show(getActivity(), "",getString(R.string.carregandoD), true);
        requests.getObject("duvidas",this,this);
        return this.rootView;
    }

    @Override
    public void onClickListener(View view, int posicao) {
        Duvida duvida = this.duvidas.get(posicao);
        DuvidaFragment fragment = new DuvidaFragment();
        Bundle b = new Bundle();
        b.putSerializable("duvida", duvida);
        fragment.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), R.string.erroC, Toast.LENGTH_SHORT).show();
        if(this.progress!=null) this.progress.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            rootView.findViewById(R.id.progress).setVisibility(View.GONE);
            SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");

            JSONArray arrayDuvidas = response.getJSONArray("data");
            JSONObject jsonDuvida;
            for(int i = 0; i< arrayDuvidas.length(); i++){
                jsonDuvida = arrayDuvidas.getJSONObject(i);

                this.duvidas.add(new Duvida(jsonDuvida.getString("id"),jsonDuvida.getString("titulo"),
                        new Date(f.parse(jsonDuvida.getString("created_at")).getTime()),
                        jsonDuvida.getString("conteudo"),
                        jsonDuvida.getString("interessado"),
                        null,
                        jsonDuvida.getString("assunto"),new Usuario(jsonDuvida.getString("username"),jsonDuvida.getString("userimage"),jsonDuvida.getString("usertoken"))));

            }
            if(response.getString("from")!="null") {
                this.adapter.reload();
                loadPage(response.getInt("from"), ((int) Math.ceil((float) response.getInt("total") / response.getInt("per_page"))));
                this.adapter.notifyDataSetChanged();
            }
            if(this.progress!=null) this.progress.dismiss();
        } catch (JSONException |ParseException e) {
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
                    requests.getObject("duvidas?page=" + page, DuvidasFragment.this, DuvidasFragment.this);
                    return true;
                }
            });
        }
    }
}
