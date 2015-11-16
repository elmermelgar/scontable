/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
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
public class ECReport {

    private MayorDB mdb;
    private Date fecha_inicio;
    private Date fecha_fin;

    public ECReport() {
        this.mdb = new MayorDB();
    }

    public void generarReporte() {

        Map parametros = new HashMap();
        List<EC> inversion = new ArrayList<>();
        Double total_inv = 0.0;

//        INVERSIONES
        EC aportaciones = mdb.getRubroEC("311");
        if (aportaciones != null) {
            if (aportaciones.getSaldo() > 0.0) {
                total_inv += aportaciones.getSaldo();
                inversion.add(aportaciones);
            }
        }

        EC reserva = mdb.getRubroEC("3121");
        if (reserva != null) {
            if (reserva.getSaldo() > 0.0) {
                total_inv += reserva.getSaldo();
                inversion.add(reserva);
            }
        }

        EC superavit = mdb.getRubroEC("313");
        if (superavit != null) {
            if (superavit.getSaldo() > 0.0) {
                total_inv += superavit.getSaldo();
                inversion.add(superavit);
            }
        }

        EC util_no_distrib = mdb.getRubroEC("3212");
        if (util_no_distrib != null) {
            if (util_no_distrib.getSaldo() > 0.0) {
                total_inv += util_no_distrib.getSaldo();
                inversion.add(util_no_distrib);
            }
        }
        parametros.put("inversion", inversion);
        parametros.put("total_inversion", total_inv);

//        DESINVERSIONES
        List<EC> desinversion = new ArrayList<>();
        Double total_desinv = 0.0;

        EC perdida_acum = mdb.getRubroEC("3221");
        if (perdida_acum != null) {
            if (perdida_acum.getSaldo() > 0.0) {
                total_desinv += perdida_acum.getSaldo();
                desinversion.add(perdida_acum);
            }
        }

        EC perdida_ejerc = mdb.getRubroEC("3312");
        if (perdida_ejerc != null) {
            if (perdida_ejerc.getSaldo() > 0.0) {
                total_desinv += perdida_ejerc.getSaldo();
                desinversion.add(perdida_ejerc);
            }
        }

        EC retiros_personales = mdb.getRubroEC("341");
        if (retiros_personales != null) {
            if (retiros_personales.getSaldo() > 0.0) {
                total_desinv += retiros_personales.getSaldo();
                desinversion.add(retiros_personales);
            }
        }
        parametros.put("desinversion", desinversion);
        parametros.put("total_desinversion", total_desinv);
        parametros.put("capital_social", total_inv - total_desinv);

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
            URL url_reporte = getClass().getResource("/sic/reporte/estadoCapital.jasper");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(url_reporte);
            JasperPrint printReport = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource(1));
            JasperViewer visor = new JasperViewer(printReport, false);
            visor.setVisible(true);
            visor.setTitle("Estado de Capital");

        } catch (JRException ex) {
            Logger.getLogger(BCReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setPeriodo(Date inicio, Date fin) {
        this.fecha_inicio = inicio;
        this.fecha_fin = fin;
    }
}
