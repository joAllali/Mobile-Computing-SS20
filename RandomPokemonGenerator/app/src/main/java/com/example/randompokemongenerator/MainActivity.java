package com.example.randompokemongenerator;

import android.content.SyncStatusObserver;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;
import me.sargunvohra.lib.pokekotlin.model.PokemonSprites;

import android.os.StrictMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button generatePokemon;
    private ImageView pokemonDisplay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        progressBar = findViewById(R.id.networkProgressBar);
        pokemonDisplay = findViewById(R.id.pokemonDisplay);
        generatePokemon = findViewById(R.id.btnGenerate);
        generatePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startAsyncTask(v);
            }
        });
    }

    public void startAsyncTask(View v){
        PokeAsyncTask task = new PokeAsyncTask(this);
        int randomNumber = generateRandomNumber(1,800);
        System.out.println(randomNumber);
        task.execute(randomNumber);
    }

    private static class PokeAsyncTask extends AsyncTask<Integer, Integer, Drawable>{
        private WeakReference<MainActivity> activityWeakReference;

        PokeAsyncTask(MainActivity activity){
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = activityWeakReference.get();
            if (activity == null||activity.isFinishing()){
                return;
            }

            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Drawable doInBackground(Integer... integers) {
            int randomNumber = integers[0];
            publishProgress(10);
            PokeApi pokeApi = new PokeApiClient();
            Pokemon pokemon = pokeApi.getPokemon(randomNumber);
            publishProgress(60);
            String url = pokemon.getSprites().getFrontShiny();
            Drawable image = loadImageFromWebOperations(url);
            publishProgress(100);
            return image;
        }

        @Override
        protected void onPostExecute(Drawable image) {
            MainActivity activity = activityWeakReference.get();
            if (activity == null||activity.isFinishing()){
                return;
            }







            ImageView iv = activity.pokemonDisplay;
            iv.setImageDrawable(image);
            activity.progressBar.setVisibility(View.INVISIBLE);
            activity.progressBar.setProgress(0);
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            MainActivity activity = activityWeakReference.get();
            if (activity == null||activity.isFinishing()){
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }
    }

    public static Drawable loadImageFromWebOperations(String url) {
        System.out.println(url);
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public static int generateRandomNumber(int minbound, int maxbound){
        //min inclusive, max exclusive

        if (minbound >= maxbound) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((maxbound - minbound) + 1) + minbound;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
