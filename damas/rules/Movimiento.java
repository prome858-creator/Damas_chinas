package rules;

public class Movimiento {
    private int origenFila;
    private int origenCol;
    private int destinoFila;
    private int destinoCol;
    private boolean esCaptura;
    private int capturaFila; // Coordenada de la pieza que se va a eliminar
    private int capturaCol;

    public Movimiento(int origenFila, int origenCol, int destinoFila, int destinoCol) {
        this.origenFila = origenFila;
        this.origenCol = origenCol;
        this.destinoFila = destinoFila;
        this.destinoCol = destinoCol;
        this.esCaptura = false;
    }

    public Movimiento(int origenFila, int origenCol, int destinoFila, int destinoCol, int capturaFila, int capturaCol) {
        this(origenFila, origenCol, destinoFila, destinoCol);
        this.esCaptura = true;
        this.capturaFila = capturaFila;
        this.capturaCol = capturaCol;
    }

    // Getters
    public int getOrigenFila() { return origenFila; }
    public int getOrigenCol() { return origenCol; }
    public int getDestinoFila() { return destinoFila; }
    public int getDestinoCol() { return destinoCol; }
    public boolean esCaptura() { return esCaptura; }
    public int getCapturaFila() { return capturaFila; }
    public int getCapturaCol() { return capturaCol; }
}