package Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by cristian castro on 04/08/2016.
 */
public class SqlLite extends SQLiteOpenHelper {//Extiende para poder gestionar la BDD
    //Ruta a la localización de la BDÐ
    private static String BDD_RUTA = "/data/data/com.example.cristiancastro.tallerandroid/databases/";
    //Nombre de la BDD para acceder a la misma
    private static String BDD_NOMBRE = "bd_ahorcado.sqlite";
    //Para almacenar nuestra BDD en memoria
    private SQLiteDatabase miBdd; //
    private Context miContexto;
    //Elemento para acceder a los datos
    protected Cursor c;

    public SqlLite(Context contexto) {
        super(contexto, BDD_NOMBRE, null, 1);
        this.miContexto = contexto;
        try {
            this.crearDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // llama al método que copia la base de datos desde Assets a la ruta
    // indicada en el teléfonc.
    public void crearDataBase() throws IOException {
        boolean dbExist = comprobarBaseDatos();
        if (!dbExist) {
            this.getWritableDatabase();
            try {
                copiarBaseDatos();
            } catch (IOException e) {
                throw new Error("Error al copiar la Base de Datos");
            }
        }
    }
    // Comprueba si la base de datos ya se encuantra almacenada en la ruta;
    private boolean comprobarBaseDatos()
    {
        SQLiteDatabase checkDB = null;
        try
        {
            String myPath = BDD_RUTA + BDD_NOMBRE;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e)
        {
        }

        if(checkDB != null)
        {
            checkDB.close();
            return true;
        }
        return false;
    }
    private void copiarBaseDatos() throws IOException
    {
        try
        {
            InputStream myInput = miContexto.getAssets().open(BDD_NOMBRE);
            String outFlieName = BDD_RUTA + BDD_NOMBRE;
            OutputStream myOutput = new FileOutputStream(outFlieName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0)
            {
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (IOException e)
        {
            throw e;
        }
    }
    //Abre la base de datps àra la lectura de tuplas.
    public void abrirBaseDatos() throws SQLException
    {
        String myPath = BDD_RUTA + BDD_NOMBRE ;
        //Abrimos la BDD para leer y escribir en la misma
        this.miBdd = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    //Cierra la conexión con la base de datos.
    @Override
    public synchronized void close()
    {
        if(miBdd != null)
        {
            miBdd.close();
        }
        super.close();
    }
    //Se implementa para no desarrollar este código en cada clase en la que se desea selecccionar algo de BDD

    public void seleccionar(String sql)
    {
        this.abrirBaseDatos();
        try {
            //Cargamos en el Cursor lo que retorna la consulta
            this.c = this.miBdd.rawQuery(sql, null);
            this.c.moveToFirst();
        }
        catch (SQLException ex)
        {
            System.err.println("SQLException: "+ ex.getMessage());
        }
        this.miBdd.close();
    }
    //Implementar una sentencia en la BDD
    public void ejecutarSentencia(String sql)
    {
        this.abrirBaseDatos();
        try
        {
            //Ejecutamos la sentencia SQL
            this.miBdd.execSQL(sql);
        }
        catch (SQLException ex)
        {
            System.err.println("SQLException: "+ ex.getMessage());
        }
        this.miBdd.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

