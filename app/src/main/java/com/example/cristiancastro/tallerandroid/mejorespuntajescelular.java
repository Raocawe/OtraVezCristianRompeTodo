package com.example.cristiancastro.tallerandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import Dominio.Ahorcado;
import Dominio.Partida;
import Dominio.UsuarioPublico;

public class mejorespuntajescelular extends AppCompatActivity {

    Context MiContext;
    ArrayAdapter<Partida> adaptador;
    ArrayList<Partida> listaDePartidas;


    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejorespuntajescelular);
        MiContext = getApplicationContext();
        Ahorcado aho = new Ahorcado();



        //CARGA LISTA A MOSTRAR
        try {
            listaDePartidas = aho.TopCincoMejoresPartidas(MiContext);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ListView list = (ListView)findViewById(R.id.lstVerMejoresPuntajesCelular);
        adaptador = new ArrayAdapter<Partida>(MiContext,android.R.layout.simple_spinner_item,listaDePartidas);
        list.setAdapter(adaptador);


        TV = (TextView)findViewById(R.id.lblMPC);

        String font_path = "font/MixBrush.ttf";

        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);


        TV.setTypeface(TF);

    }
}
