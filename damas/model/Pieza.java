package model;

public class Pieza {
    private String bando; // "OSCURAS" o "CLARAS"
    private boolean esDama;

    public Pieza(String bando) {
        this.bando = bando;
        this.esDama = false; // Empieza siendo peón
    }

    public String getBando() {
        return bando;
    }

    public boolean esDama() {
        return esDama;
    }

    public void coronar() {
        this.esDama = true; // Regla 3.1: Se convierte en dama
    }
}