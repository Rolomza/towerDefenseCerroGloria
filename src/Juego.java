public class Juego {
    private int puntosMagia;

    // ArrayList<Mapa> mapas = new ArrayList<Mapa>();




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