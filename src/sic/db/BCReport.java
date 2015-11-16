/*
 * Clase encargada de realizar los cálculos para la generación del 
 * Balance de Comprobación
 */
package sic.db;

import java.net.URL;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Oscar
 */
public class BCReport {

    private MayorDB mdb;
    private Date fecha_inicio;
    private Date fecha_fin;

    public BCReport() {

        mdb = new MayorDB();

    }

    public void generarReporte() {

        Map parametros = new HashMap();
        List<BC> bc_datos = null;
        //Agregar activos
        List<BC> activos = mdb.getSaldoCuentasActivo(fecha_inicio, fecha_fin);
        if (activos != null) {
            for (int i = 0; i < activos.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = activos.get(i).getDebe();
                Double haber = activos.get(i).getHaber();
                if (debe >= haber) {
                    debe -= haber;
                    haber = 0.0;
                } else {
                    haber -= debe;
                    debe = 0.0;
                }
                activos.get(i).setDebe(debe);
                activos.get(i).setHaber(haber);
            }
            bc_datos = activos;
        }

        //Agregar pasivos
        List<BC> pasivos = mdb.getSaldoCuentasPasivo(fecha_inicio, fecha_fin);
        if (pasivos != null) {
            for (int i = 0; i < pasivos.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = pasivos.get(i).getDebe();
                Double haber = pasivos.get(i).getHaber();
                if (haber >= debe) {
                    haber -= debe;
                    debe = 0.0;
                } else {
                    debe -= haber;
                    haber = 0.0;
                }
                pasivos.get(i).setDebe(debe);
                pasivos.get(i).setHaber(haber);
            }
            if (bc_datos != null) {
                bc_datos.addAll(pasivos);
            } else {
                bc_datos = pasivos;
            }
        }

        //Agregar patrimonio
        List<BC> patrimonio = mdb.getSaldoCuentasCapital(fecha_inicio, fecha_fin);
        if (patrimonio != null) {
            for (int i = 0; i < patrimonio.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = patrimonio.get(i).getDebe();
                Double haber = patrimonio.get(i).getHaber();
                if (haber >= debe) {
                    haber -= debe;
                    debe = 0.0;
                } else {
                    debe -= haber;
                    haber = 0.0;
                }
                patrimonio.get(i).setDebe(debe);
                patrimonio.get(i).setHaber(haber);
            }
            if (bc_datos != null) {
                bc_datos.addAll(patrimonio);
            } else {
                bc_datos = patrimonio;
            }
        }

        //Agregar cuentas de resultado deudor
        List<BC> res_deudor = mdb.getSaldoCuentasResDeudor(fecha_inicio, fecha_fin);
        if (res_deudor != null) {
            for (int i = 0; i < res_deudor.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = res_deudor.get(i).getDebe();
                Double haber = res_deudor.get(i).getHaber();
                if (debe >= haber) {
                    debe -= haber;
                    haber = 0.0;
                } else {
                    haber -= debe;
                    debe = 0.0;
                }
                res_deudor.get(i).setDebe(debe);
                res_deudor.get(i).setHaber(haber);
            }
            if (bc_datos != null) {
                bc_datos.addAll(res_deudor);
            } else {
                bc_datos = res_deudor;
            }
        }

        //Agregar cuentas de resultado acreedor
        List<BC> res_acreedor = mdb.getSaldoCuentasResAcreedor(fecha_inicio, fecha_fin);
        if (res_acreedor != null) {
            for (int i = 0; i < res_acreedor.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = res_acreedor.get(i).getDebe();
                Double haber = res_acreedor.get(i).getHaber();
                if (haber >= debe) {
                    haber -= debe;
                    debe = 0.0;
                } else {
                    debe -= haber;
                    haber = 0.0;
                }
                res_acreedor.get(i).setDebe(debe);
                res_acreedor.get(i).setHaber(haber);
            }
            if (bc_datos != null) {
                bc_datos.addAll(res_acreedor);
            } else {
                bc_datos = res_acreedor;
            }
        }
        Double saldo_debe = 0.0;
        Double saldo_haber = 0.0;
        for (BC bc : bc_datos) {
            saldo_debe += bc.getDebe();
            saldo_haber += bc.getHaber();
        }
        parametros.put("saldo_debe", saldo_debe);
        parametros.put("saldo_haber", saldo_haber);
        if (bc_datos != null | !bc_datos.isEmpty()) {
            //parametros para el reporte
            String inicio = String.valueOf(fecha_inicio.getDate()) + "-"
                    + String.valueOf(fecha_inicio.getMonth() + 1) + "-"
                    + String.valueOf(fecha_inicio.getYear() + 1900);
            String fin = String.valueOf(fecha_fin.getDate()) + "-"
                    + String.valueOf(fecha_fin.getMonth() + 1) + "-"
                    + String.valueOf(fecha_fin.getYear() + 1900);
            parametros.put("inicio", inicio);
            parametros.put("fin", fin);
            parametros.put("datos", bc_datos);
            try {
                URL url_reporte = getClass().getResource("/sic/reporte/balanceComprobacion.jasper");
                JasperReport reporte = (JasperReport) JRLoader.loadObject(url_reporte);
                JasperPrint printReport = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource(1));
                JasperViewer visor = new JasperViewer(printReport, false);
                visor.setVisible(true);
                visor.setTitle("Balance de Comprobación");

            } catch (JRException ex) {
                Logger.getLogger(BCReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No hay datos para el balance de comprobación!");
        }

    }

    public void setPeriodo(Date inicio, Date fin) {
        this.fecha_inicio = inicio;
        this.fecha_fin = fin;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }
}
