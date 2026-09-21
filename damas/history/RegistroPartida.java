package history;

import java.util.ArrayList;
import java.util.List;

public class RegistroPartida {
    private List<String> historial;

    public RegistroPartida() {
        this.historial = new ArrayList<>();
    }

    // Guarda cada movimiento en formato legible (ej. "OSCURAS movió de [2, 1] a [3, 2]")
    public void registrarMovimiento(String bando, int fOrig, int cOrig, int fDest, int cDest, boolean fueCaptura) {
        String tipo = fueCaptura ? "CAPTURA" : "MOVIMIENTO";
        String entrada = String.format("%s (%s): [%d, %d] -> [%d, %d]", bando, tipo, fOrig, cOrig, fDest, cDest);
        historial.add(entrada);
    }

    // Requisito Adicional: Permite dar un repaso posterior a cómo se jugó
    public void mostrarRepaso() {
        System.out.println("\n=== REPASO HISTÓRICO DE LA PARTIDA ===");
        if (historial.isEmpty()) {
            System.out.println("No se realizaron movimientos en esta partida.");
            return;
        }
        for (int i = 0; i < historial.size(); i++) {
            System.out.println((i + 1) + ". " + historial.get(i));
        }
        System.out.println("=====================================\n");
    }
}