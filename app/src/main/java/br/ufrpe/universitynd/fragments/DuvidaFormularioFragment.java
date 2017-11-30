package br.ufrpe.universitynd.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.utils.MultiSpinner;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class DuvidaFormularioFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, Response.ErrorListener, Response.Listener<JSONObject> {
    private View rootView;
    private Button enviar;
    private Spinner assunto;
    private Spinner interessado;
    private MultiSpinner disciplina;
    private EditText titulo;
    private EditText conteudo;
    private LinearLayout disciplina_layout;
    private Requests requests;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvida_form_fragment,container,false);
        getActivity().setTitle(R.string.dizDuviva);
        this.requests = Requests.getInstance(getActivity());

        enviar = (Button) this.rootView.findViewById(R.id.enviar);
        assunto = (Spinner) this.rootView.findViewById(R.id.assunto);
        disciplina_layout = (LinearLayout) this.rootView.findViewById(R.id.disciplina_layout);
        conteudo = (EditText) this.rootView.findViewById(R.id.conteudo);
        titulo = (EditText) this.rootView.findViewById(R.id.titulo);
        disciplina = (MultiSpinner) this.rootView.findViewById(R.id.disciplina);
        interessado = (Spinner) this.rootView.findViewById(R.id.interessado);
        disciplina_layout.setVisibility(View.GONE);
        enviar.setOnClickListener(this);
        assunto.setOnItemSelectedListener(this);
        return this.rootView;
    }

    @Override
    public void onClick(View v) {
        JSONObject duvida = new JSONObject();
        SharedPreferences preferences = getActivity().getSharedPreferences("usuario", 0);
        try {
            duvida.put("titulo",this.titulo.getText());
            duvida.put("conteudo",this.conteudo.getText());
            duvida.put("assunto",this.assunto.getSelectedItem().toString());
            duvida.put("disciplina",this.disciplina.getSelectedItem().toString());
            duvida.put("interessado",this.disciplina.getSelectedItem().toString());
            duvida.put("token",preferences.getString("token", ""));
            requests.post("duvidas",duvida,this,this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String assunto = parent.getSelectedItem().toString();
        if(assunto.equals("Disciplina") || assunto.equals("Dispensa de Disciplina")){
            disciplina_layout.setVisibility(View.VISIBLE);
        } else {
            disciplina_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "Erro de Conexão :)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Duvida duvida = null;
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
            duvida = new Duvida(response.getString("titulo"),new Date(f.parse(response.getString("created_at")).getTime()),response.getString("conteudo"),response.getString("interessado"),null,response.getString("assunto"));
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        DuvidaFragment fragment = new DuvidaFragment();
        Bundle b = new Bundle();
        b.putSerializable("duvida", duvida);
        fragment.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack("").commit();
    }
}
