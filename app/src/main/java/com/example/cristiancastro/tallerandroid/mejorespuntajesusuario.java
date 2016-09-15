package com.example.cristiancastro.tallerandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejorespuntajesusuario);
        Micontext = getApplicationContext();
        ListView list = (ListView) findViewById(R.id.lstVerMejoresPuntajesUsuario);
        b = getIntent().getExtras();
        Ahorcado aho = new Ahorcado();
        u = new UsuarioPublico();
        u.setIdUP(b.getInt("Usuario"));
        u = aho.SeleccionarEspecificaUsuarioPublicoPorId(u, Micontext);

        //CARGA LISTA A MOSTRAR
        try {
            listaDePartidas = aho.TopCincoMejoresCelularPartidas(u, Micontext);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        adaptador = new ArrayAdapter<Partida>(Micontext, android.R.layout.simple_spinner_item, listaDePartidas);
        list.setAdapter(adaptador);

        TV = (TextView) findViewById(R.id.lblMPU);                                                                                                  //medio de un ID
        String font_path = "font/MixBrush.ttf";                                                                             //donde tiene que buscar ) de nuetra fuente
        Typeface TF = Typeface.createFromAsset(getAssets(), font_path);
        TV.setTypeface(TF);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "mejorespuntajesusuario Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.cristiancastro.tallerandroid/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "mejorespuntajesusuario Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.cristiancastro.tallerandroid/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
