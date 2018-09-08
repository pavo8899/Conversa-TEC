package com.example.alejandroanzures.conversa_tec;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;
import com.vikramezhil.droidspeech.OnDSPermissionsListener;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Quick_Talk extends AppCompatActivity implements OnDSListener, OnDSPermissionsListener
{

    DroidSpeech droidSpeech;
    TextToSpeech TtoVoice;

    String Respuesta;
    int Volume1;

    TextView textViewRespuesta;
    EditText editTextFrasePersonal;
    Button btnRespuesta,btnPregunta;
    ListView listViewFrases;

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_talk);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.quick_talk);

        droidSpeech = new DroidSpeech(this.getApplicationContext(), this.getFragmentManager());
        /* Set the listener */
        droidSpeech.setOnDroidSpeechListener(this);
        droidSpeech.setContinuousSpeechRecognition(false);
        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        TtoVoice=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    TtoVoice.setLanguage(Locale.getDefault());
                }
            }
        });

        btnRespuesta=(Button)findViewById(R.id.btnRespuesta);
        textViewRespuesta=(TextView)findViewById(R.id.textViewRespuesta);
        listViewFrases=(ListView)findViewById(R.id.listViewFrases);
        editTextFrasePersonal=(EditText)findViewById(R.id.editTextFrasePersonal);
        btnPregunta=(Button)findViewById(R.id.btnPregunta);

        btnRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonRespuestaAction();
            }
        });
        textViewRespuesta.setText(Respuesta);
        btnPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonPreguntaAction();
            }
        });

        String[] Frases={"Hola, ¿Podrías ayudarme?","¿Donde están los baños más cercanos?","Gracias, eres muy amable"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Frases);
        listViewFrases.setAdapter(adaptador);

        listViewFrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemAction(parent,view,position,id);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putString("Respuesta",Respuesta);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        Respuesta = savedInstanceState.getString("Respuesta");
        textViewRespuesta.setText(Respuesta);
    }

    //CSR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void ButtonRespuestaAction()
    {
        droidSpeech.startDroidSpeechRecognition();
    }

    public void onItemAction(AdapterView<?> parent, View view, int position, long id)
    {
        Volume1=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        String Pregunta=listViewFrases.getItemAtPosition(position).toString();
        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,0);

        }catch (Exception ex){}

        Toast.makeText(this, "Realizando Pregunta",Toast.LENGTH_SHORT).show();

        HashMap<String, String> myHashRender = new HashMap<String, String>();


        TtoVoice.speak(Pregunta, TextToSpeech.QUEUE_FLUSH, myHashRender);
        //TtoVoice.speak(Pregunta, TextToSpeech.QUEUE_FLUSH, null);

        textViewRespuesta.setText(Pregunta);

        while (TtoVoice.isSpeaking())
        {

        }
        TtoVoice.stop();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,Volume1,0);

        }catch (Exception ex){}
    }

    public void ButtonPreguntaAction()
    {
        Volume1=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        String Pregunta=editTextFrasePersonal.getText().toString();
        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,0);

        }catch (Exception ex){}

        Toast.makeText(this, "Realizando Pregunta",Toast.LENGTH_SHORT).show();

        HashMap<String, String> myHashRender = new HashMap<String, String>();


        TtoVoice.speak(Pregunta, TextToSpeech.QUEUE_FLUSH, myHashRender);
        //TtoVoice.speak(Pregunta, TextToSpeech.QUEUE_FLUSH, null);

        textViewRespuesta.setText(Pregunta);

        while (TtoVoice.isSpeaking())
        {

        }
        TtoVoice.stop();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,Volume1,0);

        }catch (Exception ex){}
    }
    //droidSpeech Methods
    @Override
    public void onDroidSpeechSupportedLanguages(String currentSpeechLanguage, List<String> supportedSpeechLanguages)
    {
        // Triggered when the device default languages are retrieved
    }

    @Override
    public void onDroidSpeechRmsChanged(float rmsChangedValue)
    {
        // Triggered whenever the sound level in the speech of the user has changed
    }

    @Override
    public void onDroidSpeechLiveResult(String liveSpeechResult)
    {
        // Triggered during live speech of the user`
        textViewRespuesta.setText(liveSpeechResult);
    }

    @Override
    public void onDroidSpeechFinalResult(String finalSpeechResult)
    {
        // Triggered after the user finishes the speech
        Respuesta=finalSpeechResult;
        textViewRespuesta.setText(Respuesta);
    }

    @Override
    public void onDroidSpeechClosedByUser()
    {
        // Triggered if user closes the recognition progress view
    }

    @Override
    public void onDroidSpeechError(String errorMsg)
    {
        // Triggered when droid speech encounters an error
        Toast toast=Toast.makeText(this,errorMsg,Toast.LENGTH_LONG);
        toast.show();

    }

    @Override
    public void onDroidSpeechAudioPermissionStatus(boolean audioPermissionGiven, String errorMsgIfAny)
    {
        if(audioPermissionGiven)
            droidSpeech.startDroidSpeechRecognition();
        else
        {
            Toast toast=Toast.makeText(this,"Error\nNo se puede reconocer voz sin los permisos",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onDestroy()
    {
        droidSpeech.closeDroidSpeechOperations();
        super.onDestroy();
    }

}
