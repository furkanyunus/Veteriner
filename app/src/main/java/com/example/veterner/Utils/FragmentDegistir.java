package com.example.veterner.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.veterner.Fragments.HomeFragment;
import com.example.veterner.R;
public class FragmentDegistir {

    private Context context;

    private Fragment currFragment;

    public FragmentDegistir(Context context) {
        this.context = context;
    }


    public void change(Fragment fragment){
        currFragment = fragment;
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

    public void changeWithParameters(Fragment fragment,String petId){
        currFragment = fragment;

        Bundle bundle=new Bundle();
        bundle.putString("petid",petId);
        fragment.setArguments(bundle);

        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

    public void changePreview() {
        if (currFragment instanceof HomeFragment) {
            ((HomeFragment) currFragment).setPhotoPreview();
        }
    }
}
