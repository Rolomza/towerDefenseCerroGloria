import java.sql.SQLOutput;
import java.util.Scanner;

public class Juego {

    public void iniciarJuego() {
        System.out.println("Bienvenido a Cerro de la Gloria Defense!");
        int cantidadNiveles = 2;

        Nivel nivel1 = new Nivel(1);
        nivel1.generarCasillerosEnemigos();

        while(cantidadNiveles > 0) {
            nivel1.iniciarNivel();
            cantidadNiveles--;
        }
    }


}