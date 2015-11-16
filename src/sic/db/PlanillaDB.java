/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

import java.util.ArrayList;
import java.util.List;
import sic.model.Empleado;
import sic.model.Planilla;

/**
 *
 * @author Oscar
 */
public class PlanillaDB {
    public static final double ISSS = 0.075;
    public static final double AFP = 0.065;
    public static final double VAC = 1.3;
    private List<Planilla> planilla = new ArrayList<>();
    
    

    public List<Planilla> getPlanilla() {
        this.planilla.clear();
        for (Empleado emp : new EmpleadoDB().getEmpleados()) {
            Planilla plan = new Planilla();
            plan.setNombre_empleado(emp.getNombre()+" "+emp.getApellido());
            double sal = emp.getSalario();
            plan.setSalario_nominal(sal);
            double monto_isss = sal*ISSS;
            plan.setIsss(monto_isss);
            double monto_afp = sal*AFP;
            plan.setAfp(monto_afp);
//            double vacaciones = (sal/2)*VAC + (sal/2)*(ISSS + AFP); 
            double vacaciones = 0.0;
            plan.setVacacion(vacaciones);
            double aguinaldo = 0.0;
            plan.setAguinaldo(aguinaldo);
            double bono = 0.0;
            plan.setBono(bono);
            double descuentos = 0.0;
            plan.setDescuento(descuentos);
            double sal_real = sal - monto_isss - monto_afp + vacaciones + aguinaldo + bono - descuentos;
            plan.setSalario_real(sal_real);
            this.planilla.add(plan);
        }
        
        return this.planilla;
    }
    
    public void setPlanilla(List<Planilla> planilla) {
        this.planilla = planilla;
    }
    
}
