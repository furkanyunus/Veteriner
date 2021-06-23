package com.example.veterner.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterner.Model.AsiModel;
import com.example.veterner.R;
import com.example.veterner.RestApi.ManagerAll;
import com.example.veterner.Utils.FragmentDegistir;
import com.example.veterner.Utils.PaylasilanReferanslariAl;
import com.squareup.picasso.Picasso;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsiFragment extends Fragment {
    private View view;
    private CalendarPickerView calendarPickerView;
    private DateFormat format;
    private Calendar nextYear;//java utilden date ve calander i secmeliyiz
    private Date today;
    private List<AsiModel> asiList;
    private List<Date> dateList;

   private String id;
   private PaylasilanReferanslariAl paylasilanReferanslariAl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi, container, false);
        tanimla();
        getAsi();
      clickToCalendar();
        return view;
    }

    public void tanimla() {

        //fragmentDegistir = new FragmentDegistir(getContext());
        calendarPickerView = view.findViewById(R.id.calenderPickerView);
        format = new SimpleDateFormat("dd/MM/yyyy");
        //şimdi picker view da neleri gösterecegiz biz bi sene sonraya kadar gösterelim
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);//1 yıl sonrasına kadar göster
        today = new Date();


        //gösterme işlemi için
        calendarPickerView.init(today, nextYear.getTime());//gettime ile date formatına aldık

        asiList = new ArrayList<>();
        dateList = new ArrayList<>();
      paylasilanReferanslariAl=new PaylasilanReferanslariAl(getActivity());
      id=paylasilanReferanslariAl.getSession().getString("id",null);

    }

    public void getAsi() {
        Call<List<AsiModel>> req= ManagerAll.getInstance().getAsi(id);
        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                if(response.isSuccessful()){
                    if(response.body().get(0).isTf()){
                        asiList=response.body();
                        for (int i=0;i<asiList.size();i++){
                            String dataString=response.body().get(i).getAsitarih().toString();
                            try {
                                Date date =format.parse(dataString);
                                dateList.add(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        calendarPickerView.init(today,nextYear.getTime())
                                .withHighlightedDates(dateList);
                    }
                }
                else{
                  FragmentDegistir fragmentDegistir=new FragmentDegistir(getContext());
                    fragmentDegistir.change(new HomeFragment());
                    Toast.makeText(getContext(),"Petinize ait asi tarihi mevcut degil...",Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });
    }

 public void clickToCalendar() {
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                for (int i=0;i<dateList.size();i++){
                    if(date.toString().equals(dateList.get(i).toString())){

                     //   Toast.makeText(getContext(),asiList.get(i).getPetisim().toString(),Toast.LENGTH_LONG).show();
                    openQuestionAlert(asiList.get(i).getPetisim().toString(),asiList.get(i).getAsitarih().toString(),
                            asiList.get(i).getAsiisim().toString(),asiList.get(i).getPetresim().toString());
                    }
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public void openQuestionAlert(String petIsmi,String tarih,String asiIsmi,String resimUrl) {
        //alert diyalog acilması icin
        LayoutInflater layoutInflater = this.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.asitakiplayout, null);

        TextView petİsimText =view.findViewById(R.id.petİsimText);
        TextView petAsiTakipBilgiText=view.findViewById(R.id.petAsiTakipBilgiText);
        CircleImageView asiTakipCircleImageView=view.findViewById(R.id.asiTakipCircleImageView);

        petİsimText.setText(petIsmi);
        petAsiTakipBilgiText.setText(petIsmi+" isimli petinizin "+tarih+" tarihinde "+asiIsmi+" aşısı yapılacaktır.");
        Picasso.get().load(resimUrl).into(asiTakipCircleImageView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();


        alertDialog.show();
    }
}