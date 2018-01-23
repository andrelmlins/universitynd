package br.ufrpe.universitynd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import br.ufrpe.universitynd.fragments.BuscaAvancadaFragment;
import br.ufrpe.universitynd.fragments.DuvidasFragment;
import br.ufrpe.universitynd.fragments.HomeFragment;
import br.ufrpe.universitynd.fragments.MeuPerfilFragment;
import br.ufrpe.universitynd.fragments.MinhasDuvidasFragment;
import br.ufrpe.universitynd.fragments.RankingFragment;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    private ActionBar actionBar;
    private Fragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        actionBar = getSupportActionBar();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        CircularImageView user_image = (CircularImageView) header.findViewById(R.id.user_image);
        TextView user_name = (TextView) header.findViewById(R.id.user_name);
        TextView user_email = (TextView) header.findViewById(R.id.user_email);
        SharedPreferences preferences = getSharedPreferences("usuario",0);
        Picasso.with(this).load(preferences.getString("picture","")).into(user_image);
        user_name.setText(preferences.getString("fullname", ""));
        user_email.setText(preferences.getString("email",""));

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_fragment, new HomeFragment(),"fragment").commit();
        } else {
            mContent = getSupportFragmentManager().findFragmentByTag("fragment");
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.home) {
            fm.beginTransaction().replace(R.id.content_fragment, new HomeFragment()).addToBackStack("").commit();
        } else if(id == R.id.meu_perfil){
            fm.beginTransaction().replace(R.id.content_fragment, new MeuPerfilFragment()).addToBackStack("").commit();
        } else if(id == R.id.busca_avancada){
            fm.beginTransaction().replace(R.id.content_fragment, new BuscaAvancadaFragment()).addToBackStack("").commit();
        }  else if(id == R.id.minhas_perguntas){
            fm.beginTransaction().replace(R.id.content_fragment, new MinhasDuvidasFragment()).addToBackStack("").commit();
        } else if(id == R.id.ultimas_perguntas){
            fm.beginTransaction().replace(R.id.content_fragment, new DuvidasFragment()).addToBackStack("").commit();
        } else if(id == R.id.ranking){
            fm.beginTransaction().replace(R.id.content_fragment, new RankingFragment()).addToBackStack("").commit();
        }  else if(id == R.id.sair) {
            SharedPreferences preferences = getSharedPreferences("usuario", 0);
            preferences.edit().clear().commit();
            Intent i = new Intent(Main.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState){
        mContent = getSupportFragmentManager().getFragment(inState,"fragment");
    }

    public void setColor(String[] color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color[1]));
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color[0])));
        }
    }
    public void setColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
            actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorPrimary)));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#a11a20"));
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ca2129")));
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
