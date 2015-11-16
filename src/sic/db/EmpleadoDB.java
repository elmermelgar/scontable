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
import sic.model.Empleado;

/**
 *
 * @author Oscar
 */
public class EmpleadoDB {
    private List<Empleado> empleados = new ArrayList<>();
    
    public boolean guardar(String nombre_empleado, String apellido_empleado, Double salario){
        boolean guardado = true;
        try {
            int id_empleado = getNextIdEmpleado();
            String sql = "INSERT INTO empleado(id_empleado, nombre_empleado, apellido_empleado, salario) VALUES (?,?,?,?)";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_empleado);
            pst.setString(2, nombre_empleado);
            pst.setString(3, apellido_empleado);
            pst.setDouble(4, salario);
            pst.execute();
        } catch (SQLException e) {
            guardado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return guardado;
    }
    
    public boolean actualizar(Integer id_empleado, String nombre_empleado, String apellido_empleado, Double salario) {
        boolean actualizado = true;
        try {
            String sql = "UPDATE empleado SET nombre_empleado=?, apellido_empleado=?, salario=? WHERE id_empleado=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setString(1, nombre_empleado);
            pst.setString(2, apellido_empleado);
            pst.setDouble(3, salario);
            pst.setInt(4, id_empleado);
            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return actualizado;
    }
    
    public boolean actualizar(Integer id_empleado, Integer id_planilla, String nombre_empleado, String apellido_empleado, Double salario) {
        boolean actualizado = true;
        try {
            String sql = "UPDATE empleado SET id_planilla=?, nombre_empleado=?, apellido_empleado=?, salario=? WHERE id_empleado=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_planilla);
            pst.setString(2, nombre_empleado);
            pst.setString(3, apellido_empleado);
            pst.setDouble(4, salario);
            pst.setInt(5, id_empleado);
            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return actualizado;
    }
    
    public boolean actualizar(Integer id_empleado, Integer id_planilla) {
        boolean actualizado = true;
        try {
            String sql = "UPDATE empleado SET id_planilla=? WHERE id_empleado=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_planilla);
            pst.setInt(2, id_empleado);
            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return actualizado;
    }
    
    public boolean eliminar(Integer id_empleado) {
        boolean elim = true;
        try {
            String sql = "DELETE FROM empleado WHERE id_empleado=?;";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_empleado);
            pst.executeUpdate();
        } catch (SQLException e) {
            elim = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return elim;
    }
    
    public List<Empleado> getEmpleados() {
        empleados.clear();
        try {
            String sql = "SELECT * FROM empleado";
            Statement st = Conexion.getConexion().createStatement();
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                Empleado e = new Empleado();
                e.setId_emplado(res.getInt("id_empleado"));
                e.setNombre(res.getString("nombre_empleado"));
                e.setApellido(res.getString("apellido_empleado"));
                e.setSalario(res.getDouble("salario"));
                
                empleados.add(e);
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    public int getNextIdEmpleado(){
        
        int numero = 0;
        try {
            String sql = "SELECT MAX(id_empleado) num FROM empleado";
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
    
    public Empleado getElementoCosto(){
        
        Empleado emp = null;
        try {
            String sql = "SELECT sum(empleado.salario) total, count(empleado.id_empleado) num FROM public.empleado;";
            Statement st = Conexion.getConexion().createStatement();
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                emp = new Empleado();
                emp.setMonto_total_planilla(res.getDouble("total"));
                emp.setNumero_empleados(res.getInt("num"));
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return emp;
    }
}
