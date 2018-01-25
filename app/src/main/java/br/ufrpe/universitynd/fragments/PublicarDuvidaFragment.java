package br.ufrpe.universitynd.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.models.Usuario;
import br.ufrpe.universitynd.utils.MultiSpinner;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class PublicarDuvidaFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, Response.ErrorListener, Response.Listener<JSONObject> {
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

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.delete).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.like).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvida_form_fragment,container,false);
        getActivity().setTitle(R.string.dizDuviva);
        ((Main)getActivity()).setColor();
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
        if(!this.titulo.getText().equals("") || !this.conteudo.equals("")) {
            try {
                duvida.put("titulo", this.titulo.getText());
                duvida.put("conteudo", this.conteudo.getText());
                duvida.put("assunto", this.assunto.getSelectedItem().toString());
                duvida.put("disciplina", this.disciplina.getSelectedItem().toString());
                duvida.put("interessado", this.interessado.getSelectedItem().toString());
                duvida.put("token", preferences.getString("token", ""));
                requests.post("duvidas", duvida, this, this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(),getString(R.string.erroD),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String assunto = parent.getSelectedItem().toString();
        if(assunto.equals(getString(R.string.disciplinaS)) || assunto.equals(getString(R.string.dispensaDisciplina))){
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
        Toast.makeText(getActivity(), R.string.erroC, Toast.LENGTH_SHORT).show();

        // teste duvidas
//        String[] dis = {this.disciplina.getSelectedItem().toString()};
//        Teste.addDuv(new Duvida(this.titulo.getText().toString(),new Date(),this.conteudo.getText().toString(),
//                this.interessado.getSelectedItem().toString(),
//                dis,
//                this.assunto.getSelectedItem().toString()));
    }

    @Override
    public void onResponse(JSONObject response) {
        Duvida duvida = null;
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
            duvida = new Duvida(response.getString("id"),response.getString("titulo"),new Date(f.parse(response.getString("created_at")).getTime()),response.getString("conteudo"),response.getString("interessado"),null,response.getString("assunto"),new Usuario(response.getString("username"),response.getString("userimage"),response.getString("usertoken")));
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
