package model;

public class Tablero {
    private Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[8][8];
        inicializarTablero();
    }

    public void inicializarTablero() {
        // 1. Crear las 64 casillas y definir cuáles son oscuras
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                // En las damas inglesas se juega en las casillas oscuras. 
                // Una casilla es oscura si la suma de fila y columna es impar (o par según convención).
                boolean oscura = (f + c) % 2 != 0;
                casillas[f][c] = new Casilla(f, c, oscura);
            }
        }

        // 2. Colocar las piezas en las filas iniciales (Regla 1.2)
        // Las oscuras ocupan las filas 0, 1 y 2 (en casillas oscuras)
        // Las claras ocupan las filas 5, 6 y 7 (en casillas oscuras)
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                if (casillas[f][c].esOscura()) {
                    if (f < 3) {
                        casillas[f][c].setPiezaActual(new Pieza("OSCURAS"));
                    } else if (f > 4) {
                        casillas[f][c].setPiezaActual(new Pieza("CLARAS"));
                    }
                }
            }
        }
    }

    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }
}