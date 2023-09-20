
public class Main {
    public static void main(String[] args) {
        Juego cerroGloriaTD = new Juego();
        cerroGloriaTD.iniciarJuego();
    }
}

interface Ataque {
    void atacar(Nivel nivelActual, Casillero casillero);
}
