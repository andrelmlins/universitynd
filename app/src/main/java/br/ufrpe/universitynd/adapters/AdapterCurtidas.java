package br.ufrpe.universitynd.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Curtir;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by AndreLucas on 24/01/2018.
 */

public class AdapterCurtidas extends RecyclerView.Adapter<AdapterCurtidas.MyViewHolder> {
    private final List<Curtir> curtidas;
    private ArrayList<Curtir> arraylist;
    private LayoutInflater mLayoutInflater;
    private TextView nome;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public AdapterCurtidas(Context context, List<Curtir> curtidas) {
        this.curtidas = curtidas;
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Curtir>();
        this.arraylist.addAll(this.curtidas);
    }

    public void reload(){
        this.arraylist = new ArrayList<Curtir>();
        this.arraylist.addAll(this.curtidas);
    }

    @Override
    public AdapterCurtidas.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) { // criar uma nova view so quando for necessário
        View v = mLayoutInflater.inflate(R.layout.adapter_curtida, viewGroup,false);
        //Criar nosso viewHolder
        AdapterCurtidas.MyViewHolder mvh = new AdapterCurtidas.MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(AdapterCurtidas.MyViewHolder holder, int position) {//vincula os dados da lista a view
        holder.nome.setText(curtidas.get(position).getUsuario().getNomeCompleto()); //seta os valores
        holder.data.setText(curtidas.get(position).getDataFormatada());
        Picasso.with(context).load(curtidas.get(position).getUsuario().getFoto()).into(holder.imagem);
    }

    @Override
    public int getItemCount() {
        return curtidas.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        this.curtidas.clear();
        if (charText.length() == 0) {
            this.curtidas.addAll(arraylist);
        } else {
            for (Curtir c : arraylist) {
                if (c.getUsuario().getNomeCompleto().toLowerCase(Locale.getDefault()).contains(charText)){
                    curtidas.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nome;
        public TextView data;
        public CircularImageView imagem;

        public MyViewHolder(View itemView){
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
            data = (TextView) itemView.findViewById(R.id.data);
            imagem = (CircularImageView) itemView.findViewById(R.id.imagem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(view,getPosition());
            }
        }
    }
}
