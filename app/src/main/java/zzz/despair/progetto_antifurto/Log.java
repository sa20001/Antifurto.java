package zzz.despair.progetto_antifurto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Log extends AppCompatActivity implements View.OnClickListener {
    String cycle_life = "Log ";
    TextView textView_log;
    Button refresh_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        refresh_log = findViewById(R.id.refresh_log);
        refresh_log.setOnClickListener(this);

        //https://stackoverflow.com/questions/25351286/android-display-logcat-in-textview
        try {
            Process process = Runtime.getRuntime().exec("logcat -d  *:S tag, Java_dict_string, Aggiorno_i_dati, python.stdout"); //finalmente ho capito come mettere i tag sui log *:S nometag
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
                log.append("\n\n");
            }
            textView_log = findViewById(R.id.textwiew_log);
            textView_log.setText(log.toString());
        } catch (IOException e) {
            // Handle Exception
        }


        android.util.Log.d("tag", cycle_life + "in onCreate");    //.d  d--> debug
    }

    @Override
    protected void onStart() {
        super.onStart();

        android.util.Log.d("tag", cycle_life + "in onStart");    //.d  d--> debug
    }

    @Override
    protected void onResume() {
        super.onResume();

        android.util.Log.d("tag", cycle_life + "in onResume");    //.d  d--> debug
    }

    @Override
    protected void onPause() {
        super.onPause();

        android.util.Log.d("tag", cycle_life + "in onPause");    //.d  d--> debug
    }

    @Override
    protected void onStop() {
        super.onStop();

        android.util.Log.d("tag", cycle_life + "in onStop");    //.d  d--> debug
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        android.util.Log.d("tag", cycle_life + "in onDestroy");    //.d  d--> debug
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.refresh_log:
                try {
                    Process process = Runtime.getRuntime().exec("logcat -d  *:S tag, Java_dict_string, Aggiorno_i_dati, python.stdout"); //finalmente ho capito come mettere i tag sui log *:S nometag
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    StringBuilder log = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        log.append(line);
                        log.append("\n\n");
                    }
                    textView_log = findViewById(R.id.textwiew_log);
                    textView_log.setText(log.toString());
                } catch (IOException e) {
                    // Handle Exception
                }

                break;

        }
    }
}