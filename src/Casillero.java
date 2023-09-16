import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Casillero {
    private static final AtomicInteger contador = new AtomicInteger(-1);
    private final int id;
    private HashMap<String,ArrayList<Enemigo>> enemigosCasillero = new HashMap<>();

    // Revisar modelado de barrera, si hacemos solo clase Barrera o creamos clase Estructura para Barreras y Cerro Gloria
    private Barrera barrera;
    private Cerro cerroGloria = null;
    private ArrayList<Enemigo> listaHumanos = new ArrayList<>();
    private ArrayList<Enemigo> listaHobbits = new ArrayList<>();
    private ArrayList<Enemigo> listaElfos = new ArrayList<>();
    private ArrayList<Enemigo> listaEnanos = new ArrayList<>();
    private ArrayList<Enemigo> enemigosListosParaMoverse = new ArrayList<>();
    private Coordenada coordenadaCasillero;
    public Casillero(int coordX,int coordY) {
        this.id = contador.incrementAndGet();
        // Agrego listas al HashMap de Casillero
        this.enemigosCasillero.put("Humano",listaHumanos);
        this.enemigosCasillero.put("Hobbit",listaHobbits);
        this.enemigosCasillero.put("Elfo",listaElfos);
        this.enemigosCasillero.put("Enano",listaEnanos);
        // Esta coordenada nos servira para colocar las barreras en el lugar indicado por el usuario
        this.coordenadaCasillero = new Coordenada(coordX,coordY);
    }
    public Casillero(int coordX , int coordY , Cerro cerroGloria){
        this.id =contador.incrementAndGet();
        this.coordenadaCasillero = new Coordenada(coordX,coordY);
        this.cerroGloria=cerroGloria;
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

    public void eliminarEnemigo(Enemigo enemigo) {
        if (enemigo instanceof Humano) {
            ArrayList<Enemigo> listaEnemigos = enemigosCasillero.get("Humano");
            listaEnemigos.remove(enemigo);
        } else if (enemigo instanceof Elfo) {
            ArrayList<Enemigo> listaEnemigos = enemigosCasillero.get("Elfo");
            listaEnemigos.remove(enemigo);
        }else if (enemigo instanceof Enano) {
            ArrayList<Enemigo> listaEnemigos = enemigosCasillero.get("Enano");
            listaEnemigos.remove(enemigo);
        }else {
            ArrayList<Enemigo> listaEnemigos = enemigosCasillero.get("Hobbit");
            listaEnemigos.remove(enemigo);
        }

    }


    public boolean tieneEnemigos() {
        for (ArrayList<Enemigo> listaEnemigo : enemigosCasillero.values()) {
            if (!listaEnemigo.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    public HashMap<String, ArrayList<Enemigo>> getEnemigosCasillero() {
        return enemigosCasillero;
    }

    public void mostrarEntidadesCasillero() {
        /// Muestra el HashMap
        String nombreCasillero = toString();
        System.out.println(nombreCasillero + this.enemigosCasillero);
    }

    public void reducirContadores(){

        for (ArrayList<Enemigo> listaEnemigos : this.enemigosCasillero.values()){

            if (!listaEnemigos.isEmpty()){
                for (Enemigo enemigo : listaEnemigos){
                    enemigo.reducirContadorIteraciones();
                }
            }
        }

    }


    public void setEnemigosListosParaMoverse(){
        for (ArrayList<Enemigo> listaEnemigos : this.enemigosCasillero.values()){
            if (!listaEnemigos.isEmpty()){
                for (Enemigo enemigo : listaEnemigos){
                    if (enemigo.getContadorMovimientosRestantes() == 0){
                        this.enemigosListosParaMoverse.add(enemigo);
                    }
                }
            }
        }
    }

    public void agregarBarrera(){
        this.barrera = new Barrera();
    }

    public boolean tieneBarrera(){
        return !(barrera == null);
    }

    public ArrayList<Enemigo> getEnemigosListosParaMoverse(){
        return this.enemigosListosParaMoverse;
    }

    @Override
    public String toString() {
        return "Casillero{" + "id=" + id + '}';
    }

    public int getId() {
        return id;
    }

    public void setCerroGloria(Cerro cerroGloria) {
        this.cerroGloria = cerroGloria;
    }

    public Cerro getCerroGloria() {
        return cerroGloria;
    }

    public Coordenada getCoordenadaCasillero() {
        return this.coordenadaCasillero;
    }
    public Barrera getBarrera(){
        return this.barrera;
    }
}
