package com.example.veterner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterner.Model.AnswersModel;
import com.example.veterner.Model.DeleteAnswerModel;
import com.example.veterner.Model.PetModel;
import com.example.veterner.R;
import com.example.veterner.RestApi.ManagerAll;
import com.example.veterner.Utils.Warnings;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder>{
    List<AnswersModel> list;
    Context context;

    public AnswersAdapter(List<AnswersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.cevapitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.cevapSoruText.setText("Soru : " + list.get(position).getSoru().toString());
        holder.cevapCevapText.setText("Cevap : " + list.get(position).getCevap().toString());

        holder.cevapSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(list.get(position).getCevapid().toString(),list.get(position).getSoruid().toString(),position);

            }
        });

    }
    public void deleteToList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();//silindikden sonra itemlerin indexlerinin yeniden düzenlenmesi yani listenin yenilenmesi için kullandık


    }
    public void deleteService(String cevapid,String soruid,final int pos)
    {
        Call<DeleteAnswerModel> req= ManagerAll.getInstance().deleteAnswer(cevapid,soruid);
        req.enqueue(new Callback<DeleteAnswerModel>()
        {
            @Override
            public void onResponse(Call<DeleteAnswerModel> call, Response<DeleteAnswerModel> response) {

                if(response.body().isTf())
                {
                    if(response.isSuccessful())
                    {
                        Toast.makeText(context,response.body().getText(),Toast.LENGTH_LONG).show();
                        deleteToList(pos);
                    }

                }
                else
                {
                    Toast.makeText(context,response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }



            @Override
            public void onFailure(Call<DeleteAnswerModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cevapSoruText, cevapCevapText;
        MaterialButton cevapSilButon;

        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            cevapSoruText = (TextView) itemView.findViewById(R.id.cevapSoruText);
            cevapCevapText =(TextView) itemView.findViewById(R.id.cevapCevapText);
            cevapSilButon =(MaterialButton) itemView.findViewById(R.id.cevapSilButon);
        }
    }
}
