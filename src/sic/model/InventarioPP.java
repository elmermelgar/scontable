/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

/**
 *
 * @author Oscar
 */
public class InventarioPP {
    private Integer id_ipp;
    private String producto_p;
    private Integer cantidad;
    private Double monto;

    public Integer getId_ipp() {
        return id_ipp;
    }

    public void setId_ipp(Integer id_ipp) {
        this.id_ipp = id_ipp;
    }

    public String getProducto_p() {
        return producto_p;
    }

    public void setProducto_p(String producto_p) {
        this.producto_p = producto_p;
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
