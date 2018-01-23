package br.ufrpe.universitynd.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Usuario;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class AdapterUsuarios extends BaseAdapter {
    private List<Usuario> usuarios;
    private ArrayList<Usuario> arraylist;
    private LayoutInflater mLayoutInflater;
    private TextView nome;
    private Activity context;

    public AdapterUsuarios(Activity context, List<Usuario> usuarios) {
        this.usuarios = usuarios;
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Usuario>();
        this.arraylist.addAll(this.usuarios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(R.layout.adapter_ranking, parent, false);
        Usuario u = this.usuarios.get(position);

        TextView nome = (TextView) view.findViewById(R.id.nome);
        TextView quantidade = (TextView) view.findViewById(R.id.quantidade);
        TextView posicao = (TextView) view.findViewById(R.id.posicao);
        ImageView image = (ImageView) view.findViewById(R.id.imagem);

        nome.setText(u.getNomeCompleto());
        quantidade.setText("Respostas: "+u.getRespostas());
        posicao.setText(position+"");
        Picasso.with(view.getContext()).load(u.getFoto()).into(image);

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        this.usuarios.clear();
        if (charText.length() == 0) {
            this.usuarios.addAll(arraylist);
        } else {
            for (Usuario u : arraylist) {
                if (u.getNomeCompleto().toLowerCase(Locale.getDefault()).contains(charText)){
                    this.usuarios.add(u);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return this.usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
