package br.ufrpe.universitynd.fragments;

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

import org.json.JSONException;
import org.json.JSONObject;

import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.utils.MultiSpinner;

/**
 * Created by AndreLucas on 24/11/2017.
 */

public class DuvidaFormularioFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private View rootView;
    private Button enviar;
    private Spinner assunto;
    private Spinner interessado;
    private MultiSpinner disciplina;
    private EditText titulo;
    private EditText conteudo;
    private LinearLayout disciplina_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvida_form_fragment,container,false);
        getActivity().setTitle("Diga sua dúvida");

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
        try {
            duvida.put("titulo",this.titulo.getText());
            duvida.put("conteudo",this.conteudo.getText());
            duvida.put("assunto",this.assunto.getSelectedItem().toString());
            duvida.put("disciplina",this.disciplina.getSelectedItem().toString());
            duvida.put("interessado",this.disciplina.getSelectedItem().toString());
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
}
