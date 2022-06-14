package com.jjcc.dishdiscovery.activities.spoonacular;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.adapters.IngredientsAdapter;
import com.jjcc.dishdiscovery.activities.spoonacular.adapters.SimilarRecipeAdapter;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RecipeClickListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RecipeInformationListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.SimilarRecipesListener;
import com.jjcc.dishdiscovery.activities.spoonacular.models.RecipeInformation.RecipeInformationResponse;
import com.jjcc.dishdiscovery.activities.spoonacular.models.SimilarRecipe.SimilarRecipeApiResponse;
import com.jjcc.dishdiscovery.activities.ui.meal.MealFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import xyz.hanks.library.bang.SmallBangView;

public class RecipeInformationActivity extends AppCompatActivity {
    int id;
    String recipeName;
    SmallBangView imageView;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients, recycler_similar;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_information);

        //for the heart/like animation
        imageView = findViewById(R.id.imageViewAnimation);
        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageView.isSelected()) {
                            imageView.setSelected(false);
                        } else {
                            // if not selected only
                            // then show animation.
                            imageView.setSelected(true);
                            imageView.likeAnimation();
                        }
                    }
                });

        //Rating Bar
        RatingBar rBar = findViewById(R.id.rBar);
        if (rBar != null) {
            RatingBar button = findViewById(R.id.rBar);
            if (button != null) {
                button.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
                    public final void onClick(View it) {
                        String msg = String.valueOf(rBar.getRating());
                        Toast.makeText(RecipeInformationActivity.this, ("Rating is: " + msg), Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        }

        //call find view
        findView();

        //get id from string extra
        id = Integer.parseInt(getIntent().getStringExtra("id"));

        manager = new RequestManager(this);
        manager.getRecipeInformation(recipeInformationLister, id);
        manager.getSimilarRecipes(similarRecipesListener, id);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity(String.valueOf(id));
            }
        });
    }

    //get views from XML
    private void findView() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        //textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recycler_similar = findViewById(R.id.recycler_similar);
        button = findViewById(R.id.viewRecipes);
    }


    //make new listener and set data from api call
    private final RecipeInformationListener recipeInformationLister = new RecipeInformationListener() {

        //if fetch set data
        @Override
        public void didFetch(RecipeInformationResponse response, String message) {
            dialog.dismiss();
            //title [i.e chicken sandwich]
            textView_meal_name.setText(response.title);
            recipeName = response.title;
            textView_meal_source.setText(response.sourceName);
            //textView_meal_summary.setText(response.summary);

            //photo of food
            Picasso.get().load(response.image).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeInformationActivity.this, LinearLayoutManager.HORIZONTAL, false));

            ingredientsAdapter = new IngredientsAdapter(RecipeInformationActivity.this, response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);


        }

        //on fail print error
        @Override
        public void didError(String message) {
            Toast.makeText(RecipeInformationActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };


    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeApiResponse> response, String message) {
            recycler_similar.setHasFixedSize(true);
            recycler_similar.setLayoutManager(new LinearLayoutManager(RecipeInformationActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeInformationActivity.this, response, recipeClickListener);
            recycler_similar.setAdapter(similarRecipeAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeInformationActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            Log.i("TAG Name", "This is recipeClickListener: " + id);
            startActivity(new Intent(RecipeInformationActivity.this, RecipeInformationActivity.class).putExtra("id", id));
        }
    };

    public void openNewActivity(String id){
        Intent intent = new Intent(RecipeInformationActivity.this, RecipeInstructionsActivity.class);
        Bundle extras = new Bundle();
        extras.putString("id", id);
        extras.putString("recipeName", recipeName);
        intent.putExtras(extras);
        startActivity(intent);



        //startActivity(new Intent(RecipeInformationActivity.this, RecipeInstructionsActivity.class).putExtra("id", id));
    }
}

