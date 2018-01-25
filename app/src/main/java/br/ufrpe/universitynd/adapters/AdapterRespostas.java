package br.ufrpe.universitynd.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Resposta;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class AdapterRespostas extends RecyclerView.Adapter<AdapterRespostas.MyViewHolder> {
    private final List<Resposta> respostas;
    private ArrayList<Resposta> arraylist;
    private LayoutInflater mLayoutInflater;
    private TextView nome;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public AdapterRespostas(Context context, List<Resposta> respostas) {
        this.respostas = respostas;
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Resposta>();
        this.arraylist.addAll(this.respostas);
    }

    public void reload(){
        this.arraylist.addAll(this.respostas);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) { // criar uma nova view so quando for necessário
        View v = mLayoutInflater.inflate(R.layout.adapter_respostas, viewGroup, false);
        //Criar nosso viewHolder
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {//vincula os dados da lista a view
        holder.nome.setText(respostas.get(position).getUsuario().getNomeCompleto()); //seta os valores
        holder.conteudo.setText(respostas.get(position).getConteudo());
        Picasso.with(context).load(respostas.get(position).getUsuario().getFoto()).into(holder.imagem);
    }

    @Override
    public int getItemCount() {
        return respostas.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        this.respostas.clear();
        if (charText.length() == 0) {
            this.respostas.addAll(arraylist);
        } else {
            for (Resposta r : arraylist) {
                if (r.getConteudo().toLowerCase(Locale.getDefault()).contains(charText)
                        || r.getUsuario().getNomeCompleto().toLowerCase(Locale.getDefault()).contains(charText)) {
                    respostas.add(r);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nome;
        public TextView conteudo;
        public CircularImageView imagem;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
            conteudo = (TextView) itemView.findViewById(R.id.conteudo);
            imagem = (CircularImageView) itemView.findViewById(R.id.imagem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(view, getPosition());
            }
        }
    }
}
