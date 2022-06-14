package com.jjcc.dishdiscovery.activities.spoonacular.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.models.Step;

import java.util.List;

public class InstructionStepsAdapter extends RecyclerView.Adapter<InstructionStepViewHolder> {

    Context context;
    List<Step> list;

    public InstructionStepsAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instuction_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {

        holder.textView_instruction_number.setText(String.valueOf(list.get(position).number));
        holder.getTextView_instruction_title.setText(list.get(position).step);

        holder.recycler_instructions_ingredients.setHasFixedSize(true);
        holder.recycler_instructions_ingredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsIngredientsAdapter instructionsIngredientsAdapter = new InstructionsIngredientsAdapter(context, list.get(position).ingredients);
        holder.recycler_instructions_ingredients.setAdapter(instructionsIngredientsAdapter);


        holder.recycler_instruction_equipments.setHasFixedSize(true);
        holder.recycler_instruction_equipments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsEquipmentAdapter instructionsEquipmentAdapter = new InstructionsEquipmentAdapter(context, list.get(position).equipment);
        holder.recycler_instruction_equipments.setAdapter(instructionsEquipmentAdapter);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionStepViewHolder extends RecyclerView.ViewHolder {

    TextView textView_instruction_number, getTextView_instruction_title;
    RecyclerView recycler_instruction_equipments, recycler_instructions_ingredients;

    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instruction_number = itemView.findViewById(R.id.textView_instruction_number);
        getTextView_instruction_title = itemView.findViewById(R.id.textView_instruction_title);
        recycler_instruction_equipments = itemView.findViewById(R.id.recycler_instructions_equipment);
        recycler_instructions_ingredients = itemView.findViewById(R.id.recycler_instructions_ingredients);
    }
}
