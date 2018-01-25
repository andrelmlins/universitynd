package br.ufrpe.universitynd.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
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

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.adapters.AdapterUsuarios;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.models.Usuario;
import br.ufrpe.universitynd.utils.Requests;

import static android.widget.Toast.LENGTH_LONG;


/**
 * Created by Tamires on 25/11/2017.
 */

public class BuscaAvancadaFragment extends Fragment implements  RecyclerViewOnClickListenerHack, SearchView.OnQueryTextListener, Response.ErrorListener, Response.Listener<JSONObject> {
    private View rootView;
    private Button btnBuscar, btnLimpar;
    private AdapterDuvidas adapter;
    private List<Duvida> duvidas;
    private RecyclerView myRecyclerView;
    private Requests requests;
    private ProgressDialog progress;
    private Spinner categoria;
    String nomeCat;
    // BuscaAvancadaFragment buscaAvancadaFragment = new BuscaAvancadaFragment();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.edit).setVisible(false);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState) {

        this.rootView = inflater.inflate(R.layout.busca_avancada_fragment, container, false);
        getActivity().setTitle(R.string.busca_avancada);
        ((Main)getActivity()).setColor();

        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaDuvidas); // Acessar o RecyclerView
        categoria = (Spinner)this.rootView.findViewById(R.id.spiCategoria);
        //user = (Spinner) this.rootView.findViewById(R.id.spiTipoUsuario);

        this.myRecyclerView.setHasFixedSize(true); // otimizando dizendo que o tamanho do RecyclerView nao irá mudar
        this.requests = Requests.getInstance(getActivity());

        LinearLayoutManager lls = new LinearLayoutManager(getActivity());//Gerenciar a apresentacao dos itens
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);//setando o linearLayout dentro do RecyclerView

        this.duvidas = new ArrayList<Duvida>();


        this.adapter = new AdapterDuvidas(getActivity(), duvidas);
        this.adapter.setRecyclerViewOnClickListenerHack(this);

        this.myRecyclerView.setAdapter(this.adapter);

        String duvida = getString(R.string.carregandoD);
        this.progress = ProgressDialog.show(getActivity(), "",duvida, true);/////###
        requests.getObject("duvidas",this,this);

        this.btnBuscar = (Button) this.rootView.findViewById(R.id.btnBuscar);
        this.btnLimpar = (Button) this.rootView.findViewById(R.id.btnLimpar);

        BuscaAvancadaFragment.this.myRecyclerView.setVisibility(View.GONE);

        this.btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onQueryTextChange(categoria.getSelectedItem().toString());
                BuscaAvancadaFragment.this.myRecyclerView.setVisibility(View.VISIBLE);
//               
            }
        });
        this.btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscaAvancadaFragment.this.myRecyclerView.setVisibility(View.GONE);
            }
        });
        return this.rootView;
    }

    @Override
    public void onClickListener(View view, int position) {
        Duvida duvida = this.duvidas.get(position);
        DuvidaFragment fragment = new DuvidaFragment();
        Bundle b = new Bundle();
        b.putSerializable("duvida",duvida);
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
                        jsonDuvida.getString("assunto"),new Usuario(jsonDuvida.getString("username"),
                        jsonDuvida.getString("userimage"),jsonDuvida.getString("usertoken"))));

            }
            this.adapter = new AdapterDuvidas(getActivity(), this.duvidas);
            this.adapter.setRecyclerViewOnClickListenerHack(this);
            this.myRecyclerView.setAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();
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
}