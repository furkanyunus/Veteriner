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

import com.example.veterner.Adapter.PetAdapter;
import com.example.veterner.Model.PetModel;
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


public class UserPetsFragment extends Fragment {


    View view;
    private RecyclerView petListrecyclerview;
    private PetAdapter petAdapter;
    private List<PetModel> petList;
    private FragmentDegistir fragmentDegistir;
    private String mus_id;
    private PaylasilanReferanslariAl paylasilanReferanslariAl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_user_pets, container, false);
        Define();
        getPets("71");
        return view;
    }

    public void Define(){
        petList=new ArrayList<>();
        petListrecyclerview=view.findViewById(R.id.petListrecyclerview);
        RecyclerView.LayoutManager mng=new GridLayoutManager(getContext(),1);
        petListrecyclerview.setLayoutManager(mng);
        fragmentDegistir=new FragmentDegistir(getContext());
        paylasilanReferanslariAl=new PaylasilanReferanslariAl(getActivity());
        mus_id=paylasilanReferanslariAl.getSession().getString("id",null);
    }



    public void getPets(String mus_id){
        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(mus_id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                if (response.body().get(0).isTf())
                {
                    petList=response.body();
                    petAdapter=new PetAdapter(petList,getContext());
                    petListrecyclerview.setAdapter(petAdapter);
                    Toast.makeText(getContext(),"Kayitli" + petList.size() + "petiniz bulunmaktadir.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"Kayitli petiniz bulunmamaktadir.",Toast.LENGTH_LONG).show();
                    fragmentDegistir.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }
}