/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

/**
 *
 * @author Oscar
 */
public class Partida {
    
    private Integer id_partida;
    private Integer num_partida;

    public Integer getId_partida() {
        return id_partida;
    }

    public Partida() {
    }

    public Partida(Integer id_partida, Integer num_partida) {
        this.id_partida = id_partida;
        this.num_partida = num_partida;
    }
    
    public void setId_partida(Integer id_partida) {
        this.id_partida = id_partida;
    }

    public Integer getNum_partida() {
        return num_partida;
    }

    public void setNum_partida(Integer num_partida) {
        this.num_partida = num_partida;
    }
    
}
