package com.example.alejandroanzures.conversa_tec;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class Clase_Directa extends AppCompatActivity {

    //Variables de TextToVoice
    TextToSpeech TtoVoice;

    //Varaibles VoiceToText
    private static final String TAG = "MyStt3Activity";
    private SpeechRecognizer sr;
    private boolean ClaseIniciada=false;

    //Elementos del layout
    TextView txtvCurrentSpeech;
    EditText txtvCurrentSpeech0;
    TextView txtLogError;
    //FloatingActionButton fabAdd;
    //FloatingActionButton fabStartStop;
    Button btnIniciar,btnPreguntar;

    //Animation
    /*Animation fabOpen;
    Animation fabClose;
    Animation fabRotate;
    Animation fabAntiRotate;*/

    //Current
    String Actual="";
    ArrayAdapter<String> adapter=null;
    String Pregunta="";

    //Other Variables
    boolean isOpen=false;
    int loglines=0;

    //Colores
    String ColorPregunta;
    String HEXColorFondo="";
    String HEXColorTexto="";
    String HEXColorPregunta="";
    String TamañoTexto="";
    SharedPreferences prefs;
    AudioManager audioManager;

    //Bases de Datos
    clasesDB db;
    clasesDB DB;

    //Bluetooth headset
    BluetoothHelper mBluetoothHelper;
    BluetoothAdapter btAdapter;
    Set pairedDevices;
    BluetoothHeadset btHeadset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_directa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.drawable.ic_clase_directa);

        //Elementos del Layout
        txtvCurrentSpeech0=(EditText) findViewById(R.id.txtvCurrentSpeech0);
        txtvCurrentSpeech=(TextView) findViewById(R.id.txtvCurrentSpeech);
        txtvCurrentSpeech.setText(Actual);
        txtvCurrentSpeech.setMovementMethod(new ScrollingMovementMethod());
        txtLogError=(TextView) findViewById(R.id.txtLogError);


        //Floating Action Buttons
        btnPreguntar=(Button)findViewById(R.id.btnPreguntar);
        //fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        btnIniciar=(Button)findViewById(R.id.btnIniciar);
        //fabStartStop = (FloatingActionButton) findViewById(R.id.fabStartStop);

        //Animations
        /*fabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabClose=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fabRotate=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clock);
        fabAntiRotate=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anti_clock);*/

        //FAB Actions
        btnPreguntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAddClick();
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabStartStopClick(view);
            }
        });



        //Base de Datos
        DB=new clasesDB(this);

        //Voz a Texto
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        //Texto a Voz
        TtoVoice=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    TtoVoice.setLanguage(Locale.getDefault());
                }
                Log.d(TAG,  "status " +  status);
            }
        });


        db=new clasesDB(this);
        setTitle("CT: "+db.getNombreClase());

        Actual=db.getSpeechClase();
        txtvCurrentSpeech.setText(Html.fromHtml(Actual));

        //Settings personalizados
        SetSettings();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Setting and enabling bluetooth headset
        mBluetoothHelper = new BluetoothHelper(this);
        SetupBluetooth();
    }

    public void SetSettings()
    {
        prefs =getSharedPreferences("ConversaTEC", Context.MODE_PRIVATE);
        HEXColorFondo=prefs.getString("colorFondo", "#e1c694");
        HEXColorTexto=prefs.getString("colorTexto", "#020202");
        HEXColorPregunta=prefs.getString("colorPregunta", "#c62828");
        TamañoTexto=prefs.getString("tamañoTexto", "20");

        int color= Color.parseColor(HEXColorFondo);
        txtvCurrentSpeech.setBackgroundColor(color);
        //txtvCurrentSpeech0.setBackgroundColor(color);
        color= Color.parseColor(HEXColorTexto);
        txtvCurrentSpeech.setTextColor(color);

        txtvCurrentSpeech.setTextSize(Float.parseFloat(TamañoTexto));

        ColorPregunta=HEXColorPregunta;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,100,0);

        }catch (Exception ex){}
    }

    //Metodos de FAB
    boolean solicitar=true;
    public void fabAddClick()
    {
        if(solicitar) {
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(this.getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Solicitando Pregunta", Toast.LENGTH_SHORT).show();
            Pregunta = txtvCurrentSpeech0.getText().toString();
            solicitar=false;
        }
        else
        {
            fabTmpQuestionClick();
            solicitar=true;
        }

    }
    public void fabStartStopClick(View view)
    {

        //fabStartStop.setClickable(false);

        if(!ClaseIniciada)
        {
            btnIniciar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop,0,0,0);
            btnIniciar.setText("Pausar");
            ClaseIniciada = true;
            Snackbar.make(view, "Iniciando Reconocimiento", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Reconocimiento();
        }
        else
        {
            btnIniciar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_start,0,0,0);
            btnIniciar.setText("Continuar");
            ClaseIniciada = false;
            Snackbar.make(view, "Deteniendo Reconocimiento", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        isOpen=false;
        //

    }
    public void fabTmpQuestionClick()
    {

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,0);

        }catch (Exception ex){}

        Toast.makeText(getApplicationContext(), "Realizando Pregunta",Toast.LENGTH_SHORT).show();

        HashMap<String, String> myHashRender = new HashMap<String, String>();

        if (mBluetoothHelper.isOnHeadsetSco())
        {
            myHashRender.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_VOICE_CALL));
        }
        TtoVoice.speak(Pregunta, TextToSpeech.QUEUE_FLUSH, myHashRender);
        //TtoVoice.speak(Pregunta, TextToSpeech.QUEUE_FLUSH, null);

        Actual=Actual+String.format("<P><font color=\"%s\">"+Pregunta+"</font></P>", ColorPregunta);
        txtvCurrentSpeech.setText(Html.fromHtml(Actual));
        db.insertSpeech(Actual);

        while (TtoVoice.isSpeaking())
        {

        }
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);

        }catch (Exception ex){}
        Reconocimiento();
    }

    //Metodos de la Clase
    public void Reconocimiento()
    {
        if(btAdapter.isEnabled())
        {
            for (Object tryDevice : pairedDevices)
            {
                //This loop tries to start VoiceRecognition mode on every paired device until it finds one that works(which will be the currently in use bluetooth headset)
                if (btHeadset.startVoiceRecognition((BluetoothDevice) tryDevice))
                {
                    break;
                }
            }
        }
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Empieza a Hablar");

        if(ClaseIniciada)
        try {
            sr.startListening(i);

            Log.i("111111","11111111");
        }
        catch (ActivityNotFoundException antExc)
        {
            Toast.makeText(Clase_Directa.this, "Su dispositivo no soporta el dictado por voz",Toast.LENGTH_LONG).show();
        }
    }

    //Clase para reconocimiento
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
            //txtvCurrentSpeech.setText("error " + error);
            //Toast.makeText(Clase_Directa.this, "Error " +error,Toast.LENGTH_LONG).show();
            String Error="";
            switch (error)
            {
                case 3: Error="ERROR_AUDIO"; break;
                case 5: Error="ERROR_CLIENT"; break;
                case 9: Error="ERROR_INSUFFICIENT_PERMISSIONS"; break;
                case 2: Error="ERROR_NETWORK"; break;
                case 1: Error="ERROR_NETWORK_TIMEOUT"; break;
                case 7: Error="ERROR_NO_MATCH"; break;
                case 8: Error="ERROR_RECOGNIZER_BUSY"; break;
                case 4: Error="ERROR_SERVER"; break;
                case 6: Error="ERROR_SPEECH_TIMEOUT"; break;
                default: Error="UNKNOW_ERROR"; break;
            }
            txtLogError.setText(Error+" "+(loglines++));
            Reconocimiento();
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

            Actual=Actual+"<P>"+data.get(0).toString()+"</P>";
            txtvCurrentSpeech.setText(Html.fromHtml(Actual));
            db.insertSpeech(Actual);
            Reconocimiento();
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

    //Metodos de salida
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("¿Esta seguro que desea Salir?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClaseIniciada = false;
                        Clase_Directa.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onDestroy()
    {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try{
            ClaseIniciada = false;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,0);

        }catch (Exception ex){}
        super.onDestroy();
    }

    //Manejo del bluetooth headset

    private class BluetoothHelper extends BluetoothHeadsetUtils
    {
        public BluetoothHelper(Context context)
        {
            super(context);
        }

        @Override
        public void onScoAudioDisconnected()
        {
            // Cancel speech recognizer if desired
        }

        @Override
        public void onScoAudioConnected()
        {
            // Should start speech recognition here if not already started
        }

        @Override
        public void onHeadsetDisconnected()
        {

        }

        @Override
        public void onHeadsetConnected()
        {

        }
    }

    private void SetupBluetooth()
    {
        try {
            btAdapter = BluetoothAdapter.getDefaultAdapter();

            pairedDevices = btAdapter.getBondedDevices();

            BluetoothProfile.ServiceListener mProfileListener = new BluetoothProfile.ServiceListener() {
                public void onServiceConnected(int profile, BluetoothProfile proxy) {
                    if (profile == BluetoothProfile.HEADSET) {
                        btHeadset = (BluetoothHeadset) proxy;
                    }
                }

                public void onServiceDisconnected(int profile) {
                    if (profile == BluetoothProfile.HEADSET) {
                        btHeadset = null;
                    }
                }
            };
            btAdapter.getProfileProxy(this, mProfileListener, BluetoothProfile.HEADSET);
        }catch (Exception ex){
            txtLogError.setText(ex.getMessage()+" "+(loglines++));
        }
    }

    //Manejo de Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clase_directa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bluetooth) {
            item.setIcon(R.drawable.ic_bluetooth_enabled);
            return true;
        }
        if (id == R.id.action_network) {
            Drawable drawable = item.getIcon();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, getResources().getColor(R.color.colorIconEnabled));
            item.setIcon(drawable);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
