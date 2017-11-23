package br.ufrpe.universitynd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static br.ufrpe.universitynd.R.layout.activity_duvidasform;

/**
 * Created by air on 23/11/17.
 */

public class DuvidasFormularioActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvidasform);

//        setContentView(activity_duvidasform);
//
//        TextView txtProduct = (TextView) findViewById(R.id.product_label);
//
//        Intent i = getIntent();
//        // getting attached intent data
//        String product = i.getStringExtra("product");
//        // displaying selected product name
//        txtProduct.setText(product);
    }
}
