package com.example.veterner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterner.Model.RegisterPojo;
import com.example.veterner.RestApi.ManagerAll;
import com.example.veterner.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {
    private Button kayitOlButon;
    private EditText registerPassword,registerUserName,registerMailAdress;
    private TextView registerText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
        registerToUser();
        changeActivity();
    }



    public void tanimla(){//olusturdugumuz nesnelere atama yaptık hangi ıdlerle eşlleştiğini gösteriri
     kayitOlButon=(Button)findViewById(R.id.kayitOlButon);
     registerPassword=(EditText) findViewById(R.id.registerPassword);
     registerUserName=(EditText)findViewById(R.id.registerUserName);
     registerMailAdress=(EditText)findViewById(R.id.registerMailAdress);
     registerText=(TextView) findViewById(R.id.registerText);
    }

    public void registerToUser(){// butona tıklayınca kayıt edecek
        kayitOlButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//çagırıyoruz
                String mail=registerMailAdress.getText().toString();
                String userN=registerUserName.getText().toString();
                String pass=registerPassword.getText().toString();

                register(mail,userN,pass);
                Delete();

            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(KayitOlActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void changeActivity(){
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KayitOlActivity.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Delete(){
        registerPassword.setText("");
        registerUserName.setText("");
        registerMailAdress.setText("");
    }



    public void register(String userMailAdres,String userName,String userPass)
    {
        Call<RegisterPojo> req= ManagerAll.getInstance().kayitOl(userMailAdres,userName,userPass);
        req.enqueue(new Callback<RegisterPojo>() {

            @Override
            public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {//cevap başarılıysa

                if (response.body().isTf()){

                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(KayitOlActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override//hata varsa
            public void onFailure(Call<RegisterPojo> call, Throwable t) {

                Toast.makeText(getApplicationContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }
}