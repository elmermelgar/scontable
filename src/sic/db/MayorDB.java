/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.model.Transaccion;

/**
 *
 * @author Oscar
 */
public class MayorDB {

    public boolean guardar(String id_cuenta, Double saldo_cuenta,Date fechaI,Date fechaF,Date fecha) {
        boolean guardado = true;
        try {
            Integer id_mayor = Integer.valueOf(getNextIdMayor());
            String sql = "INSERT INTO mayor(id_mayor, id_cuenta, saldo_cuenta, fecha_inicio, fecha_fin, fecha )VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_mayor);
            pst.setString(2, id_cuenta);
            pst.setDouble(3, saldo_cuenta);
            pst.setDate(4, fechaI);
            pst.setDate(5, fechaF);
            pst.setDate(6, fecha);
            pst.execute();
        } catch (SQLException e) {
            guardado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return guardado;
    }

    public boolean actualizar(Integer id_mayor, String id_cuenta, Double saldo_cuenta, Date fecha) {
        boolean actualizado = true;
        try {
            String sql = "UPDATE mayor SET id_cuenta=?, saldo_cuenta=?, fecha=? WHERE id_mayor=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setString(1, id_cuenta);
            pst.setDouble(2, saldo_cuenta);
            pst.setDate(3, fecha);
            pst.setInt(4, id_mayor);
            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return actualizado;
    }
    
    public boolean resetMayor(){
        boolean reseteado = true;
        String query = "DELETE FROM mayor";
        try {
            Statement st = Conexion.getConexion().createStatement();
            st.executeQuery(query);
        } catch (SQLException ex) {
            reseteado = false;
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reseteado;
    }
    
    public List<Transaccion> getTransacciones(Date inicio_periodo, Date fin_periodo) {

        List<Transaccion> transacciones = new ArrayList<>();

        try {
            String query = "SELECT transaccion.debe, transaccion.haber, transaccion.fecha_tran, "
                    + "transaccion.id_partida, transaccion.id_cuenta, transaccion.id_transaccion "
                    + "FROM public.cuenta, public.transaccion WHERE "
                    + "cuenta.id_cuenta = transaccion.id_cuenta AND "
                    + "transaccion.fecha_tran >=? AND transaccion.fecha_tran <=?;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio_periodo);
            pst.setDate(2, fin_periodo);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Transaccion tran = new Transaccion();
                tran.setId_transaccion(res.getInt("id_transaccion"));
                tran.setCuenta((new CuentaDB()).getCuenta(res.getString("id_cuenta")));
                tran.setPartida((new PartidaDB()).getPartida(res.getInt("id_partida")));
                tran.setFecha_tran(res.getDate("fecha_tran"));
                tran.setDebe(res.getDouble("debe"));
                tran.setHaber(res.getDouble("haber"));
                transacciones.add(tran);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return transacciones;

    }
    
    public Transaccion getCuenta(String id_cuenta, Date inicio_periodo, Date fin_periodo) {

        Transaccion tran = null;
        try {
            String query = "SELECT mayor.id_mayor, mayor.id_cuenta, mayor.saldo_cuenta, mayor.fecha"
                    + " FROM public.cuenta, public.mayor WHERE mayor.id_cuenta = ?"
                    + " AND mayor.fecha >=? AND mayor.fecha <=?;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setString(1, id_cuenta);
            pst.setDate(2, inicio_periodo);
            pst.setDate(3, fin_periodo);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                tran = new Transaccion();
                tran.setId_transaccion(res.getInt("id_transaccion"));
                tran.setCuenta((new CuentaDB()).getCuenta(res.getString("id_cuenta")));
                tran.setPartida((new PartidaDB()).getPartida(res.getInt("id_partida")));
                tran.setFecha_tran(res.getDate("fecha_tran"));
                tran.setDebe(res.getDouble("debe"));
                tran.setHaber(res.getDouble("haber"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tran;
    }
    
    public Transaccion getCuenta(String id_cuenta) {

        Transaccion tran = null;
        try {
            String query = "SELECT mayor.id_mayor, mayor.id_cuenta, mayor.saldo_cuenta, mayor.fecha"
                    + " FROM public.cuenta, public.mayor WHERE mayor.id_cuenta = ?;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setString(1, id_cuenta);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                tran = new Transaccion();
                tran.setId_transaccion(res.getInt("id_transaccion"));
                tran.setCuenta((new CuentaDB()).getCuenta(res.getString("id_cuenta")));
                tran.setPartida((new PartidaDB()).getPartida(res.getInt("id_partida")));
                tran.setFecha_tran(res.getDate("fecha_tran"));
                tran.setDebe(res.getDouble("debe"));
                tran.setHaber(res.getDouble("haber"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tran;
    }

    public List<BC> getSaldoCuentasActivo(Date inicio_periodo, Date fin_periodo) {

        List<BC> cuentas = new ArrayList<>();

        try {
            String query = "SELECT transaccion.id_cuenta, cuenta.nombre_cuenta, "
                    + "SUM(transaccion.debe) saldo_debe, SUM(transaccion.haber) saldo_haber "
                    + "FROM public.transaccion, public.cuenta "
                    + "WHERE cuenta.id_cuenta = transaccion.id_cuenta AND "
                    + "transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '1%' "
                    + "GROUP BY transaccion.id_cuenta, cuenta.nombre_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio_periodo);
            pst.setDate(2, fin_periodo);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BC cuenta = new BC();
                cuenta.setCodigo(res.getString("id_cuenta"));
                cuenta.setNombre(res.getString("nombre_cuenta"));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cuentas;

    }

    public List<BC> getSaldoCuentasPasivo(Date inicio_periodo, Date fin_periodo) {

        List<BC> cuentas = new ArrayList<>();

        try {
            String query = "SELECT transaccion.id_cuenta, cuenta.nombre_cuenta, "
                    + "SUM(transaccion.debe) saldo_debe, SUM(transaccion.haber) saldo_haber "
                    + "FROM public.transaccion, public.cuenta "
                    + "WHERE cuenta.id_cuenta = transaccion.id_cuenta AND "
                    + "transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '2%' "
                    + "GROUP BY transaccion.id_cuenta, cuenta.nombre_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio_periodo);
            pst.setDate(2, fin_periodo);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BC cuenta = new BC();
                cuenta.setCodigo(res.getString("id_cuenta"));
                cuenta.setNombre(res.getString("nombre_cuenta"));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cuentas;

    }

    public List<BC> getSaldoCuentasCapital(Date inicio_periodo, Date fin_periodo) {

        List<BC> cuentas = new ArrayList<>();

        try {
            String query = "SELECT transaccion.id_cuenta, cuenta.nombre_cuenta, "
                    + "SUM(transaccion.debe) saldo_debe, SUM(transaccion.haber) saldo_haber "
                    + "FROM public.transaccion, public.cuenta "
                    + "WHERE cuenta.id_cuenta = transaccion.id_cuenta AND "
                    + "transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '3%' "
                    + "GROUP BY transaccion.id_cuenta, cuenta.nombre_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio_periodo);
            pst.setDate(2, fin_periodo);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BC cuenta = new BC();
                cuenta.setCodigo(res.getString("id_cuenta"));
                cuenta.setNombre(res.getString("nombre_cuenta"));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cuentas;

    }

    public List<BC> getSaldoCuentasResDeudor(Date inicio_periodo, Date fin_periodo) {

        List<BC> cuentas = new ArrayList<>();

        try {
            String query = "SELECT transaccion.id_cuenta, cuenta.nombre_cuenta, "
                    + "SUM(transaccion.debe) saldo_debe, SUM(transaccion.haber) saldo_haber "
                    + "FROM public.transaccion, public.cuenta "
                    + "WHERE cuenta.id_cuenta = transaccion.id_cuenta AND "
                    + "transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '4%' "
                    + "GROUP BY transaccion.id_cuenta, cuenta.nombre_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio_periodo);
            pst.setDate(2, fin_periodo);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BC cuenta = new BC();
                cuenta.setCodigo(res.getString("id_cuenta"));
                cuenta.setNombre(res.getString("nombre_cuenta"));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cuentas;

    }

    public List<BC> getSaldoCuentasResAcreedor(Date inicio_periodo, Date fin_periodo) {

        List<BC> cuentas = new ArrayList<>();

        try {
            String query = "SELECT transaccion.id_cuenta, cuenta.nombre_cuenta, "
                    + "SUM(transaccion.debe) saldo_debe, SUM(transaccion.haber) saldo_haber "
                    + "FROM public.transaccion, public.cuenta "
                    + "WHERE cuenta.id_cuenta = transaccion.id_cuenta AND "
                    + "transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '5%' "
                    + "GROUP BY transaccion.id_cuenta, cuenta.nombre_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio_periodo);
            pst.setDate(2, fin_periodo);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BC cuenta = new BC();
                cuenta.setCodigo(res.getString("id_cuenta"));
                cuenta.setNombre(res.getString("nombre_cuenta"));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cuentas;

    }

    public Double getSaldoDeudor(String id_cuenta_deudora) {

        Double saldo = 0.0;

        try {
            String query = "SELECT SUM(transaccion.debe) saldo_debe, SUM(transaccion.haber) saldo_haber "
                    + "FROM public.cuenta, public.transaccion WHERE "
                    + "cuenta.id_cuenta =? AND transaccion.id_cuenta =?;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setString(1, id_cuenta_deudora);
            pst.setString(2, id_cuenta_deudora);
            ResultSet res = pst.executeQuery();
            res.next();
            saldo = res.getDouble("saldo_debe") - res.getDouble("saldo_haber");
        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return saldo;

    }

    public Double getSaldoAcreedor(String id_cuenta_acreedora) {

        Double saldo = 0.0;

        try {
            String query = "SELECT SUM(transaccion.debe) saldo_debe, SUM(transaccion.haber) saldo_haber "
                    + "FROM public.cuenta, public.transaccion WHERE "
                    + "cuenta.id_cuenta =? AND transaccion.id_cuenta =?;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setString(1, id_cuenta_acreedora);
            pst.setString(2, id_cuenta_acreedora);
            ResultSet res = pst.executeQuery();
            res.next();
            saldo = res.getDouble("saldo_haber") - res.getDouble("saldo_debe");
        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return saldo;

    }

    public void mayorizarCuentas(Date inicio, Date fin) {
        Date fecha_hoy = new Date(new java.util.Date(System.currentTimeMillis()).getTime());
        //mayorizar activos
        System.out.println("estoy en mayorizar cuentas ");
        List<Transaccion> activos = getActivos(inicio, fin);
        if (activos != null) {
            for (int i = 0; i < activos.size(); i++) {
                // Calcular saldo final de cada cuenta
                System.out.println("recorrido de activos");
             System.out.println(Arrays.toString(activos.toArray()));
                Double debe = activos.get(i).getDebe();
                Double haber = activos.get(i).getHaber();
                guardar(activos.get(i).getCuenta().getId_cuenta(), debe - haber,inicio,fin, fecha_hoy);
            }
        }

        //mayorizar pasivos
        List<Transaccion> pasivos = getPasivos(inicio, fin);
        if (pasivos != null) {
            for (int i = 0; i < pasivos.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = pasivos.get(i).getDebe();
                Double haber = pasivos.get(i).getHaber();
                guardar(pasivos.get(i).getCuenta().getId_cuenta(), haber - debe,inicio,fin, fecha_hoy);
            }
        }

        //mayorizar patrimonio
        List<Transaccion> patrimonio = getPatrimonio(inicio, fin);
        if (patrimonio != null) {
            for (int i = 0; i < patrimonio.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = patrimonio.get(i).getDebe();
                Double haber = patrimonio.get(i).getHaber();
                guardar(patrimonio.get(i).getCuenta().getId_cuenta(), haber - debe,inicio,fin, fecha_hoy);
            }
        }

        //mayorizar cuentas de resultado deudor
        List<Transaccion> res_deudor = getResDeudor(inicio, fin);
        if (res_deudor != null) {
            for (int i = 0; i < res_deudor.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = res_deudor.get(i).getDebe();
                Double haber = res_deudor.get(i).getHaber();
                guardar(res_deudor.get(i).getCuenta().getId_cuenta(), debe - haber,inicio,fin, fecha_hoy);
            }
        }

        //mayorizar cuentas de resultado acreedor
        List<Transaccion> res_acreedor = getResAcreedor(inicio, fin);
        if (res_acreedor != null) {
            for (int i = 0; i < res_acreedor.size(); i++) {
                // Calcular saldo final de cada cuenta
                Double debe = res_acreedor.get(i).getDebe();
                Double haber = res_acreedor.get(i).getHaber();
                guardar(res_acreedor.get(i).getCuenta().getId_cuenta(), haber - debe,inicio,fin, fecha_hoy);
            }
        }
    }

    public int getNextIdMayor() {

        int numero = 0;
        try {
            String sql = "SELECT MAX(id_mayor) num FROM mayor";
            Statement st = Conexion.getConexion().createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                numero = resultado.getInt("num") + 1;
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }

        return numero;

    }

    private List<Transaccion> getActivos(Date inicio, Date fin) {
        List<Transaccion> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '1%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
             System.out.println("estoy en get activos \n select de mayorizacion");
//             System.out.println(pst);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            System.out.println(pst);
            ResultSet res = pst.executeQuery();
             System.out.println("resultado del sql");
             System.out.println(res);
            while (res.next()) {
                System.out.println(res.getString("id_cuenta"));
                Transaccion cuenta = new Transaccion();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
              System.out.println(  cuenta);
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }

    private List<Transaccion> getPasivos(Date inicio, Date fin) {
        List<Transaccion> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '2%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Transaccion cuenta = new Transaccion();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }

    private List<Transaccion> getPatrimonio(Date inicio, Date fin) {
        List<Transaccion> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '3%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Transaccion cuenta = new Transaccion();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }

    private List<Transaccion> getResDeudor(Date inicio, Date fin) {
        List<Transaccion> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '4%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Transaccion cuenta = new Transaccion();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }

    private List<Transaccion> getResAcreedor(Date inicio, Date fin) {
        List<Transaccion> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '5%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Transaccion cuenta = new Transaccion();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")));
                cuenta.setDebe(res.getDouble("saldo_debe"));
                cuenta.setHaber(res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }

    //METODO PARA RECUPRERAR DATOS PARA EL ESTADO DE RESULTADO
    public ER getRubro(String id_cuenta) {
        ER rubro = null;
        String query = "SELECT mayor.id_cuenta, mayor.saldo_cuenta FROM "
                + "public.mayor WHERE mayor.id_cuenta = ?;";
        try {
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setString(1, id_cuenta);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                String nombreCuenta = (new CuentaDB()).getCuenta(res.getString("id_cuenta")).getNombre_cuenta();
                rubro = new ER(nombreCuenta, res.getDouble("saldo_cuenta"));
                System.out.println("rubro: "+nombreCuenta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rubro;
    }
    
    //METODO PARA RECUPRERAR DATOS PARA EL ESTADO DE CAPITAL
    public EC getRubroEC(String id_cuenta) {
        EC rubro = null;
        String query = "SELECT mayor.id_cuenta, mayor.saldo_cuenta FROM "
                + "public.mayor WHERE mayor.id_cuenta = ?;";
        try {
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setString(1, id_cuenta);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                String nombreCuenta = (new CuentaDB()).getCuenta(res.getString("id_cuenta")).getNombre_cuenta();
                rubro = new EC(nombreCuenta, res.getDouble("saldo_cuenta"));
                System.out.println("rubro: "+nombreCuenta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rubro;
    }
    
//    MEODO PARA RECUPERAR DATOS PARA EL BALANCE GENERAL
    public List<BG> getActivosBG(Date inicio, Date fin){
        List<BG> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '1%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BG cuenta = new BG();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")).getNombre_cuenta());
                cuenta.setSaldo(res.getDouble("saldo_debe")-res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }
    
    public List<BG> getPasivosBG(Date inicio, Date fin){
        List<BG> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '2%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BG cuenta = new BG();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")).getNombre_cuenta());
                cuenta.setSaldo(res.getDouble("saldo_haber")-res.getDouble("saldo_debe"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }
    
    public List<BG> getPatrimonioBG(Date inicio, Date fin){
        List<BG> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '3%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BG cuenta = new BG();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")).getNombre_cuenta());
                cuenta.setSaldo(res.getDouble("saldo_haber")-res.getDouble("saldo_debe"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }
    
    public List<BG> getResDeudorBG(Date inicio, Date fin){
        List<BG> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '4%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BG cuenta = new BG();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")).getNombre_cuenta());
                cuenta.setSaldo(res.getDouble("saldo_debe")-res.getDouble("saldo_haber"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }
    
    public List<BG> getResAcreedorBG(Date inicio, Date fin){
        List<BG> cuentas = new ArrayList<>();
        try {
            String query = "SELECT transaccion.id_cuenta, SUM(transaccion.debe) saldo_debe, "
                    + "SUM(transaccion.haber) saldo_haber FROM public.transaccion "
                    + "WHERE transaccion.fecha_tran >=? AND transaccion.fecha_tran <=? AND "
                    + "transaccion.id_cuenta ILIKE '5%' GROUP BY transaccion.id_cuenta "
                    + "ORDER BY transaccion.id_cuenta ASC;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(query);
            pst.setDate(1, inicio);
            pst.setDate(2, fin);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                BG cuenta = new BG();
                cuenta.setCuenta(new CuentaDB().getCuenta(res.getString("id_cuenta")).getNombre_cuenta());
                cuenta.setSaldo(res.getDouble("saldo_haber")-res.getDouble("saldo_debe"));
                cuentas.add(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MayorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }
    
}
