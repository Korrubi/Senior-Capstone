package com.jjcc.dishdiscovery.activities.spoonacular;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.adapters.InstructionsAdapter;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.InstructionsListener;
import com.jjcc.dishdiscovery.activities.spoonacular.models.AnalyzedInstuctions.InstructionResponse;

import java.util.List;

public class RecipeInstructionsActivity extends AppCompatActivity {
    int id;
    String recipeName;
    TextView textView_meal_instruction_title;
    RecyclerView recycler_meal_instructions;
    InstructionsAdapter instructionsAdapter;
    RequestManager manager;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instructions);

        //call find view
        findView();

        //get id from string extra
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = Integer.parseInt(extras.getString("id"));
        recipeName = extras.getString("recipeName");

        manager = new RequestManager(this);
        manager.getInstructions(instructionsListener, id);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Detils...");
        dialog.show();
    }

    //get views from XML
    private void findView() {
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);
        textView_meal_instruction_title = findViewById(R.id.textView_meal_instruction_title);
    }


    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionResponse> response, String message) {
            dialog.dismiss();
            textView_meal_instruction_title.setText(recipeName);
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeInstructionsActivity.this, LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(RecipeInstructionsActivity.this, response);
            recycler_meal_instructions.setAdapter(instructionsAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeInstructionsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}

