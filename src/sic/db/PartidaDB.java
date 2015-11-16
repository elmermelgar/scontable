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
import sic.model.Partida;

/**
 *
 * @author Oscar
 */
public class PartidaDB {
    
    List<Partida> partidas = new ArrayList<>();
    
    public List<Partida> getPartidas(){
        
        partidas.clear();
        try {
            String sql = "SELECT * FROM partida ORDER BY num_partida ASC";
            Statement st = Conexion.getConexion().createStatement();
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                Partida p = new Partida();
                p.setId_partida(res.getInt("id_partida"));
                p.setNum_partida(res.getInt("num_partida"));
                partidas.add(p);
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return partidas;
        
    }
    
    public Partida getPartida(Integer num_partida){
        
        Partida p = null;
        try {
            String sql = "SELECT * FROM partida WHERE num_partida=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            
            pst.setInt(1, num_partida);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                p = new Partida();
                p.setId_partida(res.getInt("id_partida"));
                p.setNum_partida(res.getInt("num_partida"));
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return p;
    }
    
    public boolean guardar(Integer num_partida) {
        boolean guardado = true;
        try {
            Integer id_partida = Integer.valueOf(getNextIdPartida());
            String sql = "INSERT INTO partida(id_partida, num_partida) VALUES (?,?)";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, id_partida);
            pst.setInt(2, num_partida);
            pst.execute();
        } catch (SQLException e) {
            guardado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return guardado;
    }
    
    public boolean actualizar(Integer id_partida, Integer num_partida) {
        boolean actualizado = true;
        try {
            String sql = "UPDATE partida SET num_partida=? WHERE id_partida=?";
            PreparedStatement pst = Conexion.getConexion().prepareStatement(sql);
            pst.setInt(1, num_partida);
            pst.setInt(2, id_partida);
            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return actualizado;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }
    
    public int getNextNumeroPartida(){
        
        int numero = 0;
        try {
            String sql = "SELECT MAX(num_partida) num FROM partida";
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
    
    public int getNextIdPartida(){
        
        int numero = 0;
        try {
            String sql = "SELECT MAX(id_partida) num FROM partida";
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
