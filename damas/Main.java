import game.Partida;
import history.RegistroPartida;
import java.util.Scanner;
import model.Casilla;
import rules.ValidadorEntrada;

public class Main {

    // Códigos ANSI para colores en la terminal de VS Code
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_ROJO = "\u001B[31m";  // peas Oscuras (Rojas)
    public static final String ANSI_AZUL = "\u001B[34m";  // Piezas Claras (Azules)

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Partida partida = new Partida();
        RegistroPartida registro = new RegistroPartida();

        System.out.println("====================================");
        System.out.println("   JUEGO DE DAMAS INGLESAS (JAVA)   ");
        System.out.println("====================================");

        while (!partida.isJuegoTerminado()) {
            dibujarTablero(partida);

            String bandoActual = partida.getTurnoActual();
            String colorBando = bandoActual.equalsIgnoreCase("OSCURAS") 
                ? ANSI_ROJO + "ROJAS (OSCURAS)" + ANSI_RESET 
                : ANSI_AZUL + "AZULES (CLARAS)" + ANSI_RESET;
            
            System.out.println("Turno de las piezas: " + colorBando);
            System.out.println("Formato Peon: [fila] [col] [I/D]          (Ejemplo: 2 5 D)");
            System.out.println("Formato Dama: [fila] [col] [F/A] [I/D]    (Ejemplo: 7 3 A D)");
            System.out.print("Movimiento (o escribe 'salir'): ");

            String entrada = scanner.nextLine();
            if (entrada.equalsIgnoreCase("salir")) break;

            // Llama al nuevo método traducirYValidar
            int[] coords = ValidadorEntrada.traducirYValidar(entrada, partida.getTablero(), bandoActual);

            if (coords == null) {
                System.out.println("\n[!] Entrada invalida. Verifica que la casilla contenga una pieza de tu bando y la direccion sea correcta.\n");
                continue;
            }

            boolean exito = partida.realizarMovimiento(coords[0], coords[1], coords[2], coords[3]);

            if (exito) {
                boolean fueCaptura = Math.abs(coords[2] - coords[0]) > 1;
                registro.registrarMovimiento(bandoActual, coords[0], coords[1], coords[2], coords[3], fueCaptura);
                System.out.println("-> Movimiento ejecutado con exito.\n");
            } else {
                System.out.println("\n[!] Movimiento INVALIDO por reglas de juego.\n");
            }
        }

        System.out.println("\nFin del juego.");
        if (partida.getGanador() != null) {
            System.out.println("Resultado: " + partida.getGanador());
        }

        registro.mostrarRepaso();
        scanner.close();
    }

    private static void dibujarTablero(Partida partida) {
        System.out.println("\n  0 1 2 3 4 5 6 7 (Columnas)");
        for (int f = 0; f < 8; f++) {
            System.out.print(f + " ");
            for (int c = 0; c < 8; c++) {
                Casilla casilla = partida.getTablero().getCasilla(f, c);
                if (!casilla.esOscura()) {
                    System.out.print(". ");
                } else if (!casilla.tienePieza()) {
                    System.out.print("- ");
                } else {
                    String bando = casilla.getPiezaActual().getBando();
                    boolean esDama = casilla.getPiezaActual().esDama();
                    
                    if (bando.equalsIgnoreCase("OSCURAS")) {
                        System.out.print(ANSI_ROJO + (esDama ? "O " : "o ") + ANSI_RESET);
                    } else {
                        System.out.print(ANSI_AZUL + (esDama ? "X " : "x ") + ANSI_RESET);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}