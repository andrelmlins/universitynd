package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.adapters.AdapterDuvidas;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class DuvidasFragment extends Fragment implements RecyclerViewOnClickListenerHack, Response.ErrorListener, Response.Listener<JSONObject>{
    private View rootView;
    private RecyclerView myRecyclerView;
    private AdapterDuvidas adapter;
    private List<Duvida> duvidas;
    private Requests requests;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){

        this.rootView = inflater.inflate(R.layout.duvidas_fragment,container,false);
        getActivity().setTitle(R.string.ultimas_duvidas);
        ((Main)getActivity()).setColor();
        this.requests = Requests.getInstance(getActivity());

        this.myRecyclerView = (RecyclerView) this.rootView.findViewById(R.id.listaDuvidas);
        this.myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lls = new LinearLayoutManager(getActivity());
        lls.setOrientation(LinearLayoutManager.VERTICAL);
        this.myRecyclerView.setLayoutManager(lls);

        requests.getObject("duvidas",this,this);

        this.duvidas = new ArrayList<Duvida>();
//        duvidas.add(new Duvida("Danielly",new Date(),"Lorem ipsum integer aptent commodo pretium sit velit sagittis, vel vehicula tellus fusce inceptos facilisis ultrices porttitor venenatis, tempus eleifend felis laoreet tempus platea lacus. iaculis eleifend turpis sodales et ","Professor",null,"Disciplina"));
//        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 2","Aluno",null,"Estágio"));
//        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 3","Coordenação",null,"Matrícula"));
//        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 4","Professor",null,"SECOMP"));
//        duvidas.add(new Duvida("Danielly",new Date(),"Pergunta 5","Secretária",null,"Dispensa de Disciplina"));

        this.adapter = new AdapterDuvidas(getActivity(), duvidas);
        this.adapter.setRecyclerViewOnClickListenerHack(this);
        this.myRecyclerView.setAdapter(this.adapter);



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
        Toast.makeText(getActivity(), "Erro de Conexão :)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        this.duvidas.clear();
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");

            JSONArray arrayDuvidas = response.getJSONArray("data");
            JSONObject jsonDuvida;
            for(int i = 0; i< arrayDuvidas.length()-1; i++){
                jsonDuvida = arrayDuvidas.getJSONObject(i);

                this.duvidas.add(new Duvida( jsonDuvida.getString("titulo"),
                        new Date(f.parse(jsonDuvida.getString("created_at")).getTime()),
                        jsonDuvida.getString("conteudo"),
                        jsonDuvida.getString("interessado"),
                        null,
                        jsonDuvida.getString("assunto")));

            }
            this.adapter.notifyDataSetChanged();

        } catch (JSONException |ParseException e) {
            e.printStackTrace();
        }
    }
}
