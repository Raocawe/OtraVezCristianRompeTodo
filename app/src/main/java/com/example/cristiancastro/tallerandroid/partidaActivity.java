package com.example.cristiancastro.tallerandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    ImageView Errores;
    TextView Puntaje, mos, MostrarNivel;
    String[] PalabraTraida;
    String[] PalabraMostrar;
    String formato = "";
    ArrayList<String> BottonesUsados;

    int Nivel = 0;
    int ContadorErrores;
    int ErroresACompletar = 7;
    int CorrectasACompletar ;
    int Correctas;
    int Puntos = -5;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_partida);
        MiContext = getApplicationContext();
        final ImageButton Pause = (ImageButton) findViewById(R.id.btnPausa);
        Puntaje = (TextView) findViewById(R.id.lblPuntaje);
        mos = (TextView) findViewById(R.id.lblPalabraMostrar);
        MostrarNivel = (TextView) findViewById(R.id.lblNivel);
        Puntaje.setText(Integer.toString(Puntos));
        BottonesUsados = new ArrayList<String>();
        Errores =(ImageView) findViewById(R.id.imgerror);

        IniciarLevel();

        crono = (Chronometer) findViewById(R.id.crono);
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();

        //Play.setEnabled(false);
        Pause.setEnabled(true);
        b = getIntent().getExtras();
        Ahorcado ahorcado = new Ahorcado();
        u = new UsuarioPublico();
        u.setIdUP(b.getInt("Usuario"));
        u = ahorcado.SeleccionarEspecificaUsuarioPublicoPorId(u, MiContext);

        /*Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crono.start();
                crono.setText(formato);
                Pause.setEnabled(true);
                Play.setEnabled(false);
            }
        });*/

        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crono.stop();
                formato = crono.getText().toString();
                //Play.setEnabled(true);
                Pause.setEnabled(false);
            }
        });
    }

    public boolean FinalizarPartida() {
        Ahorcado aho = new Ahorcado();
        Date fecha = new Date();
        Partida newP = new Partida();

        newP.setIdUP(u.getIdUP());
        newP.setFechaPartidaN(fecha);
        newP.setPuntajePartida(Integer.parseInt(Puntaje.getText().toString()));

        return aho.guardarPartida(newP, MiContext);
    }

    public void IniciarLevel() {
        Correctas = -1;
        ContadorErrores = 0;
        Puntos += 5;
        Nivel++;
        MostrarNivel.setText(Integer.toString(Nivel));
        BuscarPalabra();
        ActualizarPalabra();
        CambiarImagen();
        ReiniciarBotones();
    }

    public void ActualizarPalabra() {
        String mostrar = "";
        for (int i = 0; i < PalabraMostrar.length; i++) {
            mostrar = mostrar.concat(PalabraMostrar[i] + " ");
        }
        mos.setText(mostrar);
        Correctas++;
    }

    public String[] TransformarStringaArray (String pS)
    {
        String[] retorno = new String[pS.length()];
        for(int i = 0; i < pS.length(); i++)
        {
            retorno[i] = String.valueOf(pS.charAt(i));
    }
        return retorno;
    }

    public void BuscarPalabra() {
        Ahorcado aho = new Ahorcado();
        Palabra pal = new Palabra();
        int numLet = aho.LevelDevuelveCantLetras(Nivel);
        CorrectasACompletar = numLet;
        String Mostrar = aho.LevelDevuelveReferencias(Nivel);
        ArrayList<Palabra> ListaPalabras = aho.SeleccionarPorNivelCantLetras(numLet, MiContext);
        if (!ListaPalabras.isEmpty()) {
            Random rnd = new Random();
            int Elejido = (int) (rnd.nextDouble() * ListaPalabras.toArray().length);
            pal = ListaPalabras.get(Elejido);

            if (pal != null) {
                //region CargarPalabraAVariables
                String Nombre2[] = TransformarStringaArray(pal.getNombreP());
                PalabraTraida = Nombre2;
                String[] PalabraMstrar = new String[Nombre2.length];

                for (int i = 0; i < Nombre2.length; i++) {
                    PalabraMstrar[i] = "_ ";
                }
                PalabraMostrar = PalabraMstrar;
                //endregion
            }
        } else {
            Toast.makeText(MiContext, "No hay palabras asignadas a este Nivel", Toast.LENGTH_SHORT).show();
        }
    }

    public void CambiarImagen() {

        switch(ContadorErrores)
        {
            case 0:
                Errores.setImageResource(R.drawable.una);
                break;
            case 1:
                Errores.setImageResource(R.drawable.dos);
                break;
            case 2:
                Errores.setImageResource(R.drawable.tres);
                break;
            case 3:
                Errores.setImageResource(R.drawable.cuatro);
                break;
            case 4:
                Errores.setImageResource(R.drawable.sinco);
                break;
            case 5:
                Errores.setImageResource(R.drawable.seis);
                break;
            case 6:
                Errores.setImageResource(R.drawable.siete);
                break;
            case 7:
                Errores.setImageResource(R.drawable.ocho);
                break;
        }
    }

    public void ReiniciarBotones()
    {
        if(!BottonesUsados.isEmpty())
        {
            for (int i = 0; i < BottonesUsados.size(); i++)
            {
                ActivarBotton(BottonesUsados.get(i));
            }
            BottonesUsados.clear();
        }
    }

    public void ActivarBotton(String pBtn)
    {
        switch(pBtn)
        {
            case "A":
                Button A =(Button) findViewById(R.id.btnA);
                A.setEnabled(true);
                break;
            case "B":
                Button B =(Button) findViewById(R.id.btnB);
                B.setEnabled(true);
                break;
            case "C":
                Button C =(Button) findViewById(R.id.btnC);
                C.setEnabled(true);
                break;
            case "D":
                Button D =(Button) findViewById(R.id.btnD);
                D.setEnabled(true);
                break;
            case "E":
                Button E =(Button) findViewById(R.id.brnE);
                E.setEnabled(true);
                break;
            case "F":
                Button F =(Button) findViewById(R.id.btnF);
                F.setEnabled(true);
                break;
            case "G":
                Button G =(Button) findViewById(R.id.btnG);
                G.setEnabled(true);
                break;
            case "H":
                Button H =(Button) findViewById(R.id.btnH);
                H.setEnabled(true);
                break;
            case "I":
                Button I =(Button) findViewById(R.id.btnI);
                I.setEnabled(true);
                break;
            case "J":
                Button J =(Button) findViewById(R.id.btnJ);
                J.setEnabled(true);
                break;
            case "K":
                Button K =(Button) findViewById(R.id.btnK);
                K.setEnabled(true);
                break;
            case "L":
                Button L =(Button) findViewById(R.id.btnL);
                L.setEnabled(true);
                break;
            case "M":
                Button M =(Button) findViewById(R.id.btnM);
                M.setEnabled(true);
                break;
            case "N":
                Button N =(Button) findViewById(R.id.btnN);
                N.setEnabled(true);
                break;
            case "Ñ":
                Button Ñ =(Button) findViewById(R.id.btnÑ);
                Ñ.setEnabled(true);
                break;
            case "P":
                Button P =(Button) findViewById(R.id.btnP);
                P.setEnabled(true);
                break;
            case "Q":
                Button Q =(Button) findViewById(R.id.btnQ);
                Q.setEnabled(true);
                break;
            case "O":
                Button O =(Button) findViewById(R.id.btnO);
                O.setEnabled(true);
                break;
            case "R":
                Button r =(Button) findViewById(R.id.btnR);
                r.setEnabled(true);
                break;
            case "S":
                Button S =(Button) findViewById(R.id.btnS);
                S.setEnabled(true);
                break;
            case "T":
                Button T =(Button) findViewById(R.id.btnT);
                T.setEnabled(true);
                break;
            case "U":
                Button U =(Button) findViewById(R.id.btnU);
                U.setEnabled(true);
                break;
            case "X":
                Button X =(Button) findViewById(R.id.btnX);
                X.setEnabled(true);
                break;
            case "Y":
                Button Y =(Button) findViewById(R.id.btnY);
                Y.setEnabled(true);
                break;
            case "W":
                Button W =(Button) findViewById(R.id.btnW);
                W.setEnabled(true);
                break;
            case "Z":
                Button Z =(Button) findViewById(R.id.btnZ);
                Z.setEnabled(true);
                break;

        }
    }

    public void ControlarErroresYCorrectas() //Tambien Actualiza Puntajes
    {
        Puntaje.setText(Integer.toString(Puntos));

        if (ErroresACompletar == ContadorErrores)
        {
            FinalizarPartida();
        }

        if (Correctas == CorrectasACompletar) {
            IniciarLevel();
        }
    }

    //region TeclasControl
    public void Q(View V) {
        Button Q = (Button) findViewById(R.id.btnQ);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("q")) {
                PalabraMostrar[i] = "q";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("Q");
        Q.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void W(View V) {
        Button W = (Button) findViewById(R.id.btnW);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("w")) {
                PalabraMostrar[i] = "w";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("W");
        W.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void E(View V) {
        Button E = (Button) findViewById(R.id.brnE);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("e")) {
                PalabraMostrar[i] = "e";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("E");
        E.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void R(View V) {
        Button r = (Button) findViewById(R.id.btnR);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("r")) {
                PalabraMostrar[i] = "r";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("R");
        r.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void T(View V) {
        Button T = (Button) findViewById(R.id.btnT);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("t")) {
                PalabraMostrar[i] = "t";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("T");
        T.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void Y(View V) {
        Button Y = (Button) findViewById(R.id.btnY);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("y")) {
                PalabraMostrar[i] = "y";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("Y");
        Y.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void U(View V) {
        Button U = (Button) findViewById(R.id.btnU);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("u")) {
                PalabraMostrar[i] = "u";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("U");
        U.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void I(View V) {
        Button I = (Button) findViewById(R.id.btnI);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("i")) {
                PalabraMostrar[i] = "i";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("I");
        I.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void O(View V) {
        Button O = (Button) findViewById(R.id.btnO);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("o")) {
                PalabraMostrar[i] = "o";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("O");
        O.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void P(View V) {
        Button P = (Button) findViewById(R.id.btnP);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("p")) {
                PalabraMostrar[i] = "p";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("P");
        P.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void A(View V) {
        Button A = (Button) findViewById(R.id.btnA);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("a"))
            {
                PalabraMostrar[i] = "a";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("A");
        A.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void S(View V) {
        Button S = (Button) findViewById(R.id.btnS);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("s")) {
                PalabraMostrar[i] = "s";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("S");
        S.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void D(View V) {
        Button D = (Button) findViewById(R.id.btnD);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("d")) {
                PalabraMostrar[i] = "d";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("D");
        D.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void F(View V) {
        Button F = (Button) findViewById(R.id.btnF);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("f")) {
                PalabraMostrar[i] = "f";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("F");
        F.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void G(View V) {
        Button G = (Button) findViewById(R.id.btnG);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("g")) {
                PalabraMostrar[i] = "g";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("G");
        G.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void H(View V) {
        Button H = (Button) findViewById(R.id.btnH);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("h")) {
                PalabraMostrar[i] = "h";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("H");
        H.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void J(View V) {
        Button J = (Button) findViewById(R.id.btnJ);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("j")) {
                PalabraMostrar[i] = "j";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("J");
        J.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void K(View V) {
        Button K = (Button) findViewById(R.id.btnK);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("k")) {
                PalabraMostrar[i] = "k";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("K");
        K.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void L(View V) {
        Button L = (Button) findViewById(R.id.btnL);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("l")) {
                PalabraMostrar[i] = "l";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("L");
        L.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void Z(View V) {
        Button Z = (Button) findViewById(R.id.btnZ);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("z")) {
                PalabraMostrar[i] = "z";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("Z");
        Z.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void Ñ(View V) {
        Button Ñ = (Button) findViewById(R.id.btnÑ);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("ñ")) {
                PalabraMostrar[i] = "ñ";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("Ñ");
        Ñ.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void X(View V) {
        Button X = (Button) findViewById(R.id.btnX);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("x")) {
                PalabraMostrar[i] = "x";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("X");
        X.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void C(View V) {
        Button C = (Button) findViewById(R.id.btnC);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("c")) {
                PalabraMostrar[i] = "c";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("C");
        C.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void V(View V) {
        Button v = (Button) findViewById(R.id.btnV);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("v")) {
                PalabraMostrar[i] = "v";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("V");
        v.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void B(View V) {
        Button B = (Button) findViewById(R.id.btnB);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("b")) {
                PalabraMostrar[i] = "b";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("B");
        B.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void N(View V) {
        Button N = (Button) findViewById(R.id.btnN);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("n")) {
                PalabraMostrar[i] = "n";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("N");
        N.setEnabled(false);
        ControlarErroresYCorrectas();
    }

    public void M(View V) {
        Button M = (Button) findViewById(R.id.btnM);
        boolean encontrado = false;
        for (int i = 0; i < PalabraTraida.length; i++) {
            if (PalabraTraida[i].equals("m")) {
                PalabraMostrar[i] = "m";
                Puntos += 2;
                ActualizarPalabra();
                encontrado = true;
            }
        }
        if (!encontrado) {
            ContadorErrores++;
            CambiarImagen();
            Puntos -= 1;
        }
        BottonesUsados.add("M");
        M.setEnabled(false);
        ControlarErroresYCorrectas();
    }
    //endregion
}
