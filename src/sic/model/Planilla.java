/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.model;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Oscar
 */
public class Planilla {
    private Integer id_planilla;
    private Date fecha_planilla;
    private String nombre_empleado;
    private Double salario_nominal;
    private Double isss;
    private Double afp;
    private Double vacacion;
    private Double aguinaldo;
    private Double bono;
    private Double descuento;
    private Double salario_real;

    public Integer getId_planilla() {
        return id_planilla;
    }

    public void setId_planilla(Integer id_planilla) {
        this.id_planilla = id_planilla;
    }

    public Date getFecha_planilla() {
        return fecha_planilla;
    }

    public void setFecha_planilla(Date fecha_planilla) {
        this.fecha_planilla = fecha_planilla;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public Double getSalario_nominal() {
        return salario_nominal;
    }

    public void setSalario_nominal(Double salario_nominal) {
        this.salario_nominal = salario_nominal;
    }

    public Double getIsss() {
        return isss;
    }

    public void setIsss(Double isss) {
        this.isss = isss;
    }

    public Double getAfp() {
        return afp;
    }

    public void setAfp(Double afp) {
        this.afp = afp;
    }

    public Double getVacacion() {
        return vacacion;
    }

    public void setVacacion(Double vacacion) {
        this.vacacion = vacacion;
    }

    public Double getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(Double aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public Double getBono() {
        return bono;
    }

    public void setBono(Double bono) {
        this.bono = bono;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getSalario_real() {
        return salario_real;
    }

    public void setSalario_real(Double salario_real) {
        this.salario_real = salario_real;
    }
    
    public int getAniosTrabajados(Date fecha_inicio){
        Calendar cal=Calendar.getInstance();
        int anioActual = cal.get(Calendar.YEAR);
        int mesActual = cal.get(Calendar.MONTH);
        int diaActual = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(fecha_inicio);
        
        int anio=anioActual-cal.get(Calendar.YEAR);
        int mes=anioActual-cal.get(Calendar.MONTH);
        if(mes==mesActual){
            return cal.get(Calendar.DAY_OF_MONTH)<=diaActual?anio:anio-1;
        }else{
            return mes<mesActual?anio-1:anio;
        }
    }
}
