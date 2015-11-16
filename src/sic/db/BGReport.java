/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class BGReport {

    private MayorDB mdb;
    private Date fecha_inicio;
    private Date fecha_fin;

    public BGReport() {
        mdb = new MayorDB();
    }

    public void generarReporte() {

        Map parametros = new HashMap();

        //ACTIVOS
        List<BG> activos = mdb.getActivosBG(fecha_inicio, fecha_fin);
        Double total_activos = 0.0;
        for (BG bg : activos) {
            total_activos += bg.getSaldo();
        }
        parametros.put("activos", activos);
        parametros.put("total_activos", total_activos);

        //PASIVOS
        List<BG> pasivos = mdb.getPasivosBG(fecha_inicio, fecha_fin);
         Double total_pasivos = 0.0;
        for (BG bg : pasivos) {
            total_pasivos += bg.getSaldo();
        }
        parametros.put("pasivos", pasivos);
        parametros.put("total_pasivos", total_pasivos);

        //PATRIMONIO
        List<BG> patrimonio = mdb.getPatrimonioBG(fecha_inicio, fecha_fin);
        Double total_patrimonio = 0.0;
        for (BG bg : patrimonio) {
            total_patrimonio += bg.getSaldo();
        }
        parametros.put("patrimonio", patrimonio);
        parametros.put("total_patrimonio", total_patrimonio);
        
        parametros.put("total_pas_pat", total_pasivos + total_patrimonio);

        URL url_reporte = getClass().getResource("/sic/reporte/balanceGeneral.jasper");
        
        String inicio = String.valueOf(fecha_inicio.getDate())+"-"
                    +String.valueOf(fecha_inicio.getMonth()+1)+"-"
                    +String.valueOf(fecha_inicio.getYear()+1900);
            String fin = String.valueOf(fecha_fin.getDate())+"-"
                    +String.valueOf(fecha_fin.getMonth()+1)+"-"
                    +String.valueOf(fecha_fin.getYear()+1900);
        parametros.put("inicio", inicio);
        parametros.put("fin", fin);

        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject(url_reporte);
            JasperPrint printReport = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource());
            JasperViewer visor = new JasperViewer(printReport, false);
            visor.setVisible(true);
            visor.setTitle("Balance General");

        } catch (JRException ex) {
            Logger.getLogger(BCReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPeriodo(Date inicio, Date fin) {
        this.fecha_inicio = inicio;
        this.fecha_fin = fin;
    }
}
