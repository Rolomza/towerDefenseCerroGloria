import java.sql.SQLOutput;
import java.util.Scanner;

public class Juego {

    public void iniciarJuego() {
        System.out.println("Bienvenido a Cerro de la Gloria Defense!");

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