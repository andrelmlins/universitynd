package br.ufrpe.universitynd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by air on 25/11/17.
 */

public class LoginActivity extends AppCompatActivity{

    private Button btnAvaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);

        this.btnAvaa =  (Button) findViewById(R.id.btnAva);


        btnAvaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, Main.class);
                startActivity(i);

            }
        });
    }
}
