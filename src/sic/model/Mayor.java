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
public class Mayor {
    
    private Integer id_mayor;
    private Cuenta cuenta;
    private Double saldo_cuenta;
    private Date fecha;

    public Mayor() {
    }

    public Mayor(Integer id_mayor, Cuenta cuenta, Double saldo_cuenta, Date fecha) {
        this.id_mayor = id_mayor;
        this.cuenta = cuenta;
        this.saldo_cuenta = saldo_cuenta;
        this.fecha = fecha;
    }

    public Integer getId_mayor() {
        return id_mayor;
    }

    public void setId_mayor(Integer id_mayor) {
        this.id_mayor = id_mayor;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Double getSaldo_cuenta() {
        return saldo_cuenta;
    }

    public void setSaldo_cuenta(Double saldo_cuenta) {
        this.saldo_cuenta = saldo_cuenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha_inicio(Date fecha) {
        this.fecha = fecha;
    }
    
}
