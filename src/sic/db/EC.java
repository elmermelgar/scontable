/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

/**
 *
 * @author Oscar
 */
public class EC {
    private String rubro;
    private Double saldo;

    public EC() {
    }

    public EC(String rubro, Double saldo) {
        this.rubro = rubro;
        this.saldo = saldo;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
}
