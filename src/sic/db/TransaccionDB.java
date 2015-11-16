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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.model.Cuenta;
import sic.model.Partida;
import sic.model.Transaccion;

/**
 *
 * @author Oscar
 */
public class TransaccionDB {
    
    private List<Transaccion> transacciones = new ArrayList<>();

    
    public List<Transaccion> getTransacciones() {
        
        transacciones.clear();
        try {
            String sql = "SELECT * FROM transaccion";
            Statement st = Conexion.getConexion().createStatement();
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                Transaccion tran = new Transaccion();
                tran.setId_transaccion(res.getInt("id_transaccion"));
                tran.setCuenta((new CuentaDB()).getCuenta(res.getString("id_cuenta")));
                tran.setPartida((new PartidaDB()).getPartida(res.getInt("id_partida")));
                tran.setFecha_tran(res.getDate("fecha_tran"));
                tran.setDebe(res.getDouble("debe"));
                tran.setHaber(res.getDouble("haber"));
                transacciones.add(tran);
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return transacciones;
    }
    
    public Transaccion getTransaccion(Integer id_transaccion){
        
        Transaccion tran = null;
        try {
            String sql = "SELECT * FROM transaccion WHERE id_transaccion= ?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_transaccion);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                tran = new Transaccion();
                tran.setId_transaccion(res.getInt("id_transaccion"));
                tran.setCuenta((new CuentaDB()).getCuenta(res.getString("id_cuenta")));
                tran.setPartida((new PartidaDB()).getPartida(res.getInt("id_partida")));
                tran.setFecha_tran(res.getDate("fecha_tran"));
                tran.setDebe(res.getDouble("debe"));
                tran.setHaber(res.getDouble("haber"));
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return tran;
    }
    
    public List<Transaccion> getPartida(Integer id_partida){
        
        transacciones.clear();
        try {
            String sql = "SELECT * FROM transaccion WHERE id_partida=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_partida);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                Transaccion tran = new Transaccion();
                tran.setId_transaccion(res.getInt("id_transaccion"));
                tran.setCuenta((new CuentaDB()).getCuenta(res.getString("id_cuenta")));
                tran.setPartida((new PartidaDB()).getPartida(res.getInt("id_partida")));
                tran.setFecha_tran(res.getDate("fecha_tran"));
                tran.setDebe(res.getDouble("debe"));
                tran.setHaber(res.getDouble("haber"));
                transacciones.add(tran);
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return transacciones;
    }
    
    public boolean guardar(Cuenta c, Partida p, Date fecha_tran, Double debe, Double haber) {
        boolean guardado = true;
        try {
            Integer id_transaccion = Integer.valueOf(getNextIdTransaccion());
            String sql = "INSERT INTO transaccion(id_transaccion, id_cuenta, id_partida, fecha_tran, debe, haber) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_transaccion);
            pst.setString(2, c.getId_cuenta());
            pst.setInt(3, p.getId_partida());
            pst.setDate(4, fecha_tran);
            pst.setDouble(5, debe);
            pst.setDouble(6, haber);
            pst.execute();
        } catch (SQLException e) {
            guardado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return guardado;
    }
    
    public boolean actualizar(Integer id_transaccion, Cuenta c, Partida p, Date fecha_tran, Double debe, Double haber) {
        boolean actualizado = true;
        try {
            String sql = "UPDATE transaccion SET id_cuenta=?, id_partida=?, fecha_tran=?, debe=?, haber=? WHERE id_transaccion=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setString(1, c.getId_cuenta());
            pst.setInt(2, p.getId_partida());
            pst.setDate(3, fecha_tran);
            pst.setDouble(4, debe);
            pst.setDouble(5, haber);
            pst.setInt(6, id_transaccion);
            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return actualizado;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
    
    private Integer getNextIdTransaccion() {
        
        int numero = 0;
        try {
            String sql = "SELECT MAX(id_transaccion) num FROM transaccion";
            Statement st = Conexion.getConexion().createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while(resultado.next()){
                numero = resultado.getInt("num") + 1;
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return numero;
        
    }
    
}
