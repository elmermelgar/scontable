/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

/**
 *
 * @author Oscar
 */
public class Cuenta {
    
    private String id_cuenta;
    private String nombre_cuenta;
    private String tipo_cuenta;

    public Cuenta() {
    }

    public String getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(String id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getNombre_cuenta() {
        return nombre_cuenta;
    }

    public void setNombre_cuenta(String nombre_cuenta) {
        this.nombre_cuenta = nombre_cuenta;
    }

    public String getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    @Override
    public String toString() {
        return id_cuenta+" "+nombre_cuenta;
    }
    
    
}
