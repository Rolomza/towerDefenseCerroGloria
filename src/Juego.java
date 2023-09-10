import java.sql.SQLOutput;
import java.util.Scanner;

public class Juego {
    private int puntosMagia = 300;

    // ArrayList<Mapa> mapas = new ArrayList<Mapa>();


    public void iniciarJuego() {
        // Instanciar mapa
        // Crear objero Nivel nivel1 = new Nivel();
        // nivel1.generarMapa()
        // nivel1.mostrarMenuUsuario()
        // nivel1.iniciarNivel()
                // iniciarNivel genera oleadas

        System.out.println("Bienvenido a Cerro de la Gloria Defense!");
        Nivel nivel1 = new Nivel(1);
        nivel1.generarCoordCaminoEnemigos();
        nivel1.mostrarMapaNivel();
        nivel1.menuTorre();
        nivel1.cargarEnemigosCasillero(); // Que no explote nada por favor te lo pido.

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