package com.example.veterner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterner.Fragments.AsiDetayFragment;
import com.example.veterner.Model.PetModel;
import com.example.veterner.R;
import com.example.veterner.Utils.FragmentDegistir;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarnePetAdapter extends RecyclerView.Adapter<SanalKarnePetAdapter.ViewHolder>{
    List<PetModel> list;
    Context context;

    public SanalKarnePetAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sanalkarnepetlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.sanalKarnePetText.setText(list.get(position).getPetisim().toString());
        holder.sanalKarneBilgiText.setText(list.get(position).getPetisim().toString()+" isimli "+
                list.get(position).getPettur()+" türünde "+list.get(position).getPetcins()+ " cinsine ait gecmis" +
                " asilari gormek icin tiklayiniz");

        Picasso.get().load(list.get(position).getPetresim()).into(holder.sanalKarnePetImage);
        holder.sanalLayoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDegistir fragmentDegistir =new FragmentDegistir(context);
                fragmentDegistir.changeWithParameters(new AsiDetayFragment(),list.get(position).getPetid());//***
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sanalKarnePetText,sanalKarneBilgiText;
        CircleImageView sanalKarnePetImage;
        CardView sanalLayoutCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            sanalKarnePetText=(TextView) itemView.findViewById(R.id.sanalKarnePetText);
            sanalKarneBilgiText=(TextView) itemView.findViewById(R.id.sanalKarneBilgiText);
            sanalKarnePetImage=(CircleImageView) itemView.findViewById(R.id.sanalKarnePetImage);
            sanalLayoutCardView=(CardView) itemView.findViewById(R.id.sanalLayoutCardView);
        }
    }

}
