package TowerDefenceCerro;

import TowerDefenceCerro.MomentosJuego.Nivel;
/**
 * Clase principal del juego que maneja la ejecución de los niveles.
 * @author  Victor Ramirez
 * @version 1.0
 */

public class Main {
    /**
     * Método principal que inicia el juego y los niveles.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {

        Nivel nivel = new Nivel(1);
        nivel.generarCasillerosEnemigos();

        while(nivel.getNroNivel() <= 3) {
            if (!nivel.getDerrotado()) {
                nivel.iniciarNivel();
                nivel.aumentarNivel();
            } else {
                System.out.println("Juego terminado.");
                break;
            }
        }
    }
}

