package com.example.veterner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterner.Model.PetModel;
import com.example.veterner.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder>{
    List<PetModel> list;
    Context context;

    public PetAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.petlistitemlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.petLayoutCinsName.setText("Pet cinsi: "+list.get(position).getPetcins().toString());
        holder.petLayoutPetName.setText("Pet ismi: "+list.get(position).getPetisim().toString());
        holder.petLayoutturName.setText("pet türü: "+list.get(position).getPettur().toString());

        Picasso.get().load(list.get(position).getPetresim()).into(holder.petlayoutPetimage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView petLayoutPetName,petLayoutCinsName,petLayoutturName;
        CircleImageView petlayoutPetimage;

        public ViewHolder(View itemView) {
            super(itemView);
            petLayoutPetName=(TextView) itemView.findViewById(R.id.petLayoutPetName);
            petLayoutCinsName=(TextView) itemView.findViewById(R.id.petLayoutCinsName);
            petLayoutturName=(TextView) itemView.findViewById(R.id.petLayoutturName);
            petlayoutPetimage=(CircleImageView) itemView.findViewById(R.id.petlayoutPetimage);
        }
    }
}
