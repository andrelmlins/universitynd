package br.ufrpe.universitynd.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by air on 22/11/17.
 */

public class AdapterDuvidas extends RecyclerView.Adapter<AdapterDuvidas.MyViewHolder> {

    private final List<Duvida> mDuvidas;
    private LayoutInflater mLayoutInflater;
    private TextView nome;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public AdapterDuvidas(Context context, List<Duvida> mDuvidas) {
        this.mDuvidas = mDuvidas;
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = mLayoutInflater.inflate(R.layout.adapter_duvidas, viewGroup,false);
        //Criar nosso viewHolder
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvNome.setText(mDuvidas.get(position).getNome());
        holder.tvData.setText(mDuvidas.get(position).getDataFormatada(mDuvidas.get(position).getData()));
        holder.tvDescricao.setText(mDuvidas.get(position).getConteudo());
        SharedPreferences preferences = context.getSharedPreferences("usuario",0);
        Picasso.with(context).load(preferences.getString("picture","")).into(holder.userImage);
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

//    private final List<Duvida> duvidas;
//    private final Activity act;
//    private TextView nome;
//
//
//    public AdapterDuvidas(List<Duvida> duvidas, Activity act) {
//        this.duvidas = duvidas;
//        this.act = act;
//
//    }
//
//    @Override
//    public int getCount() {
//
//        return duvidas.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//
//        return duvidas.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        View view = act.getLayoutInflater()
//                .inflate(R.layout.adapter_duvidas, viewGroup, false);
//        Duvida duvida = duvidas.get(position);
//        TextView nome = (TextView) view.findViewById(R.id.DuvidaNome);
//        TextView data = (TextView) view.findViewById(R.id.DuvidaData);
//        TextView descricao = (TextView) view.findViewById(R.id.DuvidaDescricao);
//
//        nome.setText(duvida.getNome());
//        data.setText(duvida.getDataFormatada(duvida.getData()));
//        descricao.setText(duvida.getTexto());
//        return view;
//    }
}