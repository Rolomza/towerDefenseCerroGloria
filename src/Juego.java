import java.sql.SQLOutput;
import java.util.Scanner;

public class Juego {
    private int puntosMagia = 300;

    public void iniciarJuego() {
        System.out.println("Bienvenido a Cerro de la Gloria Defense!");
        // Aca iria un For donde nroNivel comienze en 1 y termine en 3
        Nivel nivel1 = new Nivel(1);
        nivel1.generarCasillerosEnemigos();
        nivel1.mostrarMapaNivel();
        //nivel1.menuTorre();
        nivel1.iniciarNivel();
    }

    // Getters and setter
    public int getPuntosMagia() {
        return puntosMagia;
    }

    public void aumentarPuntosMagia(int puntosMagia) {
        this.puntosMagia += puntosMagia;
    }

    public void restarPuntosMagia(int puntosMagia) {
        this.puntosMagia -= puntosMagia;
    }

}