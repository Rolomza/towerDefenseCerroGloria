package TowerDefenceCerro;

import TowerDefenceCerro.Enemigos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Casillero {
    private static final AtomicInteger contador = new AtomicInteger(-1);
    private final int id;
    private HashMap<String,ArrayList<Enemigo>> enemigosCasillero = new HashMap<>();

    // Revisar modelado de barrera, si hacemos solo clase TowerDefenceCerro.Barrera o creamos clase Estructura para Barreras y TowerDefenceCerro.Cerro Gloria
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
        // Agrego listas al HashMap de TowerDefenceCerro.Casillero
        this.enemigosCasillero.put("TowerDefenceCerro.Enemigos.Humano",listaHumanos);
        this.enemigosCasillero.put("TowerDefenceCerro.Enemigos.Hobbit",listaHobbits);
        this.enemigosCasillero.put("TowerDefenceCerro.Enemigos.Elfo",listaElfos);
        this.enemigosCasillero.put("TowerDefenceCerro.Enemigos.Enano",listaEnanos);
        // Esta coordenada nos servira para colocar las barreras en el lugar indicado por el usuario
        this.coordenadaCasillero = new Coordenada(coordX,coordY);
    }
    public Casillero(int coordX , int coordY , Cerro cerroGloria){
        this.id =contador.incrementAndGet();
        this.coordenadaCasillero = new Coordenada(coordX,coordY);
        this.cerroGloria=cerroGloria;
    }

    public void agregarEnemigo(Enemigo enemigo) {
        // Agrego enemigo a cada lista dentro del hashmap TowerDefenceCerro.Enemigos segun el tipo de enemigo
        if (enemigo instanceof Humano) {
            // Obtengo el arrayList asociado
            ArrayList<Enemigo> listaHumanos = enemigosCasillero.get("TowerDefenceCerro.Enemigos.Humano");
            // Actualizo lista
            listaHumanos.add(enemigo);
        } else if (enemigo instanceof Hobbit) {
            ArrayList<Enemigo> listaHobbits = enemigosCasillero.get("TowerDefenceCerro.Enemigos.Hobbit");
            listaHobbits.add(enemigo);
        } else if (enemigo instanceof Elfo) {
            ArrayList<Enemigo> listaElfos = enemigosCasillero.get("TowerDefenceCerro.Enemigos.Elfo");
            listaElfos.add(enemigo);
        } else if (enemigo instanceof Enano) {
            ArrayList<Enemigo> listaEnanos = enemigosCasillero.get("TowerDefenceCerro.Enemigos.Enano");
            listaEnanos.add(enemigo);
        }
    }

    public void eliminarEnemigo(Enemigo enemigo) {


            // ver si los enemigos son eliminados con esto.
            ArrayList<Enemigo> listaEnemigos = enemigosCasillero.get(enemigo.getClass().getName());
            listaEnemigos.remove(enemigo);

//        if (enemigo instanceof TowerDefenceCerro.Enemigos.Humano) {
//            ArrayList<TowerDefenceCerro.Enemigos.Enemigo> listaEnemigos = enemigosCasillero.get("TowerDefenceCerro.Enemigos.Humano");
//            listaEnemigos.remove(enemigo);
//        } else if (enemigo instanceof TowerDefenceCerro.Enemigos.Elfo) {
//            ArrayList<TowerDefenceCerro.Enemigos.Enemigo> listaEnemigos = enemigosCasillero.get("TowerDefenceCerro.Enemigos.Elfo");
//            listaEnemigos.remove(enemigo);
//        }else if (enemigo instanceof TowerDefenceCerro.Enemigos.Enano) {
//            ArrayList<TowerDefenceCerro.Enemigos.Enemigo> listaEnemigos = enemigosCasillero.get("TowerDefenceCerro.Enemigos.Enano");
//            listaEnemigos.remove(enemigo);
//        }else {
//            ArrayList<TowerDefenceCerro.Enemigos.Enemigo> listaEnemigos = enemigosCasillero.get("TowerDefenceCerro.Enemigos.Hobbit");
//            listaEnemigos.remove(enemigo);
//        }

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
                    if (enemigo.getContadorMovimientosRestantes() <= 0){
                        // Chequeo que el enemigo no este muerto y no este en la lista
                        if (enemigo.getVida() > 0 && !this.enemigosListosParaMoverse.contains(enemigo)){
                            this.enemigosListosParaMoverse.add(enemigo);
                        }
                    }
                }
            }
        }
    }

    public void agregarBarrera(){
        this.barrera = new Barrera();
    }
    public void eliminarBarrera(){
        this.barrera = null;}

    public boolean tieneBarrera(){
        return !(barrera == null);
    }

    public ArrayList<Enemigo> getEnemigosListosParaMoverse(){
        return this.enemigosListosParaMoverse;
    }

    @Override
    public String toString() {
        return "C"+id;
    }

    public int getId() {
        return this.id;
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
