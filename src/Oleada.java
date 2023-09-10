import java.util.ArrayList;

public class Oleada {
    private ArrayList<Enemigo> listaEnemigosOleada = new ArrayList<>();
    private int nivelActual;
    private int nroOleada;

    public Oleada(int nroNivel) {
        this.nivelActual = nroNivel;
    }

    public void generarEnemigos() {
        switch (this.nivelActual) {
            case 1:
                listaEnemigosOleada.add(new Humano());
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public ArrayList<Enemigo> getListaEnemigosOleada() {
        return listaEnemigosOleada;
    }
}
