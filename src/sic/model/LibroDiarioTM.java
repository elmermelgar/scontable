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
public class LibroDiarioTM extends AbstractTableModel {

    private List<Transaccion> libro_diario = new ArrayList<>();
    private TableColumnModel columnModel = new DefaultTableColumnModel();

    @Override
    public int getRowCount() {
        return libro_diario.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object obj = null;
        Transaccion tran = libro_diario.get(rowIndex);
        switch (columnIndex) {
            case 0:
                obj = tran.getPartida().getNum_partida();
                break;
            case 1:
                obj = tran.getCuenta().getNombre_cuenta();
                break;
            case 2:
                obj = tran.getFecha_tran();
                break;
            case 3:
                obj = tran.getDebe();
                break;
            case 4:
                obj = tran.getHaber();
        }
        return obj;
    }

    public Transaccion getTransaccion(int rowIndex) {

        return libro_diario.get(rowIndex);

    }

    public List<Transaccion> getLibro_diario() {
        return libro_diario;
    }

    public void setLibro_diario(List<Transaccion> libro_diario) {
        this.libro_diario = libro_diario;
    }
    
    public TableColumnModel getColumnModel() {

        //Limpiar el modelo de columnas
        while (columnModel.getColumns().hasMoreElements()) {
            columnModel.removeColumn(columnModel.getColumns().nextElement());
        }

        for (int i = 0; i < 5; i++) {
            TableColumn col = new TableColumn(i);
            switch (i) {
                case 0:
                    col.setHeaderValue("Número Partida");
                    break;
                case 1:
                    col.setHeaderValue("Cuenta");
                    col.setMinWidth(300);
                    break;
                case 2:
                    col.setHeaderValue("Fecha");
                    break;
                case 3:
                    col.setHeaderValue("Debe");
                    break;
                case 4:
                    col.setHeaderValue("Haber");
            }
            columnModel.addColumn(col);
        }

        return columnModel;
    }
}
