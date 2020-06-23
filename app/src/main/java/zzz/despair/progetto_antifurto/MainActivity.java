package zzz.despair.progetto_antifurto;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView help_text;
    FloatingActionButton refresh;
    Button sensors, log, about, debug;
    ImageButton master;

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

        p.start();
        loop.start();

        Log.d("tag", "MainActivity in onCreate");    //.d  d--> debug
    }


String jambo = "0";
    @Override
    public void onClick(View view_l) {
        switch (view_l.getId()) {

            case R.id.floatingActionButton:
                if(loop.java_dict != null) {    //genera eccezione, al primo avvio dell'app, queste istruzioni sono solo per testing e per reference, rimuovere nella versione finale

                    if(!loop.java_dict.equals(jambo)) {
                        Toast.makeText(this, "Aggiorno i dati", Toast.LENGTH_SHORT).show();
                        jambo= loop.java_dict;
                        Log.d("Testing", loop.java_dict);
                    }
                    else Toast.makeText(this, "Non ci sono dati da aggiornare o i dati sono già aggiornati", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.button_sensor:
                Intent intent_sensor = new Intent(MainActivity.this, Sensors.class); //in questo modo creo un oggetto intent che dall'Activity_One richiama l'Activity_Two
                startActivity (intent_sensor); //avvio l'oggetto intent -->avvio l'Activity_Two
                break;

            case R.id.button_supersu:
                Intent intent_supersu = new Intent(MainActivity.this, Debug.class); //in questo modo creo un oggetto intent che dall'Activity_One richiama l'Activity_Two
                startActivity (intent_supersu); //avvio l'oggetto intent -->avvio l'Activity_Two
                break;

            case R.id.button_about:
                Intent intent_about = new Intent(MainActivity.this, About.class); //in questo modo creo un oggetto intent che dall'Activity_One richiama l'Activity_Two
                startActivity (intent_about); //avvio l'oggetto intent -->avvio l'Activity_Two
                break;

            case R.id.button_log:
                Intent intent_log = new Intent(MainActivity.this, zzz.despair.progetto_antifurto.Log.class); //in questo modo creo un oggetto intent che dall'Activity_One richiama l'Activity_Two
                startActivity (intent_log); //avvio l'oggetto intent -->avvio l'Activity_Two
                break;

            case R.id.imageButton_master:
                Toast.makeText(this, "Accensione app", Toast.LENGTH_SHORT).show();
                sensors.setEnabled(true);
                log.setEnabled(true);
                about.setEnabled(true);
                debug.setEnabled(true);
                help_text.setText(getResources().getString(R.string.text_help_1));
                master.setBackgroundColor(getResources().getColor(R.color.color_status_on));
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

    PrimeThread p = new PrimeThread(143);

 //codice che funziona, ma non aggiorna
    class SecondThread extends Thread {
        long minPrime;
        SecondThread(long minPrime) {
            this.minPrime = minPrime;
        }

        volatile String java_dict;

        public void run() {

                try {
                    while (true) {

                        Python py = Python.getInstance();
                        final PyObject pyf = py.getModule("Antifurto_main");
                        PyObject obj = pyf.callAttr("pass_dict");

//                testo = findViewById(R.id.bob);
//                testo.setText(obj.toString());

                        java_dict = obj.toString();
                        Log.d("Java_dict", java_dict);

                        Thread.sleep(4000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    SecondThread loop = new SecondThread(144);
    //codice che funziona, ma non aggiorna


    @Override
    protected void onStart() {
        super.onStart();



        Log.d("tag", "MainActivity in onStart");    //.d  d--> debug

        if(loop.java_dict != null) {    //genera eccezione, al primo avvio dell'app, queste istruzioni sono solo per testing e per reference, rimuovere nella versione finale
            Log.d("Testing", loop.java_dict);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("tag", "MainActivity in onResume");    //.d  d--> debug
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("tag", "MainActivity in onPause");    //.d  d--> debug
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("tag", "MainActivity in onStop");    //.d  d--> debug
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Python py = Python.getInstance();
        PyObject pyf = py.getModule("Antifurto_main");
        PyObject obj = pyf.callAttr("on_Destroy");

        Log.d("tag", "MainActivity in onDestroy");    //.d  d--> debug
    }
}