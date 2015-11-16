/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

import java.sql.Date;
import sic.model.Departamento;
import sic.model.Empleado;
import sic.model.Transaccion;

/**
 *
 * @author Oscar
 */
public class DepartamentoDB {

    public Departamento getElementosCosto(Date start, Date end) {
        Departamento dpto = new Departamento();
        MayorDB mdb = new MayorDB();
        //Invetario de Producto en Proceso
        Transaccion cta = mdb.getCuenta("1142", start, end);
        double monto = 0.0;
        if (cta.getDebe() > 0.0) {
            monto = cta.getDebe();
        } else if (cta.getHaber() > 0.0) {
            monto = cta.getHaber();
        }
        dpto.setMp_monto(monto);
        dpto.setMp_cantidad(100);

        //Mano de Obra Directa
        EmpleadoDB edb = new EmpleadoDB();
        Empleado emp = edb.getElementoCosto();
        dpto.setMod_cantidad(emp.getNumero_empleados());
        dpto.setMod_monto(emp.getMonto_total_planilla());

        //CIF - 60% de la M.O.D.
        dpto.setCif_cantidad(0);
        double monto_cif = emp.getMonto_total_planilla() * 0.60;
        dpto.setCif_monto(monto_cif);

        return dpto;
    }

    public Departamento getElementosCosto() {
        Departamento dpto = new Departamento();
        MayorDB mdb = new MayorDB();
        //Invetario de Producto en Proceso
        Transaccion cta = mdb.getCuenta("1142");
        double monto = 0.0;
        if (cta != null) {
            if (cta.getDebe() > 0.0) {
                monto = cta.getDebe();
            } else if (cta.getHaber() > 0.0) {
                monto = cta.getHaber();
            }
            dpto.setMp_monto(monto);
            dpto.setMp_cantidad(100);
        }

        //Mano de Obra Directa
        EmpleadoDB edb = new EmpleadoDB();
        Empleado emp = edb.getElementoCosto();
        dpto.setMod_cantidad(emp.getNumero_empleados());
        dpto.setMod_monto(emp.getMonto_total_planilla());

        //CIF - 60% de la M.O.D.
        dpto.setCif_cantidad(0);
        double monto_cif = emp.getMonto_total_planilla() * 0.60;
        dpto.setCif_monto(monto_cif);

        return dpto;
    }
}
