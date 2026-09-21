package rules;

import model.Casilla;
import model.Pieza;
import model.Tablero;

public class ValidadorEntrada {

    /**
     * Traduce el comando del usuario a coordenadas [fOrig, cOrig, fDest, cDest].
     * 
     * Formatos aceptados:
     * - Peón: "fila col direccion" (ej. "2 5 D" o "5 0 I")
     * - Dama: "fila col sentido direccion" (ej. "7 3 A D" para Atrás-Derecha o "7 3 F I" para Frente-Izquierda)
     */
    public static int[] traducirYValidar(String entrada, Tablero tablero, String bandoActual) {
        if (entrada == null) return null;

        String[] partes = entrada.trim().toUpperCase().split("\\s+");

        // Validar mínimo de argumentos (3 para peón, 4 para dama)
        if (partes.length < 3 || partes.length > 4) {
            return null;
        }

        try {
            int fOrig = Integer.parseInt(partes[0]);
            int cOrig = Integer.parseInt(partes[1]);

            // Validar límites iniciales de tablero
            if (fOrig < 0 || fOrig > 7 || cOrig < 0 || cOrig > 7) {
                return null;
            }

            Casilla casillaOrig = tablero.getCasilla(fOrig, cOrig);
            if (!casillaOrig.tienePieza()) {
                return null; // No hay pieza en la coordenada elegida
            }

            Pieza pieza = casillaOrig.getPiezaActual();
            if (!pieza.getBando().equalsIgnoreCase(bandoActual)) {
                return null; // La pieza no pertenece al jugador en turno
            }

            int fDest = fOrig;
            int cDest = cOrig;

            if (!pieza.esDama()) {
                // --- LÓGICA DE PEÓN (3 PARÁMETROS: fila col I/D) ---
                if (partes.length != 3) return null;

                String dir = partes[2];
                int avanceFila = bandoActual.equalsIgnoreCase("OSCURAS") ? 1 : -1; // Oscuras bajan (+1), Claras suben (-1)
                int avanceCol = dir.equals("D") ? 1 : (dir.equals("I") ? -1 : 0);

                if (avanceCol == 0) return null; // Dirección inválida (debe ser I o D)

                fDest = fOrig + avanceFila;
                cDest = cOrig + avanceCol;

            } else {
                // --- LÓGICA DE DAMA (4 PARÁMETROS: fila col F/A I/D) ---
                if (partes.length != 4) return null;

                String sentido = partes[2]; // F = Frente/Adelante, A = Atrás
                String dir = partes[3];     // I = Izquierda, D = Derecha

                int avanceFila = 0;
                // Para OSCURAS: Frente es bajar (+1), Atrás es subir (-1)
                // Para CLARAS: Frente es subir (-1), Atrás es bajar (+1)
                if (bandoActual.equalsIgnoreCase("OSCURAS")) {
                    avanceFila = sentido.equals("F") ? 1 : (sentido.equals("A") ? -1 : 0);
                } else {
                    avanceFila = sentido.equals("F") ? -1 : (sentido.equals("A") ? 1 : 0);
                }

                int avanceCol = dir.equals("D") ? 1 : (dir.equals("I") ? -1 : 0);

                if (avanceFila == 0 || avanceCol == 0) return null; // Sentido o dirección inválidos

                fDest = fOrig + avanceFila;
                cDest = cOrig + avanceCol;
            }

            // Validar que el destino calculado no se salga del tablero
            if (fDest < 0 || fDest > 7 || cDest < 0 || cDest > 7) {
                return null;
            }

            return new int[]{fOrig, cOrig, fDest, cDest};

        } catch (NumberFormatException e) {
            return null; // Si las coordenadas de origen no son números
        }
    }
}