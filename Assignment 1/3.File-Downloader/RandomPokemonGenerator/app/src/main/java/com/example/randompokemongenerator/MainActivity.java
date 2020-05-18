package com.example.randompokemongenerator;


import android.graphics.drawable.Drawable;

import android.os.AsyncTask;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;



import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
            String url = pokemon.getSprites().getFrontDefault();
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


            Toast.makeText(activity.getApplicationContext() , "Download Complete", Toast.LENGTH_SHORT).show();

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

}
