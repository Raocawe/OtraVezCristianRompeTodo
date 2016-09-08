package com.example.cristiancastro.tallerandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class loginadmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginadmin);
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
