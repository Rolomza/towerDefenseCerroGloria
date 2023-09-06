import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        Juego cerroGloriaTD = new Juego();
        cerroGloriaTD.iniciarJuego();
    }
}

interface Ataque {
    void atacar(Mapa mapa); // Abstrae el comportamiento de ataque ya sea de torre o enemigo.
}




