package rules;

import model.Casilla;
import model.Pieza;

public class GestorCoronacion {

    // Regla 3.1: Revisa si el peón llegó al extremo opuesto para convertirlo en Dama
    public static boolean evaluarYCoronar(Casilla casillaDestino) {
        if (!casillaDestino.tienePieza()) return false;

        Pieza pieza = casillaDestino.getPiezaActual();
        int fila = casillaDestino.getFila();

        if (!pieza.esDama()) {
            // Oscuras se coronan en la fila 7; Claras en la fila 0
            if ((pieza.getBando().equalsIgnoreCase("OSCURAS") && fila == 7) ||
                (pieza.getBando().equalsIgnoreCase("CLARAS") && fila == 0)) {
                pieza.coronar();
                return true; // Se coronó con éxito
            }
        }
        return false;
    }
}