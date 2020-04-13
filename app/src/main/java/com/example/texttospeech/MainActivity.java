package com.example.texttospeech;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button btnSpeak;
    EditText editText;
    TextToSpeech textToSpeech;
    ListView listView;
    SeekBar vitesse;


    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Init TextToSpeech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.FRENCH);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "This language is not supported!",
                                Toast.LENGTH_SHORT);
                    } else {
                        Set<String> a=new HashSet<>();
                        a.add("male");//here you can give male if you want to select male voice.
                        Voice v=new Voice("en-us-x-sfg#male_2-local",Locale.FRENCH,400,200,true,a);
                        btnSpeak.setEnabled(true);
                        textToSpeech.setPitch(0.6f);
                        textToSpeech.setSpeechRate(1.0f);
                        textToSpeech.setVoice(v);
                        textToSpeech.setLanguage(Locale.FRENCH);

                    }
                }
            }
        });

        // Init View
        btnSpeak = (Button) findViewById(R.id.btnSpeak);

        final TextView txtVitesse = findViewById(R.id.txtVitesse);
        vitesse = findViewById(R.id.vitesse);
        vitesse.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                txtVitesse.setText(progress + "");
                Toast.makeText(getApplicationContext(), String.valueOf(progress), Toast.LENGTH_LONG).show();

            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFight();
            }
        });
    }

    private void speak() {
        SWdroid sWdroid = new SWdroid();
        CheckBox attaquerCheckBox = findViewById(R.id.checkBoxAttaquer);
        if (!attaquerCheckBox.isChecked()) {
            sWdroid.getActions().remove("Attaquer");
        }

        CheckBox deffendreCheckBox = findViewById(R.id.defendreBox);
        if (!deffendreCheckBox.isChecked()) {
            sWdroid.getActions().remove("Défendre");
        }

        CheckBox esquiverCheckBox = findViewById(R.id.checkBoxEsquiver);
        if (!esquiverCheckBox.isChecked()) {
            sWdroid.getActions().remove("Esquiver");
        }

        CheckBox jambesCheckBox = findViewById(R.id.jambesBox);
        if (!jambesCheckBox.isChecked()) {
            sWdroid.getZones().remove("Jambe");
        }

        CheckBox brasCheckBox = findViewById(R.id.brasBox);
        if (!brasCheckBox.isChecked()) {
            sWdroid.getZones().remove("Bras");
        }


        CheckBox teteCheckBox = findViewById(R.id.teteCheckBox);
        if (!teteCheckBox.isChecked()) {
            sWdroid.getZones().remove("Tête");
        }

        CheckBox gaucheCheckBox = findViewById(R.id.gaucheBx);
        if (!gaucheCheckBox.isChecked()) {
            sWdroid.getDirections().remove("Gauche");
        }

        CheckBox droiteCheckBox = findViewById(R.id.droiteBox);
        if (!droiteCheckBox.isChecked()) {
            sWdroid.getDirections().remove("Droite");
        }


        String text = sWdroid.randomPhrase();
        Log.i(TAG, text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    private void launchFight() {

        EditText viewById = findViewById(R.id.nbCoups);
        int currentItteration = 0;
        btnSpeak.setText(R.string.btn_progress);
        do {
            speak();
            try {
                Thread.sleep(1000 * vitesse.getProgress());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentItteration++;
        } while (currentItteration < Long.parseLong(viewById.getText().toString()));
        btnSpeak.setText(R.string.btn_start);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
