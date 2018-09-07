package com.example.alejandroanzures.conversa_tec;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Quick_Talk extends AppCompatActivity //implements OnDSPermissionsListener
{

   /* DroidSpeech droidSpeech;

    String Speech="Current Speech:\n";
    TextView SpeechView;

    FloatingActionButton fab;

    boolean Status=false;
    int Volume1,Volume2;

    AudioManager audioManager; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_talk);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.quick_talk);
    }

    /*//CSR
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
        if (id == R.id.action_bluetooth) {
            item.setIcon(R.drawable.ic_baseline_bluetooth_enabled);
            return true;
        }
        if (id == R.id.action_network) {
            item.setIcon(R.drawable.ic_baseline_network_enabled);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        SpeechView.setText(Speech+liveSpeechResult);
    }

    @Override
    public void onDroidSpeechFinalResult(String finalSpeechResult)
    {
        // Triggered after the user finishes the speech
        Speech=Speech+finalSpeechResult+'\n';
        SpeechView.setText(Speech);
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
        Toast toast = Toast.makeText(this.getApplicationContext(), "Error:\n"+errorMsg+"\n\nDeteniendo Reconocimiento", Toast.LENGTH_SHORT);
        toast.show();

        fab.setImageResource(android.R.drawable.ic_media_play);
        Status=false;
        droidSpeech.closeDroidSpeechOperations();

        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,Volume1,0);

        }catch (Exception ex){}

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

    }

    @Override
    public void onDroidSpeechAudioPermissionStatus(boolean audioPermissionGiven, String errorMsgIfAny)
    {
        if(!audioPermissionGiven)
        {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Error:--\n"+errorMsgIfAny, Toast.LENGTH_SHORT);
            toast.show();


        }

    }*/

}
