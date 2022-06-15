package com.jjcc.dishdiscovery.activities.spoonacular;


import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.*;

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
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;

public class SpoonacularRecommend extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    ComplexRecipeAdapter complexRecipeAdapter;
    RecyclerView recyclerView;
    //List<String> cuisinestr = new ArrayList<>();
//    String cuisine;
//    List<String> tags = new ArrayList<>();
    List<String> intolerances = new ArrayList<>();
    List<String> diet = new ArrayList<>();
    List<String> cuisine = new ArrayList<>();
    SearchView searchView;
    List<String> tags = new ArrayList<>();

    Random random = new Random();
    int min = 0;
    int max = 50;
    int offset = random.nextInt(max + min);

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ContentValues.TAG, "Spoonacular start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spoonacular);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        //Retrieve list information from bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        intolerances = (ArrayList<String>) bundle.getSerializable("Allergy");
        diet = (ArrayList<String>) bundle.getSerializable("Diet");
        cuisine = (ArrayList<String>) bundle.getSerializable("Cuisine");

        Log.i(ContentValues.TAG, "Intolerances: " + intolerances.toString());
        Log.i(ContentValues.TAG, "Diet: " + diet.toString());
        Log.i(ContentValues.TAG, "Cuisine: " + cuisine.toString());

        searchView = findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getComplexRecipes(complexRecipeResponselistener, tags, 0);
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
        Log.i("TAG Name", "I'm Spoonacular.java: " + cuisine);
        manager = new RequestManager(this);
        tags.clear();
//        offset*=10;
        //offset*=5;

       // manager.getComplexRecipesRecommend((ComplexRecipeResponseListener) complexRecipeResponselistener1, intolerances, diet, cuisine, 0);
       // Log.i(ContentValues.TAG, "MAX: " + max);


        manager.getComplexRecipesRecommend((ComplexRecipeResponseListener) complexRecipeResponselistener, intolerances, diet, cuisine, offset);
        Log.i(ContentValues.TAG, "OFFSET: " + offset);
//        final Handler handler = new Handler();
//        int finalOffset = offset;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                manager.getComplexRecipesRecommend((ComplexRecipeResponseListener) complexRecipeResponselistener, intolerances, diet, cuisine, finalOffset);
//                Log.i(ContentValues.TAG, "OFFSET: " + finalOffset);
//                dialog.show();
//            }
//        }, 100);
        //tags.add(cuisine);
        //manager.getComplexRecipes((ComplexRecipeResponseListener) complexRecipeResponselistener, Collections.singletonList(cuisine), querystr);


//
//        Log.i(ContentValues.TAG, "MAX: " + max);

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
    private final ComplexRecipeResponseListener complexRecipeResponselistener1 = new ComplexRecipeResponseListener() {
        @Override
        public void didFetch(ComplexRecipeApiResponse response, String message) {
            Log.i(ContentValues.TAG, "Total Before Result: " + response.totalResults);
            max = response.totalResults;

        }

        @Override
        public void didError(String message) {
            Toast.makeText(SpoonacularRecommend.this, message, Toast.LENGTH_SHORT).show();
        }
    };


    private final ComplexRecipeResponseListener complexRecipeResponselistener = new ComplexRecipeResponseListener() {
        @Override
        public void didFetch(ComplexRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_complex);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(SpoonacularRecommend.this, 1));
            complexRecipeAdapter = new ComplexRecipeAdapter(SpoonacularRecommend.this, response.results, recipeClickListener);
            recyclerView.setAdapter(complexRecipeAdapter);

            Log.i(ContentValues.TAG, "TotalResult: " + response.totalResults);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(SpoonacularRecommend.this, message, Toast.LENGTH_SHORT).show();
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

            startActivity(new Intent(SpoonacularRecommend.this, RecipeInformationActivity.class).putExtra("id", id));
        }
    };


}
