package com.example.cristiancastro.tallerandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import Dominio.Ahorcado;
import Dominio.UsuarioPublico;

public class registroActivity extends AppCompatActivity {

    Context Micontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro);
        Micontext = getApplicationContext();
    }

    public void Enviar(View e)
    {
        if (ValidarCampos()) {
            TextView Nombre = (TextView) findViewById(R.id.txtNombre);
            TextView Contraseña = (TextView) findViewById(R.id.txtPass);
            TextView Usuario = (TextView) findViewById(R.id.txtUsuario);
            TextView Apellido = (TextView) findViewById(R.id.txtApellido);
            TextView Edad = (TextView) findViewById(R.id.txtEdad);
            TextView Email = (TextView) findViewById(R.id.txtEmail);
            TextView Nacionalidad = (TextView) findViewById(R.id.txtNacionalidad);
            Ahorcado ahorcado = new Ahorcado();
            UsuarioPublico up = new UsuarioPublico();

            up.setUsuarioU(Usuario.getText().toString());

            if (ahorcado.existeUsuarioUsuarioPublico(up,Micontext)) {
                Toast.makeText(getApplicationContext(), "Ya existe ese Usuario", Toast.LENGTH_SHORT).show();
            } else {
                up.setNacionalidadUP(Nacionalidad.getText().toString());
                up.setEmailUP(Email.getText().toString());
                up.setNombreU(Nombre.getText().toString());
                up.setContraseñaU(Contraseña.getText().toString());
                up.setApellidoU(Apellido.getText().toString());
                up.setEdadUP(Integer.parseInt(Edad.getText().toString()));

                ahorcado.guardarUsuarioPublico(up,Micontext);
                Toast.makeText(Micontext, "Usuario Agregado", Toast.LENGTH_SHORT).show();
            }
        }//asdsd
        else
        {
            Toast.makeText(Micontext, "Completar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean ValidarCampos()
    {
        TextView Nombre = (TextView) findViewById(R.id.txtNombre);
        TextView Contraseña = (TextView) findViewById(R.id.txtPass);
        TextView Usuario = (TextView) findViewById(R.id.txtUsuario);
        TextView Apellido = (TextView) findViewById(R.id.txtApellido);
        TextView Edad = (TextView) findViewById(R.id.txtEdad);
        TextView Email = (TextView) findViewById(R.id.txtEmail);
        TextView Nacionalidad = (TextView) findViewById(R.id.txtNacionalidad);

        return !Nombre.getText().toString().isEmpty() && !Contraseña.getText().toString().isEmpty()
                && !Usuario.getText().toString().isEmpty() && !Apellido.getText().toString().isEmpty()
                && !Edad.getText().toString().isEmpty() && !Email.getText().toString().isEmpty()
                && !Nacionalidad.getText().toString().isEmpty();
    }

}
