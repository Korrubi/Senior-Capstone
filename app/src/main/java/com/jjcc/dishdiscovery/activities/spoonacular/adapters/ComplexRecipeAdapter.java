package com.jjcc.dishdiscovery.activities.spoonacular.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
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
import com.jjcc.dishdiscovery.activities.spoonacular.models.complexSearch.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComplexRecipeAdapter extends RecyclerView.Adapter<ComplexRecipeViewHolder> {
    Context context;
    List<Result> list;
    RecipeClickListener listener;

    public ComplexRecipeAdapter(Context context, List<Result> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ComplexRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComplexRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_complex_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComplexRecipeViewHolder holder, int position) {

        holder.textView_complex_title.setText(list.get(position).title);
        holder.textView_complex_title.setSelected(true);
        Picasso.get().load(list.get(position).image).into(holder.imageView_complex_food);

        holder.complex_list_container.setOnClickListener(new View.OnClickListener() {
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


class ComplexRecipeViewHolder extends RecyclerView.ViewHolder {

    CardView complex_list_container;
    TextView textView_complex_title, textView_servings, textView_likes, textView_time;
    ImageView imageView_complex_food;

    public ComplexRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        complex_list_container = itemView.findViewById(R.id.complex_list_container);
        textView_complex_title = itemView.findViewById(R.id.textView_complex_title);
        imageView_complex_food = itemView.findViewById(R.id.imageView_complex_food);
    }
}
