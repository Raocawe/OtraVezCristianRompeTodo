package com.example.cristiancastro.tallerandroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import Dominio.Ahorcado;
import Dominio.UsuarioPrivado;
import Dominio.UsuarioPublico;
import Persistencia.SqlLite;

public class Maininicio extends AppCompatActivity {

    Context MiContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maininicio);
        MiContext = getApplicationContext();
       // Trabajando();
    }

    public void Registro(View v)
    {
        try{
            Class<?> clase = Class.forName("com.example.cristiancastro.tallerandroid.registroActivity");
            Intent i = new Intent(this, clase);
            startActivity(i);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void Enviar(View v)
    {
        if(ValidarEntrada())
        {
            TextView Usuario = (TextView) findViewById(R.id.txtUsuario);
            TextView Contraseña = (TextView) findViewById(R.id.txtContraseña);

            Ahorcado ahorcado = new Ahorcado();
            UsuarioPrivado upr = new UsuarioPrivado();

            upr.setContraseñaU(Contraseña.getText().toString());
            upr.setUsuarioU(Usuario.getText().toString());
            upr = ahorcado.SeleccionarEspecificaUsuarioPrivado(upr,MiContext);

            if(upr != null)
            {
                try {
                    Class<?> clase = Class.forName("com.example.cristiancastro.tallerandroid.loginadmin");
                    Intent i = new Intent(this, clase);
                    startActivity(i);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                UsuarioPublico up = new UsuarioPublico();

                up.setContraseñaU(Contraseña.getText().toString());
                up.setUsuarioU(Usuario.getText().toString());

                up = ahorcado.SeleccionarEspecificaUsuarioPublico(up,MiContext);

                if (up != null) {
                    try {
                        Class<?> clase = Class.forName("com.example.cristiancastro.tallerandroid.inicio");
                        Intent i = new Intent(this, clase);
                        i.putExtra("Usuario", up.getIdUP());
                        startActivity(i);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MiContext, "Usuario o Contraseña Incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            Toast.makeText(MiContext, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean ValidarEntrada()
    {
        TextView Usuario = (TextView) findViewById(R.id.txtUsuario);
        TextView Contraseña = (TextView) findViewById(R.id.txtContraseña);

        return !Usuario.getText().toString().isEmpty() && !Contraseña.getText().toString().isEmpty();
    }

    public void Trabajando()
    {
        try
        {
            Class<?> clase = Class.forName("com.example.cristiancastro.tallerandroid.partidaActivity");
            Intent i = new Intent(this, clase);
            startActivity(i);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
