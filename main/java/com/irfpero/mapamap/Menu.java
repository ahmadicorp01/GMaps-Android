package com.irfpero.mapamap;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class Menu extends AppCompatActivity {

    TextToSpeech t1;
    EditText ed1;
    Button b1;
    ImageButton Imagebuttonku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ed1 = (EditText) findViewById(R.id.editText);
        b1 = (Button) findViewById(R.id.button2);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = ed1.getText().toString();
               // Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        Imagebuttonku = (ImageButton) findViewById(R.id.imageButton);
        Imagebuttonku.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent i = new Intent(Menu.this, WebView.class);
                startActivity(i);
            }
        });

        Imagebuttonku = (ImageButton) findViewById(R.id.imageButton2);
        Imagebuttonku.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent i = new Intent(Menu.this, Peraturan.class);
                startActivity(i);
            }
        });

        Imagebuttonku = (ImageButton) findViewById(R.id.imageButton3);
        Imagebuttonku.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent i = new Intent(Menu.this, MainActivity.class);
                startActivity(i);
            }
        });

//        Imagebuttonku = (ImageButton) findViewById(R.id.imageButton4);
//        Imagebuttonku.setOnClickListener(new View.OnClickListener(){
//            public void onClick (View v){
//                Toast.makeText(Menu.this, "Aku Jago4", Toast.LENGTH_LONG).show();
//            }
//        });

    }

    public void onPause()
    {
        if(t1!=null)
        {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
