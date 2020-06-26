package zzz.despair.progetto_antifurto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Sensors extends AppCompatActivity implements View.OnClickListener {
    String cycle_life = "Sensors ";
    ImageView siren, win1, win2, win3, win4, door, motion;
    Button button_call_police;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        siren = findViewById(R.id.imageView_siren);
        win1 = findViewById(R.id.imageView_win1);
        win2 = findViewById(R.id.imageView_win2);
        win3 = findViewById(R.id.imageView_win3);
        win4 = findViewById(R.id.imageView_win4);
        door = findViewById(R.id.imageView_door);
        motion = findViewById(R.id.imageView_motion);
        button_call_police=findViewById(R.id.button_call_police);
        button_call_police.setOnClickListener(this);
        button_call_police.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            boolean main_bool_siren = extras.getBoolean("bool_switch_siren");
            if (main_bool_siren){
                siren.setColorFilter(ContextCompat.getColor(this, R.color.sensor_ok), PorterDuff.Mode.SRC_ATOP);
            }
            else{
                siren.setColorFilter(ContextCompat.getColor(this, R.color.sensor_not_ok), PorterDuff.Mode.SRC_ATOP);
            }

            boolean main_bool_win1 = extras.getBoolean("bool_switch_win1");
            if (main_bool_win1){
                win1.setColorFilter(ContextCompat.getColor(this, R.color.sensor_ok), PorterDuff.Mode.SRC_ATOP);
                win1.setImageResource(R.drawable.ic_sensor_ok);
            }
            else{
                win1.setColorFilter(ContextCompat.getColor(this, R.color.sensor_not_ok), PorterDuff.Mode.SRC_ATOP);
                win1.setImageResource(R.drawable.ic_sensor_not_ok);
            }
            Log.d("check_bool", String.valueOf(main_bool_win1));

            boolean main_bool_win2 = extras.getBoolean("bool_switch_win2");
            if (main_bool_win2){
                win2.setColorFilter(ContextCompat.getColor(this, R.color.sensor_ok), PorterDuff.Mode.SRC_ATOP);
                win2.setImageResource(R.drawable.ic_sensor_ok);
            }
            else{
                win2.setColorFilter(ContextCompat.getColor(this, R.color.sensor_not_ok), PorterDuff.Mode.SRC_ATOP);
                win2.setImageResource(R.drawable.ic_sensor_not_ok);
            }

            boolean main_bool_win3 = extras.getBoolean("bool_switch_win3");
            if (main_bool_win3){
                win3.setColorFilter(ContextCompat.getColor(this, R.color.sensor_ok), PorterDuff.Mode.SRC_ATOP);
                win3.setImageResource(R.drawable.ic_sensor_ok);
            }
            else{
                win3.setColorFilter(ContextCompat.getColor(this, R.color.sensor_not_ok), PorterDuff.Mode.SRC_ATOP);
                win3.setImageResource(R.drawable.ic_sensor_not_ok);
            }

            boolean main_bool_win4 = extras.getBoolean("bool_switch_win4");
            if (main_bool_win4){
                win4.setColorFilter(ContextCompat.getColor(this, R.color.sensor_ok), PorterDuff.Mode.SRC_ATOP);
                win4.setImageResource(R.drawable.ic_sensor_ok);
            }
            else{
                win4.setColorFilter(ContextCompat.getColor(this, R.color.sensor_not_ok), PorterDuff.Mode.SRC_ATOP);
                win4.setImageResource(R.drawable.ic_sensor_not_ok);
            }

            boolean main_bool_door = extras.getBoolean("bool_switch_door");
            if (main_bool_door){
                door.setColorFilter(ContextCompat.getColor(this, R.color.sensor_ok), PorterDuff.Mode.SRC_ATOP);
                door.setImageResource(R.drawable.ic_sensor_ok);
            }
            else{
                door.setColorFilter(ContextCompat.getColor(this, R.color.sensor_not_ok), PorterDuff.Mode.SRC_ATOP);
                door.setImageResource(R.drawable.ic_sensor_not_ok);
            }

            boolean main_bool_movement = extras.getBoolean("bool_switch_motion");
            if (main_bool_movement){
                motion.setColorFilter(ContextCompat.getColor(this, R.color.sensor_ok), PorterDuff.Mode.SRC_ATOP);
                motion.setImageResource(R.drawable.ic_sensor_ok);
            }
            else{
                motion.setColorFilter(ContextCompat.getColor(this, R.color.sensor_not_ok), PorterDuff.Mode.SRC_ATOP);
                motion.setImageResource(R.drawable.ic_sensor_not_ok);
            }

            boolean active_button_police = extras.getBoolean("bool_call_police");
            if(active_button_police) button_call_police.setVisibility(View.VISIBLE);
        }




        Log.d("tag", cycle_life + "in onCreate");    //.d  d--> debug
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("tag", cycle_life + "in onStart");    //.d  d--> debug
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("tag", cycle_life + "in onResume");    //.d  d--> debug
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("tag", cycle_life + "in onPause");    //.d  d--> debug
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("tag", cycle_life + "in onStop");    //.d  d--> debug
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("tag", cycle_life + "in onDestroy");    //.d  d--> debug
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_call_police:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + "112"));
                startActivity(intent);
                Toast.makeText(this, "Ok, la chiamo", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}