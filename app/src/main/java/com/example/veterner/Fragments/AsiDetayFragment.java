package com.example.veterner.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterner.Adapter.SanalKarneGecmisAsiAdapter;
import com.example.veterner.Model.AsiModel;
import com.example.veterner.R;
import com.example.veterner.RestApi.ManagerAll;
import com.example.veterner.Utils.FragmentDegistir;
import com.example.veterner.Utils.PaylasilanReferanslariAl;
import com.example.veterner.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsiDetayFragment extends Fragment {
    private View view;
    private String musid;
    private String petId;
    private PaylasilanReferanslariAl paylasilanReferanslariAl;
    private RecyclerView asiDetayRecView;
    private SanalKarneGecmisAsiAdapter adapter;
    private List<AsiModel> list;
    private FragmentDegistir fragmentDegistir;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi_detay, container, false);
        tanimla();
        getGecmisAsi();
        return view;
    }

    public void tanimla() {

        petId = getArguments().getString("petid").toString();
        paylasilanReferanslariAl = new PaylasilanReferanslariAl(getActivity());
        musid = paylasilanReferanslariAl.getSession().getString("id", null);
         asiDetayRecView = view.findViewById(R.id.asiDetayRecView);
      RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
      asiDetayRecView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        fragmentDegistir = new FragmentDegistir(getContext());


    }

    public void getGecmisAsi() {

        Call<List<AsiModel>> req = ManagerAll.getInstance().getGecmisAsi("71", petId);
        req.enqueue(new Callback<List<AsiModel>>() {

            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {

               // Log.i("gecmisasilar",response.body().toString());
                if (response.body().get(0).isTf()) {

                    list = response.body();
                    adapter=new SanalKarneGecmisAsiAdapter(list,getContext());
                    asiDetayRecView.setAdapter(adapter);
                    Toast.makeText(getContext(), "Petinize ait " + list.size() + " adet geçmişte yapılan aşı bulunmaktadır.", Toast.LENGTH_LONG).show();
                } else {
                    FragmentDegistir fragmentDegistir=new FragmentDegistir(getContext());
                    fragmentDegistir.change(new SanalKarnePetler());
                    Toast.makeText(getContext(), "Petinize ait geçmişte yapılan herhangi bir bulunmamaktadır.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }

}