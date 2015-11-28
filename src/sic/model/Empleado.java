/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

import java.sql.Date;

/**
 *
 * @author Oscar
 */
public class Empleado {
    private Integer id_emplado;
    private Integer id_planilla;
    private String nombre;
    private String apellido;
    private Double salario;
    private Date fecha_ingreso;
    
    private int numero_empleados = 0;
    private double monto_total_planilla = 0.0;

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, Double salario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.salario = salario;
    }
    
    public Integer getId_planilla() {
        return id_planilla;
    }

    public void setId_planilla(Integer id_planilla) {
        this.id_planilla = id_planilla;
    }

    public Integer getId_emplado() {
        return id_emplado;
    }

    public void setId_emplado(Integer id_emplado) {
        this.id_emplado = id_emplado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public int getNumero_empleados() {
        return numero_empleados;
    }

    public void setNumero_empleados(int numero_empleados) {
        this.numero_empleados = numero_empleados;
    }

    public double getMonto_total_planilla() {
        return monto_total_planilla;
    }

    public void setMonto_total_planilla(double monto_total_planilla) {
        this.monto_total_planilla = monto_total_planilla;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }
}
