package model;

public class Casilla {
    private int fila;
    private int columna;
    private boolean esOscura;
    private Pieza piezaActual;

    public Casilla(int fila, int columna, boolean esOscura) {
        this.fila = fila;
        this.columna = columna;
        this.esOscura = esOscura;
        this.piezaActual = null; // Inicia vacía
    }

    public boolean tienePieza() {
        return this.piezaActual != null;
    }

    public Pieza getPiezaActual() {
        return piezaActual;
    }

    public void setPiezaActual(Pieza piezaActual) {
        this.piezaActual = piezaActual;
    }

    public boolean esOscura() {
        return esOscura;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}