package com.jjcc.dishdiscovery.activities.spoonacular;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.adapters.RandomRecipeAdapter;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RandomRecipeResponseListener;
import com.jjcc.dishdiscovery.activities.spoonacular.models.RandomRecipeApiResponse;

public class Spoonacular extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spoonacular);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        manager = new RequestManager(this);
        manager.getRandomRecipes(randomRecipeResponselistener);
        dialog.show();
    }

    private  final RandomRecipeResponseListener randomRecipeResponselistener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(Spoonacular.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(Spoonacular.this, response.recipes);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(Spoonacular.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
