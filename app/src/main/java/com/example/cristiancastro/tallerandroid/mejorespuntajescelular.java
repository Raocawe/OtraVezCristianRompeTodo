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
    ArrayList<Partida> listaDePartidas;


    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejorespuntajescelular);
        MiContext = getApplicationContext();
        Ahorcado aho = new Ahorcado();

        //region CARGA LISTA A MOSTRAR
        try {
            listaDePartidas = aho.TopCincoMejoresPartidas(MiContext);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ArrayList<String> aMostrar = new ArrayList<>();
        Partida pP;
        String sS;
        for(int i=0; i<listaDePartidas.size();i++)
        {
            pP = listaDePartidas.get(i);
            sS = "PUNTAJE: "+ pP.getPuntajePartida() + "    FECHA: "+ pP.getFechaPartidaN();
            aMostrar.add(sS);
        }
        //endregion

        ListView list = (ListView)findViewById(R.id.lstVerMejoresPuntajesCelular);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(MiContext,android.R.layout.simple_spinner_item,aMostrar);
        list.setAdapter(adaptador);


        TV = (TextView)findViewById(R.id.lblMPC);

        String font_path = "font/MixBrush.ttf";

        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);


        TV.setTypeface(TF);

    }
}
