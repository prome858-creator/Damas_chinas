package game;

import java.util.List;
import model.Casilla;
import model.Pieza;
import model.Tablero;
import rules.GestorCoronacion;
import rules.Movimiento;
import rules.ValidadorMovimientos;

public class Partida {
    @SuppressWarnings("FieldMayBeFinal")
    private Tablero tablero;
    @SuppressWarnings("FieldMayBeFinal")
    private ValidadorMovimientos validador;
    private String turnoActual;
    private int movimientosSinCapturaNiPeon;
    private boolean juegoTerminado;
    private String ganador;

    public Partida() {
        this.tablero = new Tablero();
        this.validador = new ValidadorMovimientos();
        this.turnoActual = "OSCURAS";
        this.movimientosSinCapturaNiPeon = 0;
        this.juegoTerminado = false;
        this.ganador = null;
    }

    public boolean realizarMovimiento(int fOrig, int cOrig, int fDest, int cDest) {
        if (juegoTerminado) return false;

        Casilla origen = tablero.getCasilla(fOrig, cOrig);
        // AQUÍ ESTABA EL ERROR: debe ser fDest y cDest
        Casilla destino = tablero.getCasilla(fDest, cDest);

        if (!origen.tienePieza() || !origen.getPiezaActual().getBando().equalsIgnoreCase(turnoActual)) {
            return false;
        }

        Pieza piezaMovida = origen.getPiezaActual();
        boolean eraPeon = !piezaMovida.esDama();

        List<Movimiento> capturasObligatorias = validador.obtenerCapturasObligatorias(tablero, turnoActual);

        if (!capturasObligatorias.isEmpty()) {
            Movimiento capturaElegida = null;
            for (Movimiento m : capturasObligatorias) {
                if (m.getOrigenFila() == fOrig && m.getOrigenCol() == cOrig &&
                    m.getDestinoFila() == fDest && m.getDestinoCol() == cDest) {
                    capturaElegida = m;
                    break;
                }
            }

            if (capturaElegida == null) {
                return false;
            }

            // Realizar captura
            tablero.getCasilla(capturaElegida.getCapturaFila(), capturaElegida.getCapturaCol()).setPiezaActual(null);
            destino.setPiezaActual(piezaMovida);
            origen.setPiezaActual(null);

            GestorCoronacion.evaluarYCoronar(destino);
            movimientosSinCapturaNiPeon = 0;

            List<Movimiento> masCapturas = validador.obtenerCapturasDePieza(tablero, fDest, cDest);
            if (!masCapturas.isEmpty() && !piezaMovida.esDama()) {
                return true;
            }

            cambiarTurno();
            evaluarEstadoJuego();
            return true;
        } else {
            // Movimiento simple sin captura
            if (validador.esMovimientoSimpleValido(tablero, fOrig, cOrig, fDest, cDest)) {
                destino.setPiezaActual(piezaMovida);
                origen.setPiezaActual(null);

                GestorCoronacion.evaluarYCoronar(destino);

                if (eraPeon) {
                    movimientosSinCapturaNiPeon = 0;
                } else {
                    movimientosSinCapturaNiPeon++;
                }

                cambiarTurno();
                evaluarEstadoJuego();
                return true;
            }
        }
        return false;
    }

    private void cambiarTurno() {
        this.turnoActual = this.turnoActual.equalsIgnoreCase("OSCURAS") ? "CLARAS" : "OSCURAS";
    }

    private void evaluarEstadoJuego() {
        if (movimientosSinCapturaNiPeon >= 40) {
            this.juegoTerminado = true;
            this.ganador = "EMPATE";
        }
    }

    public Tablero getTablero() { return tablero; }
    public String getTurnoActual() { return turnoActual; }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public String getGanador() { return ganador; }
}