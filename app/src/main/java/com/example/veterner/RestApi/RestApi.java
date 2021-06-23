package com.example.veterner.RestApi;

import com.example.veterner.Model.AsiModel;
import com.example.veterner.Model.AskQuestionModel;
import com.example.veterner.Model.AnswersModel;
import com.example.veterner.Model.DeleteAnswerModel;
import com.example.veterner.Model.LoginModel;
import com.example.veterner.Model.PetModel;
import com.example.veterner.Model.RegisterPojo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApi {
    @FormUrlEncoded
    @POST("/veterinerservis/kayitol.php")
    Call<RegisterPojo> registerUser(@Field("mailAdres") String mailAdres, @Field("kadi") String kAdi, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("/veterinerservis/girisyap.php")
    Call<LoginModel> loginUser(@Field   ("mailadres") String mailAddress, @Field("sifre") String pass );

    @FormUrlEncoded
    @POST("/veterinerservis/petlerim.php")
    Call<List<PetModel>> getPets(@Field("musid") String mus_id);

    @Multipart
    @POST("/veterinerservis/sorusor.php")
    Call<AskQuestionModel> soruSor(@Part("id") RequestBody id, @Part("soru") RequestBody soru, @Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("/veterinerservis/cevaplar.php")
    Call<List<AnswersModel>> getAnswers(@Field("mus_id") String mus_id);
    @FormUrlEncoded
    @POST("/veterinerservis/cevapsil.php")
    Call<DeleteAnswerModel> deleteAnswer(@Field("cevap") String cevapid, @Field("soru") String soruid);

    @FormUrlEncoded
    @POST("/veterinerservis/asitakip.php")
    Call<List<AsiModel>> getAsi(@Field("id") String id);

    @FormUrlEncoded
    @POST("/veterinerservis/gecmisasi.php")
    Call<List<AsiModel>> getGecmisAsi(@Field("id") String id,@Field("petid") String pet_id);
}




