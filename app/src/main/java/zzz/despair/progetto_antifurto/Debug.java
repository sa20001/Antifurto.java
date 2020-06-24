package zzz.despair.progetto_antifurto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Debug extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch tab_sensors, tab_about, tab_log, toggle_win1, toggle_win2, toggle_win3, toggle_win4, toggle_door;
    String cycle_life = "Debug ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);


        tab_sensors = findViewById(R.id.switch_tab_sensors);
        tab_sensors.setOnCheckedChangeListener(this);
        tab_about = findViewById(R.id.switch_tab_about);
        tab_about.setOnCheckedChangeListener(this);
        tab_log = findViewById(R.id.switch_tab_log);
        tab_log.setOnCheckedChangeListener(this);

        toggle_win1 = findViewById(R.id.switch_win1);
        toggle_win1.setOnCheckedChangeListener(this);
        toggle_win2 = findViewById(R.id.switch_win2);
        toggle_win2.setOnCheckedChangeListener(this);
        toggle_win3 = findViewById(R.id.switch_win3);
        toggle_win3.setOnCheckedChangeListener(this);
        toggle_win4 = findViewById(R.id.switch_win4);
        toggle_win4.setOnCheckedChangeListener(this);
        toggle_door=findViewById(R.id.switch_door);
        toggle_door.setOnCheckedChangeListener(this);


        Log.d("tag", cycle_life + "in onCreate");    //.d  d--> debug
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        Intent intent_back_to_home = new Intent(Debug.this, MainActivity.class); // serve per ritornare alla home richiamando MainActivity nello stato start
        intent_back_to_home.putExtra("bool_about",bool_switch_tab_about); /* serve per passare i dati tra le varie attività
        https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application*/
        intent_back_to_home.putExtra("bool_sensors",bool_switch_tab_sensors);
        intent_back_to_home.putExtra("bool_log",bool_switch_tab_log);

        intent_back_to_home.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);    /* serve per ritornare alla home richiamando MainActivity nello stato start
        https://stackoverflow.com/questions/10568408/how-to-resume-an-activity-when-calling-it-from-an-intent*/

        startActivity(intent_back_to_home); // serve per ritornare alla home richiamando MainActivity nello stato start
    }

    boolean bool_switch_tab_sensors = true;
    boolean bool_switch_tab_about = true;
    boolean bool_switch_tab_log = true;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.switch_tab_sensors:
                if (isChecked){
                    bool_switch_tab_sensors = true;
                    Toast.makeText(this, "Abilito la tab", Toast.LENGTH_SHORT).show();
                }
                else{
                    bool_switch_tab_sensors = false;
                    Toast.makeText(this, "Disabilito la tab", Toast.LENGTH_SHORT).show();
                }

                Log.d("Bool tab_sensors", String.valueOf(bool_switch_tab_sensors));
                break;

            case R.id.switch_tab_about:
                if (isChecked){
                    bool_switch_tab_about= true;
                    Toast.makeText(this, "Abilito la tab", Toast.LENGTH_SHORT).show();
                }
                else{
                    bool_switch_tab_about = false;
                    Toast.makeText(this, "Disabilito la tab", Toast.LENGTH_SHORT).show();
                }

                Log.d("Bool tab_about", String.valueOf(bool_switch_tab_about));

                break;

            case R.id.switch_tab_log:
                if (isChecked){
                    bool_switch_tab_log = true;
                    Toast.makeText(this, "Abilito la tab", Toast.LENGTH_SHORT).show();
                }
                else{
                    bool_switch_tab_log = false;
                    Toast.makeText(this, "Disabilito la tab", Toast.LENGTH_SHORT).show();
                }
                Log.d("Bool tab_log", String.valueOf(bool_switch_tab_log));
                break;

            case R.id.switch_win1:
                break;

            case R.id.switch_win2:
                break;

            case R.id.switch_win3:
                Toast.makeText(this, "Funzionano anche i sensori...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.switch_win4:
                break;

            case R.id.switch_door:
                break;

        }

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

}