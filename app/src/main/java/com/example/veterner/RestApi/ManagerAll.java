package com.example.veterner.RestApi;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.veterner.Model.AnswersModel;
import com.example.veterner.Model.AsiModel;
import com.example.veterner.Model.AskQuestionModel;
import com.example.veterner.Model.DeleteAnswerModel;
import com.example.veterner.Model.LoginModel;
import com.example.veterner.Model.PetModel;
import com.example.veterner.Model.RegisterPojo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;

public class ManagerAll extends BaseManger{


    private  static ManagerAll ourInstance = new ManagerAll();

    public  static synchronized ManagerAll getInstance()
    {
        return  ourInstance;
    }

    public Call<RegisterPojo> kayitOl(String mail , String kadi, String parola)
    {
        Call<RegisterPojo> x = getRestApi().registerUser(mail,kadi,parola);
        return  x ;
    }

    public Call<LoginModel> girisYap(String mail , String parola)
    {
        Call<LoginModel> x = getRestApi().loginUser(mail,parola);
        return  x ;
    }

    public Call<List<PetModel>> getPets(String id)
    {
        Call<List<PetModel>> x = getRestApi().getPets(id);
        return  x ;
    }

    public Call<AskQuestionModel> soruSor(ContentResolver resolver, String id, String soru, String filePath) {
        MultipartBody.Part file = null;
        if (filePath != null && !filePath.isEmpty()) {
            File original = FileUtils.getFile(filePath);

            Uri deneme = Uri.fromFile(original);

            String testUri = null;
            String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
            if (extension != null) {
                testUri = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }

            MediaType mediaType = MediaType.parse(testUri);
            RequestBody filePart = RequestBody.create(
                    mediaType,
                    original
            );

            file = MultipartBody.Part.createFormData("photo", original.getName(), filePart);
        }
        RequestBody reqId = RequestBody.create(MultipartBody.FORM, id);
        RequestBody reqSoru = RequestBody.create(MultipartBody.FORM, soru);

        Call<AskQuestionModel> x = getRestApi().soruSor(reqId, reqSoru, file);
        return x;
    }

    public Call<List<AnswersModel>> getAnswers(String mus_id) {
        Call<List<AnswersModel>> x = getRestApi().getAnswers(mus_id);
        return x;
    }


    public Call<DeleteAnswerModel> deleteAnswer(String cevap, String soru) {
        Call<DeleteAnswerModel> x = getRestApi().deleteAnswer(cevap, soru);
        return x;
    }

    public Call<List<AsiModel>> getAsi(String id) {
        Call<List<AsiModel>> x = getRestApi().getAsi(id);
        return x;
    }
    public Call<List<AsiModel>> getGecmisAsi(String id,String pet_id) {
        Call<List<AsiModel>> x = getRestApi().getGecmisAsi(id,pet_id);
        return x;
    }

}


