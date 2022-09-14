package com.emilianohg.models;

public class Billete {
    private int existencia;
    private int denominacion;
    private String fecha;

    public Billete(int denominacion) {
        this(denominacion, 1);
    }

    public Billete(int denominacion, int existencia) {
        this(denominacion, existencia, null);
    }

    public Billete(int denominacion, int existencia, String fecha) {
        this.existencia = existencia;
        this.denominacion = denominacion;
        this.fecha = fecha;
    }

    public void agregarExistencia(int extra) {
        this.existencia += extra;
    }

    public int getExistencia() {
        return existencia;
    }

    public int getDenominacion() {
        return denominacion;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Money{" +
                "existencia=" + existencia +
                ", denominacion=" + denominacion +
                ", fecha=" + fecha +
                '}';
    }
}
