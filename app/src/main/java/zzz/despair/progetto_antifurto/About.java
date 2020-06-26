package zzz.despair.progetto_antifurto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity implements View.OnClickListener {
    String cycle_life = "About ";
    Button thx_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        thx_button = findViewById(R.id.button_thx);
        thx_button.setOnClickListener(this);

        Log.d("tag", cycle_life + "in onCreate");    //.d  d--> debug
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

        Log.d("tag", cycle_life + "in onDestroy");    //.d  d--> debug
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_thx:
                String url = "https://stackoverflow.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


                break;

        }
    }
}