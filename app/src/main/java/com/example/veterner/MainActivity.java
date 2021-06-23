package com.example.veterner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.veterner.Fragments.HomeFragment;
import com.example.veterner.Utils.FragmentDegistir;
import com.example.veterner.Utils.PaylasilanReferanslariAl;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences getSharedPreferences;
    private PaylasilanReferanslariAl paylasilanReferanslariAl;
    private MaterialButton anasayfaButon,cikisButon;
    private  FragmentDegistir fragmentDegistir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragment();
        tanimla();
        kontrol();
        action();
    }

    private void getFragment() {
        fragmentDegistir = new FragmentDegistir( MainActivity.this);
        fragmentDegistir.change(new HomeFragment());
    }

    public void tanimla(){
        paylasilanReferanslariAl=new PaylasilanReferanslariAl(MainActivity.this);
        getSharedPreferences=paylasilanReferanslariAl.getSession();
        anasayfaButon=(MaterialButton)findViewById(R.id.anasayfaButon);
        cikisButon=(MaterialButton)findViewById(R.id.cikisButon);
    }


    public void kontrol(){
        if (getSharedPreferences.getString("id",null)==null && getSharedPreferences.getString("mailadres",null)==null
                && getSharedPreferences.getString("username",null)==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            fragmentDegistir.changePreview();
        }
    }

    public void action(){
       anasayfaButon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               fragmentDegistir.change(new HomeFragment());
           }
       });

        cikisButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaylasilanReferanslariAl paylasilanReferanslariAl=new PaylasilanReferanslariAl(MainActivity.this);
                paylasilanReferanslariAl.deleteToSession();
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}