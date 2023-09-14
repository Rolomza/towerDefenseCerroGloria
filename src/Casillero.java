import java.util.ArrayList;
import java.util.HashMap;

public class Casillero {
    private HashMap<String,ArrayList<Entidad>> entidadesCasillero = new HashMap<>();

    public Casillero() {}

    private ArrayList<Entidad> listaHumanos = new ArrayList<>();
    private ArrayList<Entidad> listaHobbits = new ArrayList<>();
    private ArrayList<Entidad> listaElfos = new ArrayList<>();
    private ArrayList<Entidad> listaEnanos = new ArrayList<>();

    public void agregarEnemigo(Enemigo enemigo) {

        if (enemigo instanceof Humano) {
            Humano humano = (Humano) enemigo;
            listaHumanos.add(humano);
            entidadesCasillero.put("Humano",listaHumanos);
            //
        } else if (enemigo instanceof Hobbit) {
            Hobbit hobbit = (Hobbit) enemigo;
            listaHobbits = entidadesCasillero.get("Hobbit");
            listaHobbits.add(hobbit);
            entidadesCasillero.put("Hobbit",listaHobbits);
            //
        } else if (enemigo instanceof Elfo) {
            Elfo elfo = (Elfo) enemigo;
            listaElfos = entidadesCasillero.get("Elfo");
            listaElfos.add(elfo);
            entidadesCasillero.put("Elfo",listaElfos);
            //
        } else if (enemigo instanceof Enano) {
            Enano enano = (Enano) enemigo;
            listaEnanos = entidadesCasillero.get("Enano");
            listaEnanos.add(enano);
            entidadesCasillero.put("Enano",listaEnanos);
            //
        }
        }

    public void mostrarCasillero() {
        /// Ver como implementarlo, saludos Victor
    }

    public void mostrarListaHumanos() {
        Humano h1 = (Humano) this.listaHumanos.get(0);
        h1.getNombreClass();
    }
}
