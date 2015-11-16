/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Oscar
 */
public class CatalogoCuentasTM extends AbstractTableModel {

    private List<Cuenta> catalogo_cuentas = new ArrayList<>();
    private TableColumnModel columnModel = new DefaultTableColumnModel();

    @Override
    public int getRowCount() {
        return catalogo_cuentas.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Cuenta cuenta = catalogo_cuentas.get(rowIndex);
        Object obj = null;
        switch (columnIndex) {
            case 0:
                obj = cuenta.getId_cuenta();
                break;
            case 1:
                obj = cuenta.getNombre_cuenta();
                break;
            case 2:
                obj = cuenta.getTipo_cuenta();
        }
        return obj;
    }

    public Cuenta getCuenta(int rowIndex) {

        return catalogo_cuentas.get(rowIndex);

    }

    public List<Cuenta> getCatalogo_cuentas() {
        return catalogo_cuentas;
    }

    public void setCatalogo_cuentas(List<Cuenta> catalogo_cuentas) {
        this.catalogo_cuentas = catalogo_cuentas;
    }

    public TableColumnModel getColumnModel() {

        while (columnModel.getColumns().hasMoreElements()) {
            columnModel.removeColumn(columnModel.getColumns().nextElement());
        }
        for (int i = 0; i < 3; i++) {
            TableColumn col = new TableColumn(i);
            switch (i) {
                case 0:
                    col.setHeaderValue("Código");
                    col.setMaxWidth(100);
                    break;
                case 1:
                    col.setHeaderValue("Nombre");
                    break;
                case 2:
                    col.setHeaderValue("Tipo");
            }
            columnModel.addColumn(col);
        }

        return columnModel;
    }
}
