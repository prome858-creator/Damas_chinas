package rules;

import java.util.ArrayList;
import java.util.List;
import model.Casilla;
import model.Pieza;
import model.Tablero;

public class ValidadorMovimientos {

    // Regla 2.5: Revisa si el jugador actual tiene alguna captura OBLIGATORIA disponible en el tablero
    public List<Movimiento> obtenerCapturasObligatorias(Tablero tablero, String bandoActual) {
        List<Movimiento> capturasPosibles = new ArrayList<>();

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Casilla origen = tablero.getCasilla(f, c);
                if (origen.tienePieza() && origen.getPiezaActual().getBando().equalsIgnoreCase(bandoActual)) {
                    capturasPosibles.addAll(obtenerCapturasDePieza(tablero, f, c));
                }
            }
        }
        return capturasPosibles;
    }

    // Busca los saltos de captura posibles para una pieza en particular
    public List<Movimiento> obtenerCapturasDePieza(Tablero tablero, int fila, int col) {
        List<Movimiento> capturas = new ArrayList<>();
        Casilla origen = tablero.getCasilla(fila, col);
        if (!origen.tienePieza()) return capturas;

        Pieza pieza = origen.getPiezaActual();
        // Las damas mueven en 4 direcciones; los peones oscuros/claros en sus direcciones permitidas
        int[][] direcciones = pieza.esDama() 
            ? new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}
            : (pieza.getBando().equalsIgnoreCase("OSCURAS") 
                ? new int[][]{{1, -1}, {1, 1}}    // Oscuras bajan (filas crecientes)
                : new int[][]{{-1, -1}, {-1, 1}}); // Claras suben (filas decrecientes)

        for (int[] dir : direcciones) {
            int fIntermedia = fila + dir[0];
            int cIntermedia = col + dir[1];
            int fDestino = fila + (dir[0] * 2);
            int cDestino = col + (dir[1] * 2);

            // Verificar límites del tablero 8x8
            if (esPosicionValida(fDestino, cDestino)) {
                Casilla intermedia = tablero.getCasilla(fIntermedia, cIntermedia);
                Casilla destino = tablero.getCasilla(fDestino, cDestino);

                // Regla 2.3: Debe saltar sobre una pieza rival y caer en casilla vacía
                if (intermedia.tienePieza() && 
                    !intermedia.getPiezaActual().getBando().equalsIgnoreCase(pieza.getBando()) &&
                    !destino.tienePieza()) {
                    
                    capturas.add(new Movimiento(fila, col, fDestino, cDestino, fIntermedia, cIntermedia));
                }
            }
        }
        return capturas;
    }

    // Valida si un movimiento simple (sin comer) es legal
    public boolean esMovimientoSimpleValido(Tablero tablero, int fOrigen, int cOrigen, int fDestino, int cDestino) {
        Casilla origen = tablero.getCasilla(fOrigen, cOrigen);
        Casilla destino = tablero.getCasilla(fDestino, cDestino);

        if (!origen.tienePieza() || destino.tienePieza()) return false;

        Pieza pieza = origen.getPiezaActual();
        int diffFila = fDestino - fOrigen;
        int diffCol = Math.abs(cDestino - cOrigen);

        // Regla 2.1: Mover en diagonal 1 sola casilla
        if (diffCol != 1) return false;

        if (pieza.esDama()) {
            return Math.abs(diffFila) == 1; // La dama puede avanzar o retroceder (Regla 3.2)
        } else {
            // Regla 2.2: Peón avanza
            if (pieza.getBando().equalsIgnoreCase("OSCURAS")) {
                return diffFila == 1; // Bajan
            } else {
                return diffFila == -1; // Suben
            }
        }
    }

    private boolean esPosicionValida(int fila, int col) {
        return fila >= 0 && fila < 8 && col >= 0 && col < 8;
    }
}