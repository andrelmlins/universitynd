package br.ufrpe.universitynd.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import br.ufrpe.universitynd.Main;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class DuvidaFragment extends Fragment implements MenuItem.OnMenuItemClickListener {
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
        } else {
            menu.findItem(R.id.edit).setVisible(true);
        }
        super.onPrepareOptionsMenu(menu);

        menu.findItem(R.id.edit).setOnMenuItemClickListener(this);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.duvida_fragment,container,false);
        this.duvida = (Duvida) this.getArguments().get("duvida");

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
}
