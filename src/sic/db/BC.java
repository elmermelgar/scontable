/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

/**
 *
 * @author Oscar
 */
public class BC{
    
    private String codigo;
    private String nombre;
    private Double debe;
    private Double haber;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDebe() {
        return debe;
    }

    public void setDebe(Double debe) {
        this.debe = debe;
    }

    public Double getHaber() {
        return haber;
    }

    public void setHaber(Double haber) {
        this.haber = haber;
    }
    
}
