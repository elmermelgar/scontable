/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

/**
 *
 * @author Oscar
 */
public class InventarioMP {
    private Integer id_imp;
    private String material;
    private Integer cantidad;
    private Double monto;

    public Integer getId_imp() {
        return id_imp;
    }

    public void setId_imp(Integer id_imp) {
        this.id_imp = id_imp;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
