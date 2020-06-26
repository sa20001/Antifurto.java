package zzz.despair.progetto_antifurto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView help_text;
    FloatingActionButton refresh;
    Button sensors, log, about, debug;
    ImageButton master;

    String cycle_life = "MainActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // "context" must be an Activity, Service or Application object from your app.
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        help_text = findViewById(R.id.text_main);
        refresh = findViewById(R.id.floatingActionButton);
        refresh.setOnClickListener(this);
        sensors = findViewById(R.id.button_sensor);
        sensors.setOnClickListener(this);
        log = findViewById(R.id.button_log);
        log.setOnClickListener(this);
        debug = findViewById(R.id.button_supersu);
        debug.setOnClickListener(this);
        about = findViewById(R.id.button_about);
        about.setOnClickListener(this);
        master = findViewById(R.id.imageButton_master);
        master.setOnClickListener(this);

        main_thread.start(); //togliere il commento
        loop_thread.start();
        alarm_thread.start();

        Log.d("tag", cycle_life + "in onCreate");    //.d  d--> debug


        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE = 1;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        File file = new File(getExternalFilesDir(null), "Log");     //https://developer.android.com/reference/android/content/Context#getExternalFilesDir(java.lang.String)
//        if (!file.exists())
//            Toast.makeText(this,
//                    (file.mkdirs() ? "Directory has been created" : "Directory not created"),
//                    Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this, "Directory exists", Toast.LENGTH_SHORT).show();

        if ( isExternalStorageWritable() ) {

            File appDirectory = new File(getExternalFilesDir(null), "Log");
            File logFile = new File( appDirectory, "logcat_" + System.currentTimeMillis() + ".txt" );

            // clear the previous logcat and then write the new one to the file
            try {
                Process process = Runtime.getRuntime().exec("logcat -c");
                process = Runtime.getRuntime().exec("logcat -f " + logFile);

            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;


    }


    public void pass_to_sensors(){
        Intent intent_sensor = new Intent(MainActivity.this, Sensors.class); //in questo modo creo un oggetto intent che dalla MainActivity richiama Sensors
        // riutilizzo gli stessi int della scheda debug, per questo ho negato lo stato boolean nel file Antidurto_main.py
        intent_sensor.putExtra("bool_switch_win1", loop_thread.status_sensor_win1);
        intent_sensor.putExtra("bool_switch_win2", loop_thread.status_sensor_win2);
        intent_sensor.putExtra("bool_switch_win3", loop_thread.status_sensor_win3);
        intent_sensor.putExtra("bool_switch_win4", loop_thread.status_sensor_win4);
        intent_sensor.putExtra("bool_switch_door", loop_thread.status_sensor_door);
        intent_sensor.putExtra("bool_switch_motion", loop_thread.status_sensor_motion);
        intent_sensor.putExtra("bool_switch_siren", loop_thread.status_siren);
        intent_sensor.putExtra("bool_call_police", alarm_thread.call_police);

        startActivity (intent_sensor); //avvio l'oggetto intent -->avvio Sensors
    }

boolean bool_master = false;
String jambo = "0";
    @Override
    public void onClick(View view_debug) {
        switch (view_debug.getId()) {

            case R.id.floatingActionButton:
                if(loop_thread.java_dict_string != null) {    //genera eccezione, al primo avvio dell'app, queste istruzioni sono solo per testing e per reference, rimuovere nella versione finale

                    if(!loop_thread.java_dict_string.equals(jambo)) {
                        Toast.makeText(this, "Aggiorno i dati", Toast.LENGTH_SHORT).show();
                        jambo= loop_thread.java_dict_string;
                        Log.d("Aggiorno_i_dati", loop_thread.java_dict_string);
                    }
                    else Toast.makeText(this, "Non ci sono dati da aggiornare o i dati sono già aggiornati", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.button_sensor:
                pass_to_sensors();
                break;

            case R.id.button_supersu:
                Intent intent_supersu = new Intent(MainActivity.this, Debug.class);
                startActivity (intent_supersu);
                break;

            case R.id.button_about:
                Intent intent_about = new Intent(MainActivity.this, About.class);
                startActivity (intent_about);
                break;

            case R.id.button_log:
                Intent intent_log = new Intent(MainActivity.this, zzz.despair.progetto_antifurto.Log.class);
                startActivity (intent_log);
                break;

            case R.id.imageButton_master:
                if (! bool_master) {
                    Toast.makeText(this, "Accensione app", Toast.LENGTH_SHORT).show();
                    sensors.setEnabled(true);
                    log.setEnabled(true);
                    about.setEnabled(true);
                    debug.setEnabled(true);
                    help_text.setText(getResources().getString(R.string.text_help_1));
                    master.setBackground(getDrawable(R.drawable.roundcorner_on));
                    bool_master=true;
                }
                break;
        }
    }


    class PrimeThread extends Thread {
        long minPrime;
        PrimeThread(long minPrime) {
            this.minPrime = minPrime;
        }

        public void run() {
            Python py = Python.getInstance();
            PyObject pyf = py.getModule("Antifurto_main");
            PyObject obj = pyf.callAttr("main_program");
        }
    }

    PrimeThread main_thread = new PrimeThread(143);

 //codice che funziona, ma non aggiorna
    class SecondThread extends Thread {

        long minPrime;
        SecondThread(long minPrime) {
            this.minPrime = minPrime;
        }

        volatile String java_dict_string;

        volatile boolean status_sensor_win1;
        volatile boolean status_sensor_win2;
        volatile boolean status_sensor_win3;
        volatile boolean status_sensor_win4;
        volatile boolean status_sensor_door;
        volatile boolean status_siren;
        volatile boolean status_sensor_motion;

        public void run() {

                try {
                    while (true) {

                        Python py = Python.getInstance();
                        final PyObject pyf = py.getModule("Antifurto_main");
                        PyObject obj = pyf.callAttr("pass_dict");

                        PyObject obj_sensor_win1 = pyf.callAttr("pass_sensor_win0");
                        PyObject obj_sensor_win2 = pyf.callAttr("pass_sensor_win1");
                        PyObject obj_sensor_win3 = pyf.callAttr("pass_sensor_win2");
                        PyObject obj_sensor_win4 = pyf.callAttr("pass_sensor_win3");
                        PyObject obj_sensor_door = pyf.callAttr("pass_sensor_door");
                        PyObject obj_sensor_motion = pyf.callAttr("pass_sensor_motion");
                        PyObject obj_siren = pyf.callAttr("pass_siren");

                        java_dict_string = obj.toString();
                        status_sensor_win1 = obj_sensor_win1.toBoolean();
                        status_sensor_win2 = obj_sensor_win2.toBoolean();
                        status_sensor_win3 = obj_sensor_win3.toBoolean();
                        status_sensor_win4 = obj_sensor_win4.toBoolean();
                        status_sensor_door = obj_sensor_door.toBoolean();
                        status_sensor_motion = obj_sensor_motion.toBoolean();
                        status_siren = obj_siren.toBoolean();

                        Log.d("Java_dict_string", java_dict_string);
//                        nego i bool per riutilizzare le key dell'intent per passare le key da debug.class a sensors.class, negati in Antifurto_main.py e qui li rinego per averli giusti
                        Log.d("Java_dict_string", "Sensor Window 1 status triggered " + String.valueOf(! status_sensor_win1));
                        Log.d("Java_dict_string", "Sensor Window 2 status triggered " + String.valueOf(! status_sensor_win2));
                        Log.d("Java_dict_string", "Sensor Window 3 status triggered " + String.valueOf(! status_sensor_win3));
                        Log.d("Java_dict_string", "Sensor Window 4 status triggered " + String.valueOf(! status_sensor_win4));
                        Log.d("Java_dict_string", "Sensor Door status triggered " + String.valueOf(! status_sensor_door));
                        Log.d("Java_dict_string", "Sensor Motion status triggered " + String.valueOf(! status_sensor_motion));
                        Log.d("Java_dict_string", "Siren status active " + String.valueOf(! status_siren));
//                        nego i bool per riutilizzare le key dell'intent per passare le key da debug.class a sensors.class,  negati in Antifurto_main.py e qui li rinego per averli giusti
                        Thread.sleep(5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    SecondThread loop_thread = new SecondThread(144);

    class ThirdThread extends Thread {
        long minPrime;
        ThirdThread(long minPrime) {
            this.minPrime = minPrime;
        }
        boolean kill;
        volatile boolean call_police= false;

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (! loop_thread.status_siren) {
                    call_police=true;
                    pass_to_sensors();
                    kill=true;
                }
                if (kill) break;
            }
        }
    }

    ThirdThread alarm_thread = new ThirdThread(145);


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
//        https://stackoverflow.com/questions/7174832/how-to-use-putextra-with-flag-activity-reorder-to-front-in-android-apps
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("tag", cycle_life + "in onStart");    //.d  d--> debug
    }

    @Override
    protected void onResume() {
        super.onResume();

//        codice messo in resume perchè in start non lo prende, bisogna richiamare start più volte per farglielo prendere
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean main_bool_sensors = extras.getBoolean("bool_sensors");
            if (main_bool_sensors) sensors.setEnabled(true);
            else sensors.setEnabled(false);

            boolean main_bool_log = extras.getBoolean("bool_log");
            if (main_bool_log) log.setEnabled(true);
            else log.setEnabled(false);

            boolean main_bool_about = extras.getBoolean("bool_about");
            if (main_bool_about) about.setEnabled(true);
            else about.setEnabled(false);
        }

//        codice messo in resume perchè in start non lo prende, bisogna richiamare start più volte per farglielo prendere

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

        Python py = Python.getInstance();
        PyObject pyf = py.getModule("Antifurto_main");
        PyObject obj = pyf.callAttr("on_Destroy");

        Log.d("tag", cycle_life + "in onDestroy");    //.d  d--> debug
    }
}