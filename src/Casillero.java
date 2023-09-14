import java.util.ArrayList;
import java.util.HashMap;

public class Casillero {
    private HashMap<String,ArrayList<Enemigo>> enemigosCasillero = new HashMap<>();
    private ArrayList<Enemigo> listaHumanos = new ArrayList<>();
    private ArrayList<Enemigo> listaHobbits = new ArrayList<>();
    private ArrayList<Enemigo> listaElfos = new ArrayList<>();
    private ArrayList<Enemigo> listaEnanos = new ArrayList<>();

    public Casillero() {
        // Agrego listas al HashMap de Casillero
        this.enemigosCasillero.put("Humano",listaHumanos);
        this.enemigosCasillero.put("Hobbit",listaHobbits);
        this.enemigosCasillero.put("Elfo",listaElfos);
        this.enemigosCasillero.put("Enano",listaEnanos);
    }

    public void agregarEnemigo(Enemigo enemigo) {
        // Agrego enemigo a cada lista dentro del hashmap Enemigos segun el tipo de enemigo
        if (enemigo instanceof Humano) {
            // Obtengo el arrayList asociado
            ArrayList<Enemigo> listaHumanos = enemigosCasillero.get("Humano");
            // Actualizo lista
            listaHumanos.add(enemigo);
        } else if (enemigo instanceof Hobbit) {
            ArrayList<Enemigo> listaHobbits = enemigosCasillero.get("Hobbit");
            listaHobbits.add(enemigo);
        } else if (enemigo instanceof Elfo) {
            ArrayList<Enemigo> listaElfos = enemigosCasillero.get("Elfo");
            listaElfos.add(enemigo);
        } else if (enemigo instanceof Enano) {
            ArrayList<Enemigo> listaEnanos = enemigosCasillero.get("Enano");
            listaEnanos.add(enemigo);
        }
    }

    public void mostrarEntidadesCasillero() {
        /// Muestra el HashMap
        System.out.println(this.enemigosCasillero);
    }
}
