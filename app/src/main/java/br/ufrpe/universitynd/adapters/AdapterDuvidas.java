package br.ufrpe.universitynd.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by air on 22/11/17.
 */

public class AdapterDuvidas extends RecyclerView.Adapter<AdapterDuvidas.MyViewHolder> {
    private final List<Duvida> mDuvidas;
    private ArrayList<Duvida> arraylist;
    private LayoutInflater mLayoutInflater;
    private TextView nome;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public AdapterDuvidas(Context context, List<Duvida> mDuvidas) {
        this.mDuvidas = mDuvidas;
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Duvida>();
        this.arraylist.addAll(this.mDuvidas);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) { // criar uma nova view so quando for necessário
        View v = mLayoutInflater.inflate(R.layout.adapter_duvidas, viewGroup,false);
        //Criar nosso viewHolder
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {//vincula os dados da lista a view
        holder.tvNome.setText(mDuvidas.get(position).getNome()); //seta os valores
        holder.tvData.setText(mDuvidas.get(position).getDataFormatada(mDuvidas.get(position).getData()));
        holder.tvDescricao.setText(mDuvidas.get(position).getConteudo());
        Picasso.with(context).load(mDuvidas.get(position).getUsuario().getFoto()).into(holder.userImage);
        if(position==mDuvidas.size()){
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDuvidas.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        this.mDuvidas.clear();
        if (charText.length() == 0) {
            this.mDuvidas.addAll(arraylist);
        } else {
            for (Duvida b : arraylist) {
                if (b.getNome().toLowerCase(Locale.getDefault()).contains(charText)
                        || b.getConteudo().toLowerCase(Locale.getDefault()).contains(charText)
                        || b.getAssunto().toLowerCase(Locale.getDefault()).contains(charText)
                        || b.getInteressado().toLowerCase(Locale.getDefault()).contains(charText)){
                    mDuvidas.add(b);
                }
            }
        }
        notifyDataSetChanged();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvNome;
        public TextView tvData;
        public TextView tvDescricao;
        public CircularImageView userImage;
        public LinearLayout line;

        public MyViewHolder(View itemView){
            super(itemView);
            tvNome = (TextView) itemView.findViewById(R.id.DuvidaNome);
            tvData = (TextView) itemView.findViewById(R.id.DuvidaData);
            tvDescricao = (TextView) itemView.findViewById(R.id.DuvidaDescricao);
            userImage = (CircularImageView) itemView.findViewById(R.id.user_image_list);
            line = (LinearLayout) itemView.findViewById(R.id.line);
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