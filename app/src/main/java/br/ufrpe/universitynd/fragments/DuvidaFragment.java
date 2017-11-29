package br.ufrpe.universitynd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by Danielly Queiroz on 24/11/2017.
 */

public class DuvidaFragment extends Fragment {
    private View rootView;
    private Duvida duvida;
    private Button btnResponder;
    private LinearLayout llResposta;
    private EditText edtResposta;
    private Button btnEnviar;
    private Button btnCancelar;
    private TextView nome;
    private TextView data;
    private TextView descricao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){

        this.rootView = inflater.inflate(R.layout.duvida_fragment,container,false);

        this.duvida = (Duvida) this.getArguments().get("duvida");
        this.btnResponder = (Button) this.rootView.findViewById(R.id.btnShowResposta);
        this.llResposta = (LinearLayout) this.rootView.findViewById(R.id.llResposta);
        this.edtResposta = (EditText) this.rootView.findViewById(R.id.edtResposta);
        this.btnEnviar = (Button) this.rootView.findViewById(R.id.btnEnviar);
        this.btnCancelar = (Button) this.rootView.findViewById(R.id.btnCancelar);
        this.nome = (TextView) this.rootView.findViewById(R.id.DuvidaNome);
        this.data = (TextView) this.rootView.findViewById(R.id.DuvidaData);
        this.descricao = (TextView) this.rootView.findViewById(R.id.DuvidaDescricao);

        // Mostra o formulario de Resposta
        this.btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DuvidaFragment.this.llResposta.setVisibility(View.VISIBLE);
                DuvidaFragment.this.btnResponder.setVisibility(View.GONE);
            }
        });
        //Voltar para a Pergunta
        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DuvidaFragment.this.llResposta.setVisibility(View.GONE);
                DuvidaFragment.this.btnResponder.setVisibility(View.VISIBLE);
                DuvidaFragment.this.edtResposta.setText("");
            }
        });
        //Seta os valores da Duvida
        this.nome.setText(this.duvida.getNome());
        this.data.setText(this.duvida.getDataFormatada(this.duvida.getData()));
        this.descricao.setText(this.duvida.getConteudo());
        getActivity().setTitle(this.duvida.getNome());
        return this.rootView;
    }
  
}
