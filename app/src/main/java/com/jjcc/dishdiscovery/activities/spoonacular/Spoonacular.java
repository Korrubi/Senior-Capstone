package com.jjcc.dishdiscovery.activities.spoonacular;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.adapters.ComplexRecipeAdapter;
import com.jjcc.dishdiscovery.activities.spoonacular.adapters.RandomRecipeAdapter;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.ComplexRecipeResponseListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RecipeClickListener;
import com.jjcc.dishdiscovery.activities.spoonacular.models.complexSearch.ComplexRecipeApiResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Spoonacular extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    ComplexRecipeAdapter complexRecipeAdapter;
    RecyclerView recyclerView;
    List<String> cuisinestr = new ArrayList<>();
    List<String> querystr = new ArrayList<>();
    SearchView searchView;


    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ContentValues.TAG, "Spoonacular start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spoonacular);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        //Just grab the string using key
        String cuisine = getIntent().getStringExtra("cuisine");

        searchView = findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                querystr.clear();
                cuisinestr.add(cuisine);
                querystr.add(query);
                manager.getComplexRecipes(complexRecipeResponselistener, cuisinestr, querystr);
                Log.i("TAG Name", "I'm Spoonacular.java: " + cuisine + query);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        //Sanity check for passed in cuisine name
        Log.i("TAG Name", "I'm Spoonacular.java: " + cuisine );
        manager = new RequestManager(this);
        manager.getComplexRecipes((ComplexRecipeResponseListener) complexRecipeResponselistener, Collections.singletonList(cuisine), querystr);
        dialog.show();
    }

//    private  final RandomRecipeResponseListener randomRecipeResponselistener = new RandomRecipeResponseListener() {
//        @Override
//        public void didFetch(RandomRecipeApiResponse response, String message) {
//            dialog.dismiss();
//            recyclerView = findViewById(R.id.recycler_random);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new GridLayoutManager(Spoonacular.this, 1));
//            randomRecipeAdapter = new RandomRecipeAdapter(Spoonacular.this, response.recipes, recipeClickListener);
//            recyclerView.setAdapter(randomRecipeAdapter);
//        }
//
//        @Override
//        public void didError(String message) {
//            Toast.makeText(Spoonacular.this, message, Toast.LENGTH_SHORT).show();
//        }
//    };


    private  final ComplexRecipeResponseListener complexRecipeResponselistener = new ComplexRecipeResponseListener() {
        @Override
        public void didFetch(ComplexRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_complex);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(Spoonacular.this, 1));
            complexRecipeAdapter = new ComplexRecipeAdapter(Spoonacular.this, response.results, recipeClickListener);
            recyclerView.setAdapter(complexRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(Spoonacular.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {

//            Log.i(ContentValues.TAG, "Click");
//            //Intent intent = new Intent(Spoonacular.this, MealFragment.class);
//
//
//            //make bundle
//            Bundle bundle = new Bundle();
//            bundle.putString("id", id);
//
//
//            //intent.putExtras(bundle);
//
//
//            //pass spoonaculer intent with id to mealframent as bundle
//            startActivity(new Intent(Spoonacular.this, MealFragment.class).putExtras(bundle));
//            Log.i(ContentValues.TAG, "Click");

            startActivity(new Intent(Spoonacular.this, RecipeInformationActivity.class).putExtra("id", id));
        }
    };



}
