/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

/**
 *
 * @author Oscar
 */
public class InventarioPT {
    private Integer id_ipt;
    private String producto_t;
    private Integer cantidad;
    private Double monto;

    public Integer getId_ipt() {
        return id_ipt;
    }

    public void setId_ipt(Integer id_ipt) {
        this.id_ipt = id_ipt;
    }

    public String getProducto_t() {
        return producto_t;
    }

    public void setProducto_t(String producto_t) {
        this.producto_t = producto_t;
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
