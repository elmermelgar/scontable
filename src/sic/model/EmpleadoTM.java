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
public class EmpleadoTM extends AbstractTableModel{

    private List<Empleado> empleados = new ArrayList<>();
    private TableColumnModel colModel = new DefaultTableColumnModel();
    
    @Override
    public int getRowCount() {
        return this.empleados.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Empleado empleado = this.empleados.get(rowIndex);
        Object value = null;
        switch(columnIndex){
            case 0:
                value = empleado.getNombre();
                break;
            case 1:
                value = empleado.getApellido();
                break;
            case 2:
                value = empleado.getSalario();
        }
        return value;
    }
    
    public TableColumnModel getColumnModel(){
        while(colModel.getColumns().hasMoreElements()){
            colModel.removeColumn(colModel.getColumns().nextElement());
        }
        for (int i = 0; i < 3; i++) {
            TableColumn col = new TableColumn(i);
            switch(i){
                case 0:
                    col.setHeaderValue("Nombre");
                    break;
                case 1:
                    col.setHeaderValue("Apellido");
                    break;
                case 2:
                    col.setHeaderValue("Salario");
                    col.setMaxWidth(200);
            }
            colModel.addColumn(col);
        }
        return colModel;
    }

    public List<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
}
