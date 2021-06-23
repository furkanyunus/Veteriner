package com.example.veterner.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.veterner.Adapter.AnswersAdapter;
import com.example.veterner.MainActivity;
import com.example.veterner.Model.AnswersModel;
import com.example.veterner.Model.AskQuestionModel;
import com.example.veterner.R;
import com.example.veterner.RestApi.ManagerAll;
import com.example.veterner.Utils.FragmentDegistir;
import com.example.veterner.Utils.PaylasilanReferanslariAl;
import com.example.veterner.Utils.Warnings;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View view;//idlere erişmek için
    private LinearLayout petlerimLayout,sorusorlinearLayout,cevapLayout,sanalKarneLayout;
    private LinearLayout asiTakipLayout;
    private FragmentDegistir fragmentDegistir;
    private PaylasilanReferanslariAl paylasilanReferanslariAl;
    private String id;
    private AnswersAdapter answersAdapter;
    private  List<AnswersModel> answerList;
    private ImageView imagePreview;

    String currentPhotoPath;
    private Uri photoUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        define();
        action();
        return  view;
    }

    public void define(){
        petlerimLayout= view.findViewById(R.id.petlerimLayout);
        fragmentDegistir=new FragmentDegistir(getContext());
        sorusorlinearLayout = view.findViewById(R.id.sorusorlinearLayout);
        cevapLayout= view.findViewById(R.id.cevapLayout);
        answerList=new ArrayList<>();
        paylasilanReferanslariAl =new PaylasilanReferanslariAl(getActivity());
        id=paylasilanReferanslariAl.getSession().getString("id",null);
        asiTakipLayout=(LinearLayout) view.findViewById(R.id.asiTakipLayout);
        sanalKarneLayout=(LinearLayout) view.findViewById(R.id.sanalKarneLayout);
    }

    public static int REQ_FILE_ATTACHMENT = 1451;
    public static int REQ_PICK_IMAGE = 1452;
    public static int REQ_TAKE_PHOTO = 1453;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1453;

    public void openQuestionAlert() {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = this.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.sorusoralertlayout, null);

        final EditText sorusorEditText = view.findViewById(R.id.sorusorEditText);
        MaterialButton sorusorButon = (MaterialButton) view.findViewById(R.id.sorusorButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();

        imagePreview = (ImageView) view.findViewById(R.id.imagePreview);
        Button btnPhoto = (Button) view.findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.d("PHOTO", "ERROR!");
                        // Error occurred while creating the File
                    }
                    Log.d("PHOTO", "SHOT!");
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoUri = FileProvider.getUriForFile(getActivity(), "com.example.veterner.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(takePictureIntent, 1453);
                    }
                }
                /*
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(view.getContext(), "Gerekli kamera uygulaması bulunamadı.", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });

        sorusorButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soru = sorusorEditText.getText().toString();
                sorusorEditText.setText("");

                alertDialog.cancel();
                askQuestion(id, soru,alertDialog);
            }
        });
        alertDialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQ_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Log.d("OK!", "OK");
        }
    }

    public void setPhotoPreview() {
        imagePreview.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath));
    }

    public void action(){
        petlerimLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              fragmentDegistir.change(new UserPetsFragment());
            }
        });
        sorusorlinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuestionAlert();
            }
        });
        cevapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAnswers(id);
            }
        });

       asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fragmentDegistir.change(new AsiFragment());
            }
        });
        sanalKarneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              fragmentDegistir.change(new SanalKarnePetler());
            }
        });
    }



    public void askQuestion(String mus_id, String text, final AlertDialog alr) {


        final retrofit2.Call<AskQuestionModel> req = ManagerAll.getInstance().soruSor(getActivity().getContentResolver(), mus_id, text, currentPhotoPath);
        req.enqueue(new Callback<AskQuestionModel>() {

            @Override
            public void onResponse(retrofit2.Call<AskQuestionModel> call, Response<AskQuestionModel> response) {
                if (response.body().isTf()) {//yani soru başarılı bir şekilde iletildiyse
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    alr.cancel();

                } else {
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AskQuestionModel> call, Throwable t) {
                Log.d("HATA", "Bunu bekliyorduk");
            }
        });

    }



    public void getAnswers(String mus_id){

        retrofit2.Call<List<AnswersModel>> req=ManagerAll.getInstance().getAnswers(mus_id);
        req.enqueue(new Callback<List<AnswersModel>>() {
            @Override
            public void onResponse(retrofit2.Call<List<AnswersModel>> call, Response<List<AnswersModel>> response) {
                if(response.body().get(0).isTf()){
                    if(response.isSuccessful()){

                        answerList=response.body();
                        answersAdapter =new AnswersAdapter(answerList,getContext());
                        openAnswerAlert();//başarılıysa direk açılsın

                    }


                }
                else{

                    Toast.makeText(getContext(),"Herhangibir cevap yok...",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<AnswersModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }

        });

    }




    public void openAnswerAlert() {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = this.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.cevapalertlayout, null);

        RecyclerView cevapRecylerView=view.findViewById(R.id.cevapRecylerView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getContext(),1);
        cevapRecylerView.setLayoutManager(layoutManager);
        cevapRecylerView.setAdapter(answersAdapter);

        alertDialog.show();

    }

}