package TowerDefenceCerro;

import TowerDefenceCerro.Enemigos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase Casillero representa una ubicación en el mapa del juego Tower Defense.
 * Cada casillero tiene un identificador único, coordenadas en el mapa y puede contener enemigos,
 * una barrera o un Cerro de Gloria.
 *@author  Augusto Robles
 *@version 1.0
 */
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

    /**
     * Constructor para crear una instancia de Casillero con coordenadas especificadas.
     *
     * @param coordX La coordenada X en el mapa.
     * @param coordY La coordenada Y en el mapa.
     */
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
    /**
     * Constructor para crear una instancia de Casillero con coordenadas y un Cerro de Gloria especificados.
     *
     * @param coordX      La coordenada X en el mapa.
     * @param coordY      La coordenada Y en el mapa.
     * @param cerroGloria El Cerro de Gloria asociado al casillero.
     */
    public Casillero(int coordX , int coordY , Cerro cerroGloria){
        this.id =contador.incrementAndGet();
        this.coordenadaCasillero = new Coordenada(coordX,coordY);
        this.cerroGloria=cerroGloria;
    }
    /**
     * Agrega un enemigo al casillero en función de su tipo.
     *
     * @param enemigo El enemigo que se agregará al casillero.
     */
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
    /**
     * Elimina un enemigo del casillero.
     *
     * @param enemigo El enemigo que se eliminará del casillero.
     */
    public void eliminarEnemigo(Enemigo enemigo) {
            // ver si los enemigos son eliminados con esto.
            ArrayList<Enemigo> listaEnemigos = enemigosCasillero.get(enemigo.getClass().getName());
            listaEnemigos.remove(enemigo);
    }

    /**
     * Comprueba si el casillero contiene enemigos.
     *
     * @return true si el casillero contiene enemigos, false en caso contrario.
     */
    public boolean tieneEnemigos() {
        for (ArrayList<Enemigo> listaEnemigo : enemigosCasillero.values()) {
            if (!listaEnemigo.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el mapa de enemigos en el casillero.
     *
     * @return Un mapa que contiene listas de enemigos clasificadas por tipo.
     */
    public HashMap<String, ArrayList<Enemigo>> getEnemigosCasillero() {
        return enemigosCasillero;
    }

    /**
     * Muestra las entidades (enemigos) presentes en el casillero, incluyendo su ubicación y tipo.
     */
    public void mostrarEntidadesCasillero() {
        /// Muestra el HashMap
        String nombreCasillero = toString();
        System.out.print("El casillero "+nombreCasillero + " ubicado en "+this.coordenadaCasillero.mostrarCoordenada()+" , tiene --> ");
        mostrarListaEnemigos();
    }
    /**
     * Muestra las listas de enemigos presentes en el casillero, agrupadas por tipo.
     */
    public void mostrarListaEnemigos(){
        for (ArrayList<Enemigo> listaEnemigo : this.enemigosCasillero.values()){
            if (!listaEnemigo.isEmpty()){
                System.out.println(listaEnemigo);
            }
        }
    }
    /**
     * Reduce los contadores de iteraciones de todos los enemigos presentes en el casillero.
     */
    public void reducirContadores(){

        for (ArrayList<Enemigo> listaEnemigos : this.enemigosCasillero.values()){

            if (!listaEnemigos.isEmpty()){
                for (Enemigo enemigo : listaEnemigos){
                    enemigo.reducirContadorIteraciones();
                }
            }
        }

    }

    /**
     * Actualiza la lista de enemigos listos para moverse en función de sus contadores de movimientos restantes.
     * Los enemigos con contadores de movimientos restantes iguales o menores a cero y con vida no nula se consideran listos.
     */
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
    /**
     * Agrega una barrera al casillero, creando una nueva instancia de Barrera.
     */
    public void agregarBarrera(){
        this.barrera = new Barrera();
    }
    /**
     * Elimina la barrera del casillero, estableciendo su referencia a null.
     */
    public void eliminarBarrera(){
        this.barrera = null;}
    /**
     * Comprueba si el casillero tiene una barrera.
     *
     * @return true si el casillero tiene una barrera, false si no la tiene.
     */
    public boolean tieneBarrera(){
        return !(barrera == null);
    }
    /**
     * Obtiene la lista de enemigos listos para moverse en este casillero.
     *
     * @return La lista de enemigos listos para moverse.
     */
    public ArrayList<Enemigo> getEnemigosListosParaMoverse(){
        return this.enemigosListosParaMoverse;
    }
    /**
     * Obtiene una representación en cadena del casillero, incluyendo su identificador.
     *
     * @return Una cadena que representa el casillero en el formato "C{id}".
     */
    @Override
    public String toString() {
        return "C"+id;
    }
    /**
     * Obtiene el identificador único del casillero.
     *
     * @return El identificador único del casillero.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtiene el Cerro Gloria asociado a este casillero.
     *
     * @return El Cerro Gloria asociado al casillero, o null si no hay uno.
     */

    public Cerro getCerroGloria() {
        return cerroGloria;
    }

    /**
     * Obtiene la coordenada del casillero en el mapa.
     *
     * @return La coordenada del casillero.
     */

    public Coordenada getCoordenadaCasillero() {
        return this.coordenadaCasillero;
    }
    /**
     * Obtiene la barrera asociada a este casillero, si la tiene.
     *
     * @return La barrera asociada al casillero, o null si no hay una barrera en el casillero.
     */
    public Barrera getBarrera(){
        return this.barrera;
    }
}
