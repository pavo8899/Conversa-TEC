package com.example.alejandroanzures.conversa_tec;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
//Widgets
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//Java Utils
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.util.Log;
//Librerias para reconocimiento de voz
import android.speech.RecognizerIntent;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;

public class Clase_Directa extends AppCompatActivity {

    //Variables de TextToVoice
    TextToSpeech TtoVoice;
    private static final String TAG = "MyStt3Activity";

    //Variables VoiceToText
    private SpeechRecognizer sr;

    //Elementos del layout
    TextView txtvCurrentSpeech;
    ListView lstvHistorySpeech;
    FloatingActionButton fabAdd;
    FloatingActionButton fabStartStop;
    FloatingActionButton fabQuestion;
    FloatingActionButton fabTmpQuestion;
    LinearLayout LayoutStartStop;
    LinearLayout LayoutQuestion;
    LinearLayout LayoutTmpQuestion;

    //Variables Base de Datos
    clasesDB DB;

    //Animation
    Animation fabOpen;
    Animation fabClose;
    Animation fabRotate;
    Animation fabAntiRotate;

    //Current
    String Actual="";
    ArrayAdapter<String> adapter=null;

    String Pregunta="";

    //Other Variables
    boolean isOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase__directa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Elementos del Layout
        txtvCurrentSpeech=(TextView) findViewById(R.id.txtvCurrentSpeech);
        txtvCurrentSpeech.setText(Actual);
        lstvHistorySpeech=(ListView) findViewById(R.id.lstvHistorySpeech);
        lstvHistorySpeech.setAdapter(adapter);

        //Floating Action Buttons
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabStartStop = (FloatingActionButton) findViewById(R.id.fabStartStop);
        fabQuestion = (FloatingActionButton) findViewById(R.id.fabQuestion);
        fabTmpQuestion = (FloatingActionButton) findViewById(R.id.fabTmpQuestion);
        LayoutStartStop =(LinearLayout)findViewById(R.id.startStopLayout);
        LayoutQuestion =(LinearLayout)findViewById(R.id.questionLayout);
        LayoutTmpQuestion =(LinearLayout)findViewById(R.id.tmpQuestion);

        //Animations
        fabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabClose=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fabRotate=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clock);
        fabAntiRotate=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anti_clock);

        //FAB Actions
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAddClick();
            }
        });
        fabStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabStartStopClick(view);
            }
        });
        fabQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabQuestionClick();
            }
        });
        fabTmpQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabTmpQuestionClick();
            }
        });

        //Base de Datos
        DB=new clasesDB(this);

        //Texto a Voz
        TtoVoice=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    TtoVoice.setLanguage(Locale.getDefault());
                }
            }
        });

        //Voz a Texto
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

    }

    //Metodos de FAB
    public void fabAddClick()
    {
        if(isOpen)
        {
            //fabQuestion.startAnimation(fabClose);
            LayoutQuestion.startAnimation(fabClose);
            fabQuestion.setClickable(false);
            //fabStartStop.startAnimation(fabClose);
            LayoutStartStop.startAnimation(fabClose);
            fabStartStop.setClickable(false);
            fabAdd.startAnimation(fabAntiRotate);
            isOpen=false;
        }
        else
        {
            //fabQuestion.startAnimation(fabOpen);
            LayoutQuestion.startAnimation(fabOpen);
            fabQuestion.setClickable(true);
            //fabStartStop.startAnimation(fabOpen);
            LayoutStartStop.startAnimation(fabOpen);
            fabStartStop.setClickable(true);
            fabAdd.startAnimation(fabRotate);
            isOpen=true;
        }
    }

    public void fabStartStopClick(View view)
    {
        //fabQuestion.startAnimation(fabClose);
        LayoutQuestion.startAnimation(fabClose);
        fabQuestion.setClickable(false);
        //fabStartStop.startAnimation(fabClose);
        LayoutStartStop.startAnimation(fabClose);
        fabStartStop.setClickable(false);
        fabAdd.startAnimation(fabAntiRotate);
        isOpen=false;

        Snackbar.make(view, "Iniciando Reconocimiento", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        //Reconocimiento();

        //TMP
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        sr.startListening(intent);
        Log.i("111111","11111111");
    }

    public void fabQuestionClick()
    {
        //fabQuestion.startAnimation(fabClose);
        LayoutQuestion.startAnimation(fabClose);
        fabQuestion.setClickable(false);
        //fabStartStop.startAnimation(fabClose);
        LayoutStartStop.startAnimation(fabClose);
        fabStartStop.setClickable(false);
        fabAdd.startAnimation(fabAntiRotate);
        isOpen=false;

        InputDialogMainActivityFragment message=new InputDialogMainActivityFragment();
        message.setTitleMessage("","",R.drawable.ic_realizar_pregunta,this);
        message.show(getSupportFragmentManager(),"Información");


        /*try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void displayQuestionButton()
    {
        LayoutTmpQuestion.startAnimation(fabOpen);
        fabTmpQuestion.setClickable(true);
    }

    public void fabTmpQuestionClick()
    {
        Toast.makeText(getApplicationContext(), "Realizando Pregunta",Toast.LENGTH_SHORT).show();
        TtoVoice.speak(Pregunta, TextToSpeech.QUEUE_FLUSH, null);
        LayoutTmpQuestion.startAnimation(fabClose);
        fabTmpQuestion.setClickable(false);
    }

    //Metodos de la Clase
    public void Reconocimiento()
    {
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Empieza a Hablar");

        try {
            startActivityForResult(i, 100);
        }
        catch (ActivityNotFoundException antExc)
        {
            Toast.makeText(Clase_Directa.this, "Su dispositivo no soporta el dictado por voz",Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent i)
    {
        super.onActivityResult(request_code,result_code,i);
        switch (request_code)
        {
            case 100:
                if(result_code==RESULT_OK && i!=null)
                {
                    ArrayList<String> result =i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtvCurrentSpeech.setText(result.get(0));
                    DB.agregarSpeech(result.get(0));
                    DB.leerSpeech();
                    List<String> speech=DB.leerSpeech();
                    adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,speech);
                    lstvHistorySpeech.setAdapter(adapter);
                }
            break;
        }
    }

    //Clase Listener para el speech recognition
    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            txtvCurrentSpeech.setText("error " + error);
        }
        public void onResults(Bundle results)
        {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            //txtvCurrentSpeech.setText("results: "+String.valueOf(data.size()));
            txtvCurrentSpeech.setText(txtvCurrentSpeech.getText()+data.get(0).toString());
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
