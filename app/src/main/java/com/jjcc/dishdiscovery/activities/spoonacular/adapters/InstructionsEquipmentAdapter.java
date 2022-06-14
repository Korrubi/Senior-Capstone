package com.jjcc.dishdiscovery.activities.spoonacular.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.models.Equipment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsEquipmentAdapter extends  RecyclerView.Adapter<InstructionEquipmentViewHolder> {

    Context context;
    List<Equipment> list;

    public InstructionsEquipmentAdapter(Context context, List<Equipment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionEquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionEquipmentViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instruction_step_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionEquipmentViewHolder holder, int position) {
        holder.textView_instructions_step_item.setText(list.get(position).name);
        holder.textView_instructions_step_item.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/equipment_250x250/" + list.get(position).image).into(holder.imageView_instruction_step_item);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionEquipmentViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_instruction_step_item;
    TextView textView_instructions_step_item;
    public InstructionEquipmentViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instructions_step_item  = itemView.findViewById(R.id.textView_instructions_step_item);
        imageView_instruction_step_item  = itemView.findViewById(R.id.imageView_instruction_step_item);
    }
}