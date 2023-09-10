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
        Nivel nivel1 = new Nivel();
        nivel1.generarCoordCaminoEnemigos();
        nivel1.mostrarMapaNivel();
        nivel1.menuTorre();


//        // Iniciar menus para usuario
//        System.out.println("Debe posicionar sus torres en el mapa");
//        // Se debe mostrar cantidad de puntos de magia
//        System.out.println("Puntos de magia disponibles: " + this.getPuntosMagia());
//
//        // Se debe mostrar tipos de torres disponibles para comprar y su precio y caracteristicas
//
//        this.comprarTorres();
//
//        // Posicionar torres
//            // Dar inicio al juego si está seguro


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