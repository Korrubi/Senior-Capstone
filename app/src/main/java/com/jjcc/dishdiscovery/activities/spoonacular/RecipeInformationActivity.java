package com.jjcc.dishdiscovery.activities.spoonacular;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.adapters.IngredientsAdapter;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RecipeInformationListener;
import com.jjcc.dishdiscovery.activities.spoonacular.models.RecipeInformation.RecipeInformationResponse;
import com.squareup.picasso.Picasso;

public class RecipeInformationActivity extends AppCompatActivity {
    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_information);

        //call find view
        findView();

        //get id from string extra
        id = Integer.parseInt(getIntent().getStringExtra("id"));

        manager = new RequestManager(this);
        manager.getRecipeInformation(recipeInformationLister, id);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Detils...");
        dialog.show();
    }

    //get views from XML
    private void findView() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
    }


    //make new listener and set data from api call
    private final RecipeInformationListener recipeInformationLister = new RecipeInformationListener() {

        //if fetch set data
        @Override
        public void didFetch(RecipeInformationResponse response, String message) {
            dialog.dismiss();
            //title [i.e chicken sandwich]
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(response.summary);

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
}
