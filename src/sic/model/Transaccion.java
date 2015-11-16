/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

import java.sql.Date;

/**
 *
 * @author Oscar
 */
public class Transaccion {
    
    private Integer id_transaccion;
    private Date fecha_tran;
    private Double debe;
    private Double haber;
    private Partida partida;
    private Cuenta cuenta;

    public Transaccion() {
    }

    public Transaccion(Date fecha_tran, Double debe,Double haber, Cuenta cuenta, Partida partida) {
        this.fecha_tran = fecha_tran;
        this.debe = debe;
        this.haber = haber;
        this.cuenta = cuenta;
        this.partida = partida;
    }

    public Integer getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(Integer id_transaccion) {
        this.id_transaccion = id_transaccion;
    }

    public Date getFecha_tran() {
        return fecha_tran;
    }

    public void setFecha_tran(Date fecha_tran) {
        this.fecha_tran = fecha_tran;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
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
