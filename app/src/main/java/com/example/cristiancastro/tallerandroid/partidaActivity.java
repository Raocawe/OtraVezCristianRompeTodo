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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import Dominio.Ahorcado;
import Dominio.Palabra;
import Dominio.Partida;
import Dominio.UsuarioPublico;

public class partidaActivity extends AppCompatActivity {

    Bundle b;
    UsuarioPublico u;
    Context MiContext;
    Chronometer crono;
    TextView Puntaje,mos;
    String[] PalabraTraida ;
    String[] PalabraMostrar ;
    String formato = "";

    int Nivel = 1;
    int ContadorErrores = 0;
    int CorrectasACompletar;
    int Correctas = 0;
    int Puntos = 0;

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
        mos = (TextView)findViewById(R.id.lblPalabraMostrar);
        Puntaje.setText(Integer.toString(Puntos));

        BuscarPalabra();
        ActualizarPalabra();

        crono = (Chronometer)findViewById(R.id.crono);
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        Play.setEnabled(false);
        Pause.setEnabled(true);
        b = getIntent().getExtras();
        Ahorcado ahorcado = new Ahorcado();
        u = new UsuarioPublico();
        u.setIdUP(b.getInt("Usuario"));
        u = ahorcado.SeleccionarEspecificaUsuarioPublicoPorId(u,MiContext);

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crono.start();
                crono.setText(formato);
                Pause.setEnabled(true);
                Play.setEnabled(false);
            }
        });
        Pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                crono.stop();
                formato = crono.getText().toString();
                Play.setEnabled(true);
                Pause.setEnabled(false);
            }
        });
    }

    public boolean FinalizarPartida()
    {
        Ahorcado aho = new Ahorcado();
        java.util.Date fecha = new Date();
        Partida newP = new Partida();

        newP.setIdUP(u.getIdUP());
        newP.setFechaPartidaN(fecha);
        newP.setPuntajePartida(Integer.parseInt(Puntaje.getText().toString()));

        if(aho.guardarPartida(newP,MiContext))
        {
            return true;
        }
        return false;
    }

    public void IniciarLevel()
    {
        Correctas = 0;
        ContadorErrores = 0;
        Nivel++;
        BuscarPalabra();
        ActualizarPalabra();
        CambiarImagen(ContadorErrores);
    }

    public void ActualizarPalabra()
    {
        String mostrar = "";
        for(int i=0; i<PalabraMostrar.length ; i++)
        {
            mostrar = mostrar.concat(PalabraMostrar[i]+" ");
        }
        mos.setText(mostrar);
        Correctas++;
        if (Correctas == CorrectasACompletar)
        {
            IniciarLevel();
        }
    }

    public void BuscarPalabra()
    {
        Ahorcado aho = new Ahorcado();
        Palabra pal = new Palabra();
        CorrectasACompletar = aho.LevelDevuelveCantLetras(Nivel);
        String Mostrar = aho.LevelDevuelveReferencias(Nivel);
        ArrayList<Palabra> ListaPalabras = aho.SeleccionarPorNivelCantLetras(CorrectasACompletar,MiContext);
        if (ListaPalabras != null) {
            Random rnd = new Random();
            int Elejido = (int) (rnd.nextDouble() * ListaPalabras.toArray().length-1);
            pal = ListaPalabras.get(Elejido);

            if (pal != null) {

                //region CargarPalabraAVariables
                String[] Nombre = pal.getNombreP().split("");
                String[] Nombre2 = new String[Nombre.length-1];
                for(int d = 1 ; d<Nombre.length-1; d++)
                {
                    Nombre2[d] = Nombre[d];
                }
                PalabraTraida = Nombre2;
                String[] PalabraMstrar = new String[PalabraTraida.length];

                for (int i = 0; i < PalabraTraida.length; i++) {
                    PalabraMstrar[i] = "_ ";
                }
                PalabraMostrar = PalabraMstrar;
                //endregion
            }
        }
        else
        {
            Toast.makeText(MiContext, "No hay palabras asignadas a este Nivel", Toast.LENGTH_SHORT).show();
        }
    }

    public void CambiarImagen(int pError)
    {

    }

    //region TeclasControl
    public void Q(View V)
    {Button Q = (Button) findViewById(R.id.btnQ);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "q")
            {
                PalabraMostrar[i] = "q";
                Q.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            Q.setEnabled(false);
        }
    }

    public void W(View V)
    {Button W = (Button) findViewById(R.id.btnW);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "w")
            {
                PalabraMostrar[i] = "w";
                W.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            W.setEnabled(false);
        }
    }

    public void E(View V)
    {Button E = (Button) findViewById(R.id.btnR);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "e")
            {
                PalabraMostrar[i] = "e";
                E.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            E.setEnabled(false);
        }
    }

    public void R(View V)
    {Button r = (Button) findViewById(R.id.btnR);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "r")
            {
                PalabraMostrar[i] = "r";
                r.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            r.setEnabled(false);
        }
    }

    public void T(View V)
    {Button T = (Button) findViewById(R.id.btnT);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "t")
            {
                PalabraMostrar[i] = "t";
                T.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            T.setEnabled(false);
        }
    }

    public void Y(View V)
    {Button Y = (Button) findViewById(R.id.btnY);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "y")
            {
                PalabraMostrar[i] = "y";
                Y.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            Y.setEnabled(false);
        }
    }

    public void U(View V)
    {Button U = (Button) findViewById(R.id.btnU);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "u")
            {
                PalabraMostrar[i] = "u";
                U.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            U.setEnabled(false);
        }
    }

    public void I(View V)
    {Button I = (Button) findViewById(R.id.btnI);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "i")
            {
                PalabraMostrar[i] = "i";
                I.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            I.setEnabled(false);
        }
    }

    public void O(View V)
    {Button O = (Button) findViewById(R.id.btnO);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "o")
            {
                PalabraMostrar[i] = "o";
                O.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            O.setEnabled(false);
        }
    }

    public void P(View V)
    {Button P = (Button) findViewById(R.id.btnP);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "p")
            {
                PalabraMostrar[i] = "p";
                P.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            P.setEnabled(false);
        }
    }

    public void A(View V)
    {Button A = (Button) findViewById(R.id.btnA);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "a")
            {
                PalabraMostrar[i] = "a";
                A.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            A.setEnabled(false);
        }
    }

    public void S(View V)
    {Button S = (Button) findViewById(R.id.btnS);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "s")
            {
                PalabraMostrar[i] = "s";
                S.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            S.setEnabled(false);
        }
    }

    public void D(View V)
    {Button D = (Button) findViewById(R.id.btnD);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "d")
            {
                PalabraMostrar[i] = "d";
                D.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            D.setEnabled(false);
        }
    }

    public void F(View V)
    {Button F = (Button) findViewById(R.id.btnF);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "f")
            {
                PalabraMostrar[i] = "f";
                F.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            F.setEnabled(false);
        }
    }

    public void G(View V)
    {Button G = (Button) findViewById(R.id.btnG);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "g")
            {
                PalabraMostrar[i] = "g";
                G.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            G.setEnabled(false);
        }
    }

    public void H(View V)
    {Button H = (Button) findViewById(R.id.btnH);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "h")
            {
                PalabraMostrar[i] = "h";
                H.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            H.setEnabled(false);
        }
    }

    public void J(View V)
    {Button J = (Button) findViewById(R.id.btnJ);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "j")
            {
                PalabraMostrar[i] = "j";
                J.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            J.setEnabled(false);
        }
    }

    public void K(View V)
    {Button K = (Button) findViewById(R.id.btnK);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "k")
            {
                PalabraMostrar[i] = "k";
                K.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            K.setEnabled(false);
        }
    }

    public void L(View V)
    {Button L = (Button) findViewById(R.id.btnL);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "l")
            {
                PalabraMostrar[i] = "l";
                L.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            L.setEnabled(false);
        }
    }

    public void Z(View V)
    {Button Z = (Button) findViewById(R.id.btnZ);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "z")
            {
                PalabraMostrar[i] = "z";
                Z.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            Z.setEnabled(false);
        }
    }

    public void Ñ(View V)
    {Button Ñ = (Button) findViewById(R.id.btnÑ);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "ñ")
            {
                PalabraMostrar[i] = "ñ";
                Ñ.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            Ñ.setEnabled(false);
        }
    }

    public void X(View V)
    {Button X = (Button) findViewById(R.id.btnX);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "x")
            {
                PalabraMostrar[i] = "x";
                X.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            X.setEnabled(false);
        }
    }

    public void C(View V)
    {Button C = (Button) findViewById(R.id.btnC);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "c")
            {
                PalabraMostrar[i] = "c";
                C.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            C.setEnabled(false);
        }
    }

    public void V(View V)
    {Button v = (Button) findViewById(R.id.btnV);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "v")
            {
                PalabraMostrar[i] = "v";
                v.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            v.setEnabled(false);
        }
    }

    public void B(View V)
    {Button B = (Button) findViewById(R.id.btnB);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "b")
            {
                PalabraMostrar[i] = "b";
                B.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            B.setEnabled(false);
        }
    }

    public void N(View V)
    {Button N = (Button) findViewById(R.id.btnN);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "n")
            {
                PalabraMostrar[i] = "n";
                N.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            N.setEnabled(false);
        }
    }

    public void M(View V)
    {Button M = (Button) findViewById(R.id.btnM);
        boolean encontrado = false;
        for (int i=0 ; i< PalabraTraida.length; i++)
        {
            if(PalabraTraida[i] == "m")
            {
                PalabraMostrar[i] = "m";
                M.setEnabled(false);
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if(!encontrado) {
            ContadorErrores++;
            CambiarImagen(ContadorErrores);
            M.setEnabled(false);
        }
    }
    //endregion
}
