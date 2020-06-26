package zzz.despair.progetto_antifurto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Debug extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    Switch tab_sensors, tab_about, tab_log, toggle_win1, toggle_win2, toggle_win3, toggle_win4, toggle_door, toggle_motion, toggle_siren;
    String cycle_life = "Debug ";
    Button toggle_apply;

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
        toggle_motion =findViewById(R.id.switch_motion);
        toggle_motion.setOnCheckedChangeListener(this);
        toggle_siren=findViewById(R.id.switch_siren);
        toggle_siren.setOnCheckedChangeListener(this);
        toggle_apply=findViewById(R.id.button_apply_toggle_sensors);
        toggle_apply.setOnClickListener(this);


        SharedPreferences settings = getSharedPreferences("Toggle_tab_preferences", 0); // richiamo le preferenze
        boolean switchkey_tab_sensors = settings.getBoolean("switchkey_tab_sensors", true); // richiamo le preferenze
        boolean switchkey_tab_about = settings.getBoolean("switchkey_tab_about", true); // richiamo le preferenze
        boolean switchkey_tab_log = settings.getBoolean("switchkey_tab_log", true); // richiamo le preferenze
        tab_sensors.setChecked(switchkey_tab_sensors); // richiamo le preferenze
        tab_about.setChecked(switchkey_tab_about); // richiamo le preferenze
        tab_log.setChecked(switchkey_tab_log); // richiamo le preferenze

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

    boolean bool_win1 = true;
    boolean bool_win2 = true;
    boolean bool_win3 = true;
    boolean bool_win4 = true;
    boolean bool_door = true;
    boolean bool_motion = true;
    boolean bool_siren = true;


    @Override
    public void onCheckedChanged(CompoundButton switchView, boolean isChecked) {

        SharedPreferences settings = getSharedPreferences("Toggle_tab_preferences", 0);  // salvo le preferenze
        SharedPreferences.Editor editor = settings.edit(); // salvo le preferenze

        switch (switchView.getId()) {

            case R.id.switch_tab_sensors:
                if (isChecked){
                    bool_switch_tab_sensors = true;
                    Toast.makeText(this, "Abilito la tab", Toast.LENGTH_SHORT).show();
                }
                else{
                    bool_switch_tab_sensors = false;
                    Toast.makeText(this, "Disabilito la tab", Toast.LENGTH_SHORT).show();
                }
                editor.putBoolean("switchkey_tab_sensors", isChecked); // salvo le preferenze
                editor.apply(); // commit salvo le preferenze

                Log.d("Bool_tab_sensors", String.valueOf(bool_switch_tab_sensors));
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
                editor.putBoolean("switchkey_tab_about", isChecked); // salvo le preferenze
                editor.commit(); // commit salvo le preferenze

                Log.d("Bool_tab_about", String.valueOf(bool_switch_tab_about));


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

                editor.putBoolean("switchkey_tab_log", isChecked); // salvo le preferenze
                editor.commit(); // commit salvo le preferenze

                Log.d("Bool_tab_log", String.valueOf(bool_switch_tab_log));
                break;



            case R.id.switch_win1:
                if (isChecked){
                    bool_win1=false;
                }
                else{
                    bool_win1=true;
                }
                break;

            case R.id.switch_win2:
                if (isChecked){
                    bool_win2=false;
                }
                else{
                    bool_win2=true;
                }
                break;

            case R.id.switch_win3:
                if (isChecked){
                    bool_win3=false;
            }
                else {
                    bool_win3=true;
                }
                break;

            case R.id.switch_win4:
                if (isChecked){
                    bool_win4=false;
                }
                else {
                    bool_win4=true;
                }
                break;

            case R.id.switch_door:
                if (isChecked){
                    bool_door=false;
                }
                else {
                    bool_door=true;
                }
                break;
            case R.id.switch_motion:
                if (isChecked){
                    bool_motion = false;
                }
                else {
                    bool_motion =true;
                }
                break;
            case R.id.switch_siren:
                if (isChecked){
                    bool_siren= false;
                }
                else {
                    bool_siren = true;
                }
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

    @Override
    public void onClick(View buttonView) {

        switch (buttonView.getId()) {

            case R.id.button_apply_toggle_sensors:
                Intent intent_set_sensor = new Intent(Debug.this, Sensors.class); // per passare i valori alla schermata dei sensori

                intent_set_sensor.putExtra("bool_switch_win1", bool_win1);
                intent_set_sensor.putExtra("bool_switch_win2", bool_win2);
                intent_set_sensor.putExtra("bool_switch_win3", bool_win3);
                intent_set_sensor.putExtra("bool_switch_win4", bool_win4);
                intent_set_sensor.putExtra("bool_switch_door",bool_door);
                intent_set_sensor.putExtra("bool_switch_motion", bool_motion);
                intent_set_sensor.putExtra("bool_switch_siren",bool_siren);

                startActivity(intent_set_sensor);
                break;

        }
    }
}