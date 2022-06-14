package com.jjcc.dishdiscovery.activities.spoonacular.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RecipeClickListener;
import com.jjcc.dishdiscovery.activities.spoonacular.models.SimilarRecipe.SimilarRecipeApiResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<SimilarRecipeViewHolder>{

    Context context;
    List<SimilarRecipeApiResponse> list;
    RecipeClickListener listener;

    public SimilarRecipeAdapter(Context context, List<SimilarRecipeApiResponse> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_similar_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarRecipeViewHolder holder, int position) {
        holder.textView_similar_title.setText(list.get(position).title);
        holder.textView_similar_title.setSelected(true);
        Picasso.get().load("https://spoonacular.com/recipeImages/" + list.get(position).id + "-556x370." + list.get(position).imageType).into(holder.imageView_similar);

        holder.similar_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SimilarRecipeViewHolder extends RecyclerView.ViewHolder {

    CardView similar_recipe;
    TextView textView_similar_title;
    ImageView imageView_similar;
    public SimilarRecipeViewHolder(@NonNull View itemView) {
        super(itemView);

        similar_recipe = itemView.findViewById(R.id.similar_recipe_holder);
        textView_similar_title = itemView.findViewById(R.id.textView_similar_title);
        imageView_similar = itemView.findViewById(R.id.imageView_similar);
    }
}
