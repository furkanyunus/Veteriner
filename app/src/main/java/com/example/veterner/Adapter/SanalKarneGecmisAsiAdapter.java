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
import com.example.veterner.Model.AsiModel;
import com.example.veterner.Model.PetModel;
import com.example.veterner.R;
import com.example.veterner.Utils.FragmentDegistir;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarneGecmisAsiAdapter extends RecyclerView.Adapter<SanalKarneGecmisAsiAdapter.ViewHolder>{
    List<AsiModel> list;
    Context context;

    public SanalKarneGecmisAsiAdapter(List<AsiModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sanalkarnegecmislayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.sanalKarneGecmisAsiText.setText(list.get(position).getAsiisim().toString()+" Asisi yapildi. ");
        holder.sanalKarneGecmisAsiBilgi.setText(list.get(position).getPetisim().toString()+" isimli petinize " +
                list.get(position).getAsitarih()+ " tarihinde "+list.get(position).getAsiisim()+ " asisi yapilmistir.");
        Picasso.get().load(list.get(position).getPetresim().toString()).into(holder.sanalKarneGecmisAsiImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sanalKarneGecmisAsiText,sanalKarneGecmisAsiBilgi;
        CircleImageView sanalKarneGecmisAsiImage;

        public ViewHolder(View itemView) {
            super(itemView);
            sanalKarneGecmisAsiText=(TextView) itemView.findViewById(R.id.sanalKarneGecmisAsiText);
            sanalKarneGecmisAsiBilgi=(TextView) itemView.findViewById(R.id.sanalKarneGecmisAsiBilgi);
            sanalKarneGecmisAsiImage=(CircleImageView) itemView.findViewById(R.id.sanalKarneGecmisAsiImage);
        }
    }

}
