package zzz.despair.progetto_antifurto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Log extends AppCompatActivity {
    String cycle_life = "Log ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
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
}