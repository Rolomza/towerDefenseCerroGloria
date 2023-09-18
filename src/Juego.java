import java.sql.SQLOutput;
import java.util.Scanner;

public class Juego {

    public void iniciarJuego() {
        System.out.println("Bienvenido a Cerro de la Gloria Defense!");
        int cantidadNiveles = 2;

        Nivel nivel = new Nivel(1);
        nivel.generarCasillerosEnemigos();

        while(nivel.getNroNivel() <= 3) {
            nivel.iniciarNivel();
            cantidadNiveles--;
            nivel.aumentarNivel();
        }
    }


}