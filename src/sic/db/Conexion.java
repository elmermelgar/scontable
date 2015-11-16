/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oscar
 */
public class Conexion {
    private static Connection con;
   
    private static void conectar() {
        String url = "jdbc:postgresql://localhost:5432/";
        String db = "sic_tarea";
        String user = "sic_tarea";
        String password = "sic_tarea";
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            con = DriverManager.getConnection(url+db, user, password);
        } catch (SQLException | ClassNotFoundException | InstantiationException |
                IllegalAccessException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConexion() {
        if (con == null) {
            conectar();
        }
        return con;
    }
    
}
