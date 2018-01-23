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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import br.ufrpe.universitynd.adapters.AdapterRespostas;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.models.Resposta;
import br.ufrpe.universitynd.models.Usuario;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class DuvidaFragment extends Fragment implements MenuItem.OnMenuItemClickListener, Response.ErrorListener, Response.Listener<JSONObject>, SearchView.OnQueryTextListener {
    private View rootView;
    private Duvida duvida;
    private Button btnResponder;
    private LinearLayout llResposta;
    private EditText edtResposta;
    private Button btnEnviar;
    private Button btnCancelar;
    private TextView nome;
    private TextView data;
    private TextView assunto;
    private TextView descricao;
    private TextView interessado;
    private int gone = View.GONE;
    private int visible = View.VISIBLE;
    private boolean mostrarResposta = false;
    private RecyclerView myRecyclerView;
    private AdapterRespostas adapter;
    private List<Resposta> respostas;
    private Requests requests;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);

        SharedPreferences preferences = getActivity().getSharedPreferences("usuario",0);
        if(!this.duvida.getUsuario().getToken().equals(preferences.getString("token",""))){
            menu.findItem(R.id.edit).setVisible(false);
        } else {
            menu.findItem(R.id.edit).setVisible(true);
        }
        super.onPrepareOptionsMenu(menu);

        menu.findItem(R.id.edit).setOnMenuItemClickListener(this);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvida_fragment,container,false);
        this.requests = Requests.getInstance(getActivity());
        this.duvida = (Duvida) this.getArguments().get("duvida");
        this.progress = ProgressDialog.show(getActivity(), "","Carregando as respostas...", true);
        this.requests.getObject("duvidas/"+this.duvida.getId()+"/respostas",this,this);

        ((Main)getActivity()).setColor(this.duvida.getColor());

        this.btnResponder = (Button) this.rootView.findViewById(R.id.btnShowResposta);
        this.llResposta = (LinearLayout) this.rootView.findViewById(R.id.llResposta);
        this.edtResposta = (EditText) this.rootView.findViewById(R.id.edtResposta);
        this.btnEnviar = (Button) this.rootView.findViewById(R.id.btnEnviar);
        this.btnCancelar = (Button) this.rootView.findViewById(R.id.btnCancelar);
        this.nome = (TextView) this.rootView.findViewById(R.id.DuvidaNome);
        this.data = (TextView) this.rootView.findViewById(R.id.DuvidaData);
        this.descricao = (TextView) this.rootView.findViewById(R.id.DuvidaDescricao);
        this.assunto = (TextView) this.rootView.findViewById(R.id.DuvidaAssunto);
        this.interessado = (TextView) this.rootView.findViewById(R.id.DuvidaInteressado);

        if(savedInstanteState!= null){
            mostrarResposta = savedInstanteState.getBoolean("mostrarResposta");

            if(mostrarResposta){
                DuvidaFragment.this.llResposta.setVisibility(visible);
                DuvidaFragment.this.btnResponder.setVisibility(gone);
                DuvidaFragment.this.edtResposta.setText(savedInstanteState.getString("edtResposta"));


           }else{
                DuvidaFragment.this.llResposta.setVisibility(gone);
                DuvidaFragment.this.btnResponder.setVisibility(visible);
                DuvidaFragment.this.edtResposta.setText("");
           }

        }

        this.btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DuvidaFragment.this.llResposta.setVisibility(visible);
                DuvidaFragment.this.btnResponder.setVisibility(gone);
                mostrarResposta = true;
            }
        });

        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DuvidaFragment.this.llResposta.setVisibility(gone);
                DuvidaFragment.this.btnResponder.setVisibility(visible);
                DuvidaFragment.this.edtResposta.setText("");
                mostrarResposta = false;
            }
        });
        //Seta os valores da Duvida
        this.nome.setText(this.duvida.getNome());
        this.data.setText(this.duvida.getDataFormatada(this.duvida.getData()));
        this.descricao.setText(this.duvida.getConteudo());
        this.assunto.setText(getString(R.string.assunto)+": "+this.duvida.getAssunto());
        this.interessado.setText(this.duvida.getInteressado());

        this.respostas = new ArrayList<Resposta>();
        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaRespostas);
        this.myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lls = new LinearLayoutManager(getActivity());
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);

        getActivity().setTitle(this.duvida.getNome());
        return this.rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("mostrarResposta", mostrarResposta);
        outState.putString("edtResposta", edtResposta.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.edit){
            Duvida duvida = this.duvida;
            EditarDuvidaFragment fragment = new EditarDuvidaFragment();
            Bundle b = new Bundle();
            b.putSerializable("duvida", duvida);
            fragment.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
            return true;
        } else {
            return false;
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

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "Erro de Conexão :)", Toast.LENGTH_SHORT).show();
        if(this.progress!=null) this.progress.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
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
            this.adapter = new AdapterDuvidas(getActivity(), this.duvidas);
            this.adapter.setRecyclerViewOnClickListenerHack(this);
            this.myRecyclerView.setAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();
            if(this.progress!=null) this.progress.dismiss();
        } catch (JSONException |ParseException e) {
            e.printStackTrace();
        }
    }
}
