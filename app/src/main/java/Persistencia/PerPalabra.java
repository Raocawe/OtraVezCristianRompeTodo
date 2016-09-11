package Persistencia;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import Dominio.Palabra;

/**
 * Created by cristian castro on 04/08/2016.
 */
public class PerPalabra extends SqlLite {

    public PerPalabra(Context contexto) {
        super(contexto);
    }

    public boolean guardar(Palabra p)
    {
        boolean retorno = false;
        if(p != null) {
            this.ejecutarSentencia("INSERT INTO Palabra (NombreP,DescripcionP,CantidadLetras,ReferenciaP) " + "VALUES " +
                    "('" + p.getNombreP() + "',' " + p.getDescripcionP() + "', " + p.getCantidadLetras() + ", '" + p.getReferenciaP() + "')");
        retorno = true;
        }
        return retorno;
    }

    public ArrayList<Palabra> SeleccionarPorNivel(int pint)
    {
        ArrayList<Palabra> Palabras = new ArrayList<>();
        //Lo retornado se asigna al cursor que se encuantra en SQLite
        this.seleccionar("SELECT * FROM Palabra where CantidadLetras = "+ pint +" ");
        while(!this.c.isAfterLast())
        {
            Palabra palabra = new Palabra();
            palabra.setIdP(c.getInt(4));
            palabra.setNombreP(c.getString(0));
            palabra.setDescripcionP(c.getString(1));
            palabra.setCantidadLetras(c.getInt(2));
            palabra.setReferenciaP(c.getString(3));
            Palabras.add(palabra);
            this.c.moveToNext();
        }
        this.c.close();
        return Palabras;
    }

    public boolean ModificarPalabra(Palabra p,String pPal)
    {
        boolean retorno = false;
        if (p != null)
        {
            this.ejecutarSentencia("update Palabra SET NombreP = '" + p.getNombreP() +
                    "', DescripcionP =' " + p.getDescripcionP() +
                    "', CantidadLetras = " + p.getCantidadLetras() +
                    ", ReferenciaP = '" + p.getReferenciaP() + "' where NombreP = '" + pPal + "'");
            retorno = true;
        }
        return retorno;
    }

    public boolean existePalabra(Palabra p)
    {
        boolean existe = false;
        //Lo retornado se asigna al cursor que se encuantra en SQLite
        this.seleccionar("SELECT * FROM Palabra where NombreP = '"+ p.getNombreP() + "'");
        while(this.c.isAfterLast() == false)
        {
            existe = true;
            this.c.moveToNext();
        }
        this.c.close();
        return existe;
    }
}
