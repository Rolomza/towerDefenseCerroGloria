package TowerDefenceCerro;

import TowerDefenceCerro.MomentosJuego.Nivel;

public class Main {
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

