package Dominio;

import android.content.Context;

import java.util.ArrayList;
import Persistencia.PerPalabra;
/**
 * Created by cristian castro on 04/08/2016.
 */
public class Palabra {
    private int IdP;
    private String NombreP;
    private String DescripcionP;
    private int CantidadLetras;
    private String ReferenciaP;
    public Context context;

    public Palabra(int pIdP,String pNombreP,String pDescripcionP,int pCantidadLetras,String pReferenciaP)
    {
        this.IdP = pIdP;
        this.NombreP = pNombreP;
        this.DescripcionP = pDescripcionP;
        this.CantidadLetras = pCantidadLetras;
        this.ReferenciaP = pReferenciaP;
    }

    public Palabra(){}

    public int getIdP() {
        return IdP;
    }

    public void setIdP(int idP) {
        IdP = idP;
    }

    public String getNombreP() {
        return NombreP;
    }

    public void setNombreP(String nombreP) {
        NombreP = nombreP;
    }

    public String getDescripcionP() {
        return DescripcionP;
    }

    public void setDescripcionP(String descripcionP) {
        DescripcionP = descripcionP;
    }

    public String getReferenciaP() {
        return ReferenciaP;
    }

    public void setReferenciaP(String referenciaP) {
        ReferenciaP = referenciaP;
    }

    public int getCantidadLetras() {
        return CantidadLetras;
    }

    public void setCantidadLetras(int pCantidadLetras) {
        CantidadLetras = pCantidadLetras;
    }

    public boolean guardar(Palabra p,Context pContext)
    {
        PerPalabra pp = new PerPalabra(pContext);
        return pp.guardar(p);
    }
    public ArrayList<Palabra> SeleccionarPorNivel(int pint,Context pContext)
    {
        PerPalabra pp = new PerPalabra(pContext);
        return pp.SeleccionarPorNivel(pint);
    }
    public boolean ModificarPalabra(Palabra p,String pPal,Context pContext)
    {
        PerPalabra pp = new PerPalabra(pContext);
        return pp.ModificarPalabra(p,pPal);
    }

    public boolean existePalabra(Palabra p,Context pContext)
    {
        PerPalabra pp = new PerPalabra(pContext);
        return pp.existePalabra(p);
    }

    public int LevelDevuelveCantLetras (int pLevel)
    {
        switch (pLevel)
        {
            case 1:
                return 4;
            case 2:
                return 4;
            case 3:
                return 5;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 7;
            case 9:
                return 8;
            case 10:
                return 8;
            case 11:
                return 8;
            default:
                return -1;
        }
    }
    public String LevelDevuelveReferencias (int pLevel)
    {
        switch (pLevel)
        {
            case 1:
                return "Referencia";
            case 2:
                return "Palabra";
            case 3:
                return "Referencia";
            case 4:
                return "Palabra";
            case 5:
                return "Referencia";
            case 6:
                return "Palabra";
            case 7:
                return "Referencia";
            case 8:
                return "Palabra";
            case 9:
                return "Referencia";
            case 10:
                return "Palabra";
            case 11:
                return "Ninguna";
            default:
                return "";
        }
    }
}
