package br.ufrpe.universitynd.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufrpe.universitynd.Interface.RecyclerViewOnClickListenerHack;
import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.fragments.MinhasDuvidasFragment;
import br.ufrpe.universitynd.models.Duvida;
import br.ufrpe.universitynd.models.Resposta;
import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by AndreLucas on 23/01/2018.
 */

public class AdapterRespostas extends RecyclerView.Adapter<AdapterRespostas.MyViewHolder> {
    private final List<Resposta> respostas;
    private ArrayList<Resposta> arraylist;
    private LayoutInflater mLayoutInflater;
    private TextView nome;
    private Activity context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Duvida duvida;

    public AdapterRespostas(Activity context, List<Resposta> respostas, Duvida duvida) {
        this.respostas = respostas;
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Resposta>();
        this.arraylist.addAll(this.respostas);
        this.duvida = duvida;
    }

    public void reload(){
        this.arraylist = new ArrayList<Resposta>();
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {//vincula os dados da lista a view
        holder.nome.setText(respostas.get(position).getUsuario().getNomeCompleto()); //seta os valores
        holder.conteudo.setText(respostas.get(position).getConteudo());
        Picasso.with(context).load(respostas.get(position).getUsuario().getFoto()).into(holder.imagem);
        final SharedPreferences preferences = context.getSharedPreferences("usuario", 0);
        if(respostas.get(position).getUsuario().getToken().equals(preferences.getString("token", ""))) {
            holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                final Requests r = Requests.getInstance(context);
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setMessage("Tem certeza que deseja deletar está resposta?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int which) {
                            JSONObject post = new JSONObject();
                            try {
                                post.put("token", preferences.getString("token", ""));
                                post.put("resposta_id", respostas.get(position).getId());
                                r.post("duvidas/" + duvida.getId() + "/respostas/deletar", post, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        respostas.remove(position);
                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, R.string.erroC, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                alertDialog.show();
                }
            });
        } else {
            holder.btnDeletar.setVisibility(View.GONE);
        }
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
        public ImageButton btnDeletar;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
            conteudo = (TextView) itemView.findViewById(R.id.conteudo);
            imagem = (CircularImageView) itemView.findViewById(R.id.imagem);
            btnDeletar = (ImageButton) itemView.findViewById(R.id.btnDeletar);
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
