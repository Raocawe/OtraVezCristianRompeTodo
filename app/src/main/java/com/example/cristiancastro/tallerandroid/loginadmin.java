package com.example.cristiancastro.tallerandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class loginadmin extends AppCompatActivity {

    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginadmin);


        TV = (TextView)findViewById(R.id.lblBienvenido);

        String font_path = "font/MixBrush.ttf";

        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);


        TV.setTypeface(TF);
    }

    public void CrearPalabra(View view)
    {
        try{
            Class<?> clase = Class.forName("com.example.cristiancastro.tallerandroid.agregarpalabra");
            Intent i = new Intent(this, clase);
            startActivity(i);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void ModificarPalabra(View view)
    {

        try{
            Class<?> clase = Class.forName("com.example.cristiancastro.tallerandroid.modificarpalabra");
            Intent i = new Intent(this, clase);
            startActivity(i);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
