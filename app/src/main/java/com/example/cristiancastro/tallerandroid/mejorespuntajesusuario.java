package com.example.cristiancastro.tallerandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;

import Dominio.Ahorcado;
import Dominio.Partida;
import Dominio.UsuarioPublico;

public class mejorespuntajesusuario extends AppCompatActivity {

    Context Micontext;
    ArrayAdapter<Partida> adaptador;
    ArrayList<Partida> listaDePartidas;

    Bundle b;
    UsuarioPublico u;

    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejorespuntajesusuario);
        Micontext = getApplicationContext();

        b = getIntent().getExtras();
        Ahorcado aho = new Ahorcado();
        u = new UsuarioPublico();
        u.setIdUP(b.getInt("Usuario"));
        u = aho.SeleccionarEspecificaUsuarioPublicoPorId(u,Micontext);

        //CARGA LISTA A MOSTRAR
        try {
            listaDePartidas = aho.TopCincoMejoresCelularPartidas(u,Micontext);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ListView list = (ListView)findViewById(R.id.lstVerMejoresPuntajesCelular);
        adaptador = new ArrayAdapter<Partida>(Micontext,android.R.layout.simple_spinner_item,listaDePartidas);
        list.setAdapter(adaptador);

        TV = (TextView)findViewById(R.id.lblMPU);                                                                                                  //medio de un ID

        String font_path = "font/MixBrush.ttf";                                                                             //donde tiene que buscar ) de nuetra fuente

        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);


        TV.setTypeface(TF);
    }


}
