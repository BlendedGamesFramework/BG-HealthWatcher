package com.example.yo7a.healthwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


public class O2Result extends AppCompatActivity {

    private String user, Date;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    int O2;
    private INodeJS APIREST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o2_result);

        Date = df.format(today);
        TextView RO2 = this.findViewById(R.id.O2R);
        ImageButton SO2 = this.findViewById(R.id.SendO2);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://164.90.156.141:3005/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIREST = retrofit.create(INodeJS.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            O2 = bundle.getInt("O2R");
            user = bundle.getString("Usr");
            RO2.setText(String.valueOf(O2));
            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
            final int id_player = globalVariable.getId_player();


            String counterArray = String.valueOf(O2);

            String watch_params_final = "oxygen_saturation_rate";

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

        SO2.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Health Watcher");
            i.putExtra(Intent.EXTRA_TEXT, user + "'s Oxygen Saturation Level " + "\n" + " at " + Date + " is :   " + O2);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(O2Result.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(O2Result.this, Primary.class);
        i.putExtra("Usr", user);
        startActivity(i);
        finish();
        super.onBackPressed();

    }
}
