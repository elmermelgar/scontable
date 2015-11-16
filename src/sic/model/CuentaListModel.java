/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Oscar
 */
public class CuentaListModel implements ListModel<Cuenta>{

    private List<Cuenta> cuentas = new ArrayList<>();
    
    @Override
    public int getSize() {
        
        return cuentas.size();
        
    }

    @Override
    public Cuenta getElementAt(int index) {
        
        return cuentas.get(index);
        
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
    
    public String getNombreCuenta(int index){
        
        return cuentas.get(index).getNombre_cuenta();
        
    }
    
    public int getIndexOF(Cuenta c){
        
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getId_cuenta().equals(c.getId_cuenta())) {
                return cuentas.indexOf(cuenta);
            }
        }
        return -1;
    }
    
}
