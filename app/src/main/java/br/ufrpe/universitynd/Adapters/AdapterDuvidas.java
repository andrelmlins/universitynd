package br.ufrpe.universitynd.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.universitynd.R;
import br.ufrpe.universitynd.models.Duvida;

/**
 * Created by air on 22/11/17.
 */

public class AdapterDuvidas extends BaseAdapter{

    private final List<Duvida> duvidas;
    private final Activity act;

    public  AdapterDuvidas(List<Duvida> duvidas, Activity act){
        this.duvidas = duvidas;
        this.act = act;

    }

    @Override
    public int getCount() {

        return duvidas.size();
    }

    @Override
    public Object getItem(int position) {

        return duvidas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.adapter_duvidas, viewGroup, false);
                Duvida duvida = duvidas.get(position);

        TextView descricao =  (TextView) view.findViewById(R.id.DuvidaDescricao);
        descricao.setText(duvida.getTexto());
        return view;
    }
}
