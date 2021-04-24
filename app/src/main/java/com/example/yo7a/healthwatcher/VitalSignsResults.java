package com.example.yo7a.healthwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.yo7a.healthwatcher.retrofit.Atributte;

import com.example.yo7a.healthwatcher.retrofit.INodeJS;

public class VitalSignsResults extends AppCompatActivity {

    private String user, Date;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    int VBP1, VBP2, VRR, VHR, VO2;
    private INodeJS APIREST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs_results);

        Date = df.format(today);
        TextView VSRR = this.findViewById(R.id.RRV);
        TextView VSBPS = this.findViewById(R.id.BP2V);
        TextView VSHR = this.findViewById(R.id.HRV);
        TextView VSO2 = this.findViewById(R.id.O2V);
        ImageButton All = this.findViewById(R.id.SendAll);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://144.126.216.255:3005/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIREST = retrofit.create(INodeJS.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            VRR = bundle.getInt("breath");
            VHR = bundle.getInt("bpm");
            VBP1 = bundle.getInt("SP");
            VBP2 = bundle.getInt("DP");
            VO2 = bundle.getInt("O2R");
            user = bundle.getString("Usr");
            VSRR.setText(String.valueOf(VRR));
            VSHR.setText(String.valueOf(VHR));
            VSBPS.setText(VBP1 + " / " + VBP2);
            VSO2.setText(String.valueOf(VO2));

            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
            final int id_player = globalVariable.getId_player();


            String counterArray = String.valueOf(VHR);

            String watch_params_final = "heart_rate";

            APIREST.savePost(id_player,24,counterArray,watch_params_final)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Atributte>() {

                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("onSubscribe","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        @Override
                        public void onNext(Atributte atributte) {
                            Log.d("onNext","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("FAIL",e.toString());
                        }

                        @Override
                        public void onComplete() {
                            Log.d("onComplete","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }
                    });
            counterArray = String.valueOf(VBP1);
            counterArray += ",";
            counterArray += String.valueOf(VBP2);

            watch_params_final = "blood_pressure";

            APIREST.savePost(id_player,25,counterArray,watch_params_final)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Atributte>() {

                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("onSubscribe","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        @Override
                        public void onNext(Atributte atributte) {
                            Log.d("onNext","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("FAIL",e.toString());
                        }

                        @Override
                        public void onComplete() {
                            Log.d("onComplete","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }
                    });

            counterArray = String.valueOf(VRR);

            watch_params_final = "respiration_rate";

            APIREST.savePost(id_player,26,counterArray,watch_params_final)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Atributte>() {

                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("onSubscribe","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        @Override
                        public void onNext(Atributte atributte) {
                            Log.d("onNext","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("FAIL",e.toString());
                        }

                        @Override
                        public void onComplete() {
                            Log.d("onComplete","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }
                    });

            counterArray = String.valueOf(VO2);

            watch_params_final = "oxygen_saturation_rate";

            APIREST.savePost(id_player,27,counterArray,watch_params_final)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Atributte>() {

                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("onSubscribe","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        @Override
                        public void onNext(Atributte atributte) {
                            Log.d("onNext","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("FAIL",e.toString());
                        }

                        @Override
                        public void onComplete() {
                            Log.d("onComplete","onNexteeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }
                    });



        }

        All.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Health Watcher");
            i.putExtra(Intent.EXTRA_TEXT, user + "'s new measuerment " + "\n" + " at " + Date + " are :" + "\n" + "Heart Rate = " + VHR + "\n" + "Blood Pressure = " + VBP1 + " / " + VBP2 + "\n" + "Respiration Rate = " + VRR + "\n" + "Oxygen Saturation = " + VO2);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(VitalSignsResults.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(VitalSignsResults.this, Primary.class);
        i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }
}
