package com.example.cristiancastro.tallerandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import Dominio.Ahorcado;
import Dominio.Palabra;
import Dominio.Partida;
import Dominio.UsuarioPublico;

public class partidaActivity extends AppCompatActivity {

    Bundle b;
    UsuarioPublico u;
    Context MiContext;
    Chronometer crono;
    long elapseTime = 0;
    Ahorcado ahorcado;
    TextView Puntaje;
    Button Play,Pause;
    String[] PalabraTraida;
    String[] PalabraMostrar;
    int ContadorErrores;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_partida);
        MiContext = getApplicationContext();
        final Button Play =(Button)findViewById(R.id.btnPlay);
        final Button Pause =(Button)findViewById(R.id.btnPausa);
        Puntaje = (TextView) findViewById(R.id.lblPuntaje);
        Puntaje.setText("0");

        crono = (Chronometer)findViewById(R.id.crono);
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        Play.setEnabled(true);
        Pause.setEnabled(false);
        b = getIntent().getExtras();
        ahorcado = new Ahorcado();
        u = new UsuarioPublico();
       // u.setIdUP(b.getInt("Usuario"));
       // u = ahorcado.SeleccionarEspecificaUsuarioPublicoPorId(u,MiContext);

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crono.setBase(SystemClock.elapsedRealtime()+elapseTime);
                crono.start();
                Pause.setEnabled(true);
                Play.setEnabled(false);
            }
        });
        Pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                crono.stop();
                elapseTime = crono.getBase();
                Play.setEnabled(true);
                Pause.setEnabled(false);
            }
        });
    }

    public boolean FinalizarPartida()
    {
        java.util.Date fecha = new Date();
        Partida newP = new Partida();

        newP.setIdUP(u.getIdUP());
        newP.setFechaPartidaN(fecha);
        newP.setPuntajePartida(Integer.parseInt(Puntaje.getText().toString()));

        if(ahorcado.guardarPartida(newP,MiContext))
        {
            return true;
        }
        return false;
    }

    public void Q(View V)
    {
       for (int i=0 ; i< PalabraTraida.length; i++)
       {
           if(PalabraTraida[i] == "q")
           {
               PalabraMostrar[i] = "q";
               Button Q = (Button) findViewById(R.id.btnQ);
               Q.setEnabled(false);
           }
           else
           {
               ContadorErrores++;

           }
       }
    }




}
