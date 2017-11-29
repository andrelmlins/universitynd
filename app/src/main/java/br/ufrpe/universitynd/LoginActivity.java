package br.ufrpe.universitynd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufrpe.universitynd.utils.Requests;

/**
 * Created by air on 25/11/17.
 */

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener, OnClickListener{

    private Button btnAvaa;
    private EditText username;
    private EditText password;
    private Requests requests;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        SharedPreferences preferences = getSharedPreferences("usuario",0);
        if (!preferences.getString("token", "").equals("")) {
            Intent i = new Intent(LoginActivity.this, Main.class);
            startActivity(i);
            finish();
        }
        this.requests = Requests.getInstance(this);
        this.btnAvaa =  (Button) findViewById(R.id.btnAva);
        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        btnAvaa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.progress = ProgressDialog.show(this, "","Realizando Login, aguarde...", true);
        JSONObject j = new JSONObject();
        try {
            j.put("username",String.valueOf(this.username.getText()));
            j.put("password",String.valueOf(this.password.getText()));
        } catch (JSONException e) {

        }
        requests.post("login",j,this,this);
    }

    @Override
    public void onResponse(JSONObject response) {
        if(response.has("error")){
            try {
                Toast.makeText(this, response.getString("error"), Toast.LENGTH_SHORT).show();
                if (this.progress != null) this.progress.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            SharedPreferences settings = getSharedPreferences("usuario", 0);
            SharedPreferences.Editor edit = settings.edit();
            try {
                edit.putString("username", response.getString("username"));
                edit.putString("fullname", response.getString("fullname"));
                edit.putString("token", response.getString("token"));
                edit.putString("email", response.getString("email"));
                edit.putString("departament", response.getString("departament"));
                edit.putString("picture", response.getString("picture"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            edit.commit();
            if (this.progress != null) this.progress.dismiss();
            Intent i = new Intent(LoginActivity.this, Main.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(this.progress!=null) this.progress.dismiss();
        Toast.makeText(this, "Erro de Conexão :(", Toast.LENGTH_SHORT).show();
    }
}
