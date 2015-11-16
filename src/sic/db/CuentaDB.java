/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sic.model.Cuenta;

/**
 *
 * @author Oscar
 */
public class CuentaDB {
    
    private List<Cuenta> cuentas = new ArrayList<>();
        
    public List<Cuenta> getCuentas(){
        
        cuentas.clear();
        try {
            String sql = "SELECT * FROM cuenta ORDER BY id_cuenta ASC";
            Statement st = Conexion.getConexion().createStatement();
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                Cuenta c = new Cuenta();
                c.setId_cuenta(res.getString("id_cuenta"));
                c.setNombre_cuenta(res.getString("nombre_cuenta"));
                c.setTipo_cuenta(res.getString("tipo_cuenta"));
                cuentas.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return cuentas;
        
    }
    
    public Cuenta getCuenta(String id_cuenta){
        
        Cuenta c = null;
        try {
            String sql = "SELECT * FROM cuenta WHERE id_cuenta= ?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setString(1, id_cuenta);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                c = new Cuenta();
                c.setId_cuenta(res.getString("id_cuenta"));
                c.setNombre_cuenta(res.getString("nombre_cuenta"));
                c.setTipo_cuenta(res.getString("tipo_cuenta"));
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return c;
    }
    
    public boolean guardar(String id_cuenta, String nombre_cuenta, String tipo_cuenta) {
        boolean guardado = true;
        try {
            String sql = "INSERT INTO cuenta(id_cuenta, nombre_cuenta, tipo_cuenta) VALUES (?,?,?)";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setString(1, id_cuenta);
            pst.setString(2, nombre_cuenta);
            pst.setString(3, tipo_cuenta);
            pst.execute();
        } catch (SQLException e) {
            guardado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return guardado;
    }
    
    public boolean actualizar(String id_cuenta, String nombre_cuenta, String tipo_cuenta) {
        boolean actualizado = true;
        try {
            String sql = "UPDATE cuenta SET nombre_cuenta=?, tipo_cuenta=? WHERE id_cuenta=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setString(1, nombre_cuenta);
            pst.setString(2, tipo_cuenta);
            pst.setString(3, id_cuenta);
            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return actualizado;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
    
    
}
