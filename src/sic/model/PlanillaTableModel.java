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
public class PlanillaTableModel extends AbstractTableModel {

    private List<Planilla> planilla = new ArrayList<>();

    @Override
    public int getRowCount() {
        return planilla.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        Planilla plan = planilla.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = plan.getNombre_empleado();
                break;
            case 1:
                value = plan.getSalario_nominal();
                break;
            case 2:
                value = plan.getIsss();
                break;
            case 3:
                value = plan.getAfp();
                break;
            case 4:
                value = plan.getVacacion();
                break;
            case 5:
                value = plan.getAguinaldo();
                break;
            case 6:
                value = plan.getSalario_real();
                break;
        }
        return value;
    }
    
    public TableColumnModel getColumnModel(){
        TableColumnModel modelo = new DefaultTableColumnModel();
        for (int i = 0; i < 7; i++) {
            TableColumn col = new TableColumn(i);
            switch(i){
                case 0:{
                    col.setHeaderValue("Nombre");
                    col.setMinWidth(200);
                    break;}
                case 1:
                    col.setHeaderValue("Salario Nominal");
                    break;
                case 2:
                    col.setHeaderValue("ISSS");
                    break;
                case 3:
                    col.setHeaderValue("AFP");
                    break;
                case 4:
                    col.setHeaderValue("Vacación");
                    break;
                case 5:
                    col.setHeaderValue("Aguinaldo");
                    break;
                case 6:
                    col.setHeaderValue("Salario Real");
                    break;
                
            }
            modelo.addColumn(col);
        }
        return modelo;
    }

    public List<Planilla> getPlanilla() {
        return planilla;
    }

    public void setPlanilla(List<Planilla> planilla) {
        this.planilla = planilla;
    }
}
