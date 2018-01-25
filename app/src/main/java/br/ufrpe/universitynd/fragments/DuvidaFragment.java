package br.ufrpe.universitynd.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterRespostas;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.models.Resposta;
import br.ufrpe.universitynd.models.Usuario;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class DuvidaFragment extends Fragment implements View.OnClickListener, MenuItem.OnMenuItemClickListener, Response.ErrorListener, Response.Listener<JSONObject>, SearchView.OnQueryTextListener {
    private View rootView;
    private Duvida duvida;
    private LinearLayout btnResponder;
    private LinearLayout llResposta;
    private EditText edtResposta;
    private ImageButton btnEnviar;
    private LinearLayout btnCancelar;
    private LinearLayout btnTodasRespostas;
    private LinearLayout btnCurtidas;
    private TextView nome;
    private TextView data;
    private TextView assunto;
    private TextView descricao;
    private TextView interessado;
    private TextView countRespostas;
    private TextView curtidas;
    private int gone = View.GONE;
    private int visible = View.VISIBLE;
    private boolean mostrarResposta = false;
    private RecyclerView myRecyclerView;
    private AdapterRespostas adapter;
    private List<Resposta> respostas;
    private Requests requests;
    private ProgressDialog progress;
    private Menu menu;


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
            menu.findItem(R.id.like).setVisible(true);
        } else {
            menu.findItem(R.id.edit).setVisible(true);
            menu.findItem(R.id.like).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);

        menu.findItem(R.id.edit).setOnMenuItemClickListener(this);
        menu.findItem(R.id.like).setOnMenuItemClickListener(this);
        this.menu = menu;
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvida_fragment,container,false);
        this.requests = Requests.getInstance(getActivity());
        this.duvida = (Duvida) this.getArguments().get("duvida");

        this.progress = ProgressDialog.show(getActivity(), "",getString(R.string.carregandoRes), true);
        SharedPreferences preferences = getActivity().getSharedPreferences("usuario", 0);
        //Para verificar o usuário logado
        this.requests.getObject("duvidas/"+this.duvida.getId()+"?token="+preferences.getString("token",""),this,this);

        ((Main)getActivity()).setColor(this.duvida.getColor());

        this.btnResponder = (LinearLayout) this.rootView.findViewById(R.id.btnShowResposta);
        this.llResposta = (LinearLayout) this.rootView.findViewById(R.id.llResposta);
        this.edtResposta = (EditText) this.rootView.findViewById(R.id.edtResposta);
        this.btnEnviar = (ImageButton) this.rootView.findViewById(R.id.btnEnviar);
        this.btnCancelar = (LinearLayout) this.rootView.findViewById(R.id.btnCancelar);
        this.btnTodasRespostas = (LinearLayout) this.rootView.findViewById(R.id.btnTodasRespostas);
        this.btnCurtidas = (LinearLayout) this.rootView.findViewById(R.id.btnCurtidas);
        this.nome = (TextView) this.rootView.findViewById(R.id.DuvidaNome);
        this.data = (TextView) this.rootView.findViewById(R.id.DuvidaData);
        this.descricao = (TextView) this.rootView.findViewById(R.id.DuvidaDescricao);
        this.assunto = (TextView) this.rootView.findViewById(R.id.DuvidaAssunto);
        this.interessado = (TextView) this.rootView.findViewById(R.id.DuvidaInteressado);
        this.countRespostas = (TextView) this.rootView.findViewById(R.id.countRespostas);
        this.curtidas = (TextView) this.rootView.findViewById(R.id.curtidas);

        //salvar o estado
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
            public void onClick(View v) {
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

        this.btnTodasRespostas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RespostasFragment fragment = new RespostasFragment();
                Bundle b = new Bundle();
                b.putSerializable("duvida", duvida);
                fragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
            }
        });

        this.btnCurtidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurtidasFragment fragment = new CurtidasFragment();
                Bundle b = new Bundle();
                b.putSerializable("duvida", duvida);
                fragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
            }
        });

        this.btnEnviar.setOnClickListener(this);
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
        } else if(item.getItemId()==R.id.like){
            JSONObject curtir = new JSONObject();
            SharedPreferences preferences = getActivity().getSharedPreferences("usuario", 0);
            try {
                curtir.put("token",preferences.getString("token", ""));
                this.progress = ProgressDialog.show(getActivity(), "","Salvando Curtida...", true);
                this.requests.post("duvidas/" + this.duvida.getId() + "/curtir", curtir, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("curtir")){
                                menu.findItem(R.id.like).setIcon(getResources().getDrawable(R.drawable.ic_thumb_white));
                                curtidas.setText((Integer.parseInt(curtidas.getText()+"")+1)+"");
                            } else {
                                menu.findItem(R.id.like).setIcon(getResources().getDrawable(R.drawable.ic_thumb_border));
                                curtidas.setText((Integer.parseInt(curtidas.getText()+"")-1)+"");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(progress!=null) progress.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), R.string.erroC, Toast.LENGTH_SHORT).show();
                        if(progress!=null) progress.dismiss();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }else {
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
        Toast.makeText(getActivity(), R.string.erroC, Toast.LENGTH_SHORT).show();
        if(this.progress!=null) this.progress.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray arrayRespostas = response.getJSONArray("respostas");
            this.countRespostas.setText(R.string.resp +arrayRespostas.length());
            this.curtidas.setText(response.getString("count_curtidas"));
            JSONObject jsonResposta;
            for(int i = 0; i< arrayRespostas.length(); i++){
                jsonResposta = arrayRespostas.getJSONObject(i);
                this.respostas.add(new Resposta(jsonResposta.getString("conteudo"),new Usuario(jsonResposta.getString("username"),jsonResposta.getString("userimage"),"")));
            }

            this.adapter = new AdapterRespostas(getActivity(), this.respostas);
            this.myRecyclerView.setAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();

            if(response.getBoolean("curtir")){
                menu.findItem(R.id.like).setIcon(getResources().getDrawable(R.drawable.ic_thumb_white));
            } else {
                menu.findItem(R.id.like).setIcon(getResources().getDrawable(R.drawable.ic_thumb_border));
            }
            if(this.progress!=null) this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        JSONObject resposta = new JSONObject();
        SharedPreferences preferences = getActivity().getSharedPreferences("usuario", 0);
        try {
            resposta.put("conteudo",this.edtResposta.getText());
            resposta.put("token",preferences.getString("token", ""));
            requests.post("duvidas/" + this.duvida.getId() + "/respostas", resposta, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        respostas.add(new Resposta(response.getString("conteudo"),new Usuario(response.getString("username"),response.getString("userimage"),"")));
                        adapter = new AdapterRespostas(getActivity(), respostas);
                        myRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        if(progress!=null) progress.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    DuvidaFragment.this.llResposta.setVisibility(gone);
                    DuvidaFragment.this.btnResponder.setVisibility(visible);
                    DuvidaFragment.this.edtResposta.setText("");
                    mostrarResposta = false;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), R.string.erroC, Toast.LENGTH_SHORT).show();
                    if(progress!=null) progress.dismiss();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
