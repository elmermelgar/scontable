/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ERReport {

    private MayorDB mdb;
    private Date fecha_inicio;
    private Date fecha_fin;

    public ERReport() {
        this.mdb = new MayorDB();
    }

    public void generarReporte() {
        Map parametros = new HashMap();
        URL url_reporte = getClass().getResource("/sic/reporte/estadoResultado.jasper");
        List<ER> rubros = new ArrayList<>();
        DecimalFormat precision2 = new DecimalFormat("0.00");
        Double monto_total = 0.0;
        

        //INGRESO POR VENTAS
        ER ventas = mdb.getRubro("511");
        if (ventas != null) {
            if (ventas.getSaldo() < 0) {
                monto_total -= ventas.getSaldo();
            } else if (ventas.getSaldo() > 0) {
                monto_total += ventas.getSaldo();
            }
            rubros.add(ventas);
        }

        //COSTO DE LO VENDIDO
        ER costo_vendido = mdb.getRubro("521");
        if (costo_vendido != null) {
            if (costo_vendido.getSaldo() < 0) {
                monto_total += costo_vendido.getSaldo();
            } else if (costo_vendido.getSaldo() > 0) {
                monto_total -= costo_vendido.getSaldo();
            }
            rubros.add(costo_vendido);
        }

        //CALCULO DE LA UTILIDAD BRUTA
        ER util_bruta = new ER();
        if (monto_total > 0) {
            util_bruta.setRubro("(=) Utilidad Bruta");
            util_bruta.setSaldo(monto_total);
            rubros.add(util_bruta);
        } else {
        }

        //CALCULO DE LOS COSTOS DE ADMINISTRACION
        ER costos_admin = mdb.getRubro("411");
        if (costos_admin != null) {
            if (costos_admin.getSaldo() > 0) {
                monto_total -= costos_admin.getSaldo();
                rubros.add(costos_admin);
            }
        }
        costos_admin = mdb.getRubro("4117");
        if (costos_admin != null) {
            if (costos_admin.getSaldo() > 0) {
                monto_total -= costos_admin.getSaldo();
                rubros.add(costos_admin);
            }
        }


        //COSTOS DE LOGISTICA

        //COSTOS DE COMERCIALIZACION

        //CALCULO DE LA UTILIDAD OPERATIVA
        ER util_operat = new ER();
        if (monto_total > 0) {
            util_operat.setRubro("(=) Utilidad Operativa");
            util_operat.setSaldo(monto_total);
            rubros.add(util_operat);
        } else {
        }

        //COSTOS FINACIEROS
        ER costos_financ = mdb.getRubro("412");
        if (costos_financ != null) {
            if (costos_financ.getSaldo() > 0) {
                monto_total -= costos_financ.getSaldo();
                rubros.add(costos_financ);
            }
        }

        //UTILIDAD ANTES DE IMPUESTOS Y RESERVA LEGAL
        ER util_antes_res = new ER();
        if (monto_total > 0) {
            util_antes_res.setRubro("(=) Utilidad antes de impuestos y reserva legal");
            util_antes_res.setSaldo(monto_total);
            rubros.add(util_antes_res);
        } else {
        }

        //RESERVA LEGAL
        ER reserva = new ER();
        if (monto_total > 0) {
            reserva.setRubro("(-) Reserva Legal (7%)");
            reserva.setSaldo(monto_total * 0.07);
            monto_total -= monto_total * 0.07;
            rubros.add(reserva);
        } else {
        }

        //UTILIDAD ANTES DE IMPUESTOS
        ER util_antes_imp = new ER();
        if (monto_total > 0) {
            util_antes_imp.setRubro("(=) Utilidad antes de impuestos");
            util_antes_imp.setSaldo(monto_total);
            rubros.add(util_antes_imp);
        } else {
        }

        //IMPUESTO SOBRE LA RENTA
        ER renta = new ER();
        if (monto_total > 0) {
            renta.setRubro("(-) Impuesto sobre la Renta (25%)");
            renta.setSaldo(monto_total * 0.25);
            monto_total -= monto_total * 0.25;
            rubros.add(renta);
        } else {
        }

        //UTILIDAD NETA
        ER util_neta = new ER();
        if (monto_total > 0) {
            util_neta.setRubro("(=) Utilidad neta");
            util_neta.setSaldo(monto_total);
            rubros.add(util_neta);
        } else {
        }

        if (!rubros.isEmpty()) {
            //parametros para el reporte
            String inicio = String.valueOf(fecha_inicio.getDate()) + "-"
                    + String.valueOf(fecha_inicio.getMonth() + 1) + "-"
                    + String.valueOf(fecha_inicio.getYear() + 1900);
            String fin = String.valueOf(fecha_fin.getDate()) + "-"
                    + String.valueOf(fecha_fin.getMonth() + 1) + "-"
                    + String.valueOf(fecha_fin.getYear() + 1900);
            parametros.put("inicio", inicio);
            parametros.put("fin", fin);

            try {
                //importar las librerias de jasperReport 5.6.0
                JasperReport reporte = (JasperReport) JRLoader.loadObject(url_reporte);
                JRBeanCollectionDataSource datos = new JRBeanCollectionDataSource(rubros);
                JasperPrint printReport = JasperFillManager.fillReport(reporte, parametros, datos);
                JasperViewer visor = new JasperViewer(printReport, false);
                visor.setVisible(true);
                visor.setTitle("Estado de Resultados");

            } catch (JRException ex) {
                Logger.getLogger(BCReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No hay datos para el Estado de Resultados!");
        }

    }

    public void setPeriodo(Date inicio, Date fin) {
        this.fecha_inicio = inicio;
        this.fecha_fin = fin;
    }
}
