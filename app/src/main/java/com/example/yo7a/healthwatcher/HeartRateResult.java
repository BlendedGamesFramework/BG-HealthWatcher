package com.example.yo7a.healthwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.yo7a.healthwatcher.retrofit.Atributte;

import com.example.yo7a.healthwatcher.retrofit.INodeJS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HeartRateResult extends AppCompatActivity {

    private String user, Date;
    int HR;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();

    private INodeJS APIREST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_result);

        //IP Configuration and Json adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://164.90.156.141:3005/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIREST = retrofit.create(INodeJS.class);

        Date = df.format(today);
        TextView RHR = (TextView) this.findViewById(R.id.HRR);
        ImageButton SHR = (ImageButton) this.findViewById(R.id.SendHR);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            HR = bundle.getInt("bpm");
            user = bundle.getString("Usr");
            Log.d("DEBUG_TAG", "ccccc" + user);
            RHR.setText(String.valueOf(HR));

            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
            final int id_player = globalVariable.getId_player();


            String counterArray = String.valueOf(HR);

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


        }

        SHR.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Health Watcher");
            i.putExtra(Intent.EXTRA_TEXT, user + "'s Heart Rate " + "\n" + " at " + Date + " is :    " + HR);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(HeartRateResult.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(HeartRateResult.this, Primary.class);
        i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }
}
