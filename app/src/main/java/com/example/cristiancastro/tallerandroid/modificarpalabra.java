package com.example.cristiancastro.tallerandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Dominio.Ahorcado;
import Dominio.Palabra;

public class modificarpalabra extends AppCompatActivity {

    Context MiContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarpalabra);
        MiContext = getApplicationContext();

        TextView TV;

        TV = (TextView)findViewById(R.id.lblMp);

        String font_path = "font/MixBrush.ttf";

        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);


        TV.setTypeface(TF);
    }

    public void Enviar(View e)
    {
        if (ValidarCampos()) {

            TextView Palabra = (TextView) findViewById(R.id.txtModificar);
            TextView Referencia = (TextView) findViewById(R.id.txtReferencia);
            TextView Descripcion = (TextView) findViewById(R.id.txtDefinicion);

            Dominio.Palabra pal = new Palabra();
            String Nombree = Palabra.getText().toString().toLowerCase();
            pal.setNombreP(Nombree);
            pal.setReferenciaP(Referencia.getText().toString());
            pal.setDescripcionP(Descripcion.getText().toString());
            pal.setCantidadLetras(Nombree.toCharArray().length);

            Ahorcado ahorcado = new Ahorcado();

            if (ahorcado.ExistePalabra(pal,MiContext)) {
                if(ahorcado.ModificarPalabra(pal,Nombree,MiContext)) {
                    Toast.makeText(MiContext, "Palabra Modificada", Toast.LENGTH_SHORT).show();
                    Limpiar();
                }
            } else {
                Toast.makeText(MiContext, "Palabra No Encontrada", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(MiContext, "Completar Todos los Campos", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean ValidarCampos()
    {
        TextView Palabra = (TextView) findViewById(R.id.txtModificar);
        TextView Referencia = (TextView) findViewById(R.id.txtReferencia);
        TextView Descripcion = (TextView) findViewById(R.id.txtDefinicion);
        TextView Nombre = (TextView) findViewById(R.id.txtNombre);

        return (!Nombre.getText().toString().isEmpty() &&
                !Palabra.getText().toString().isEmpty() &&
                !Referencia.getText().toString().isEmpty() &&
                !Descripcion.getText().toString().isEmpty());
    }

    public void Limpiar()
    {
        TextView Palabra = (TextView) findViewById(R.id.txtPalabra);
        TextView Referencia = (TextView) findViewById(R.id.txtReferencia);
        TextView Descripcion = (TextView) findViewById(R.id.txtDefinicion);

        Palabra.setText(" ");
        Referencia.setText(" ");
        Descripcion.setText(" ");
    }
}
