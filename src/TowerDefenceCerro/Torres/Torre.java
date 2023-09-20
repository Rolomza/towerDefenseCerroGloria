package TowerDefenceCerro.Torres;

import TowerDefenceCerro.*;
import TowerDefenceCerro.Enemigos.Enemigo;
import TowerDefenceCerro.MomentosJuego.Nivel;
import java.util.ArrayList;
import java.util.Collections;
/**
 * La clase abstracta "Torre" representa una torre en el juego "Cerro Gloria Tower Defence".
 * Las torres son estructuras defensivas que pueden atacar a los enemigos que se acercan al Cerro Gloria.
 * Cada torre tiene un costo, daño, velocidad de ataque, alcance, nivel, identificador y coordenadas en el mapa.
 * También mantiene una lista de coordenadas de casilleros donde puede atacar y una lista de enemigos a los que está atacando.
 *
 * Las clases concretas que heredan de esta clase deben implementar el método "prioridadEnemigo" para determinar
 * a qué enemigos debe atacar la torre.
 * @author  Victor Ramirez
 * @version 1.0
 */
public abstract class Torre implements IAtaque {
    protected int costeTorre; //cantidad de puntos de magia que necesitaremos para comprar la torre
    protected  double danio;
    protected int velocidadAtaque;
    protected int alcanceAtaque;
    protected int nivelTorre;
    protected int id;
    protected Coordenada coordenadaTorre;

    protected ArrayList<Coordenada> coordCasillerosAtaque = new ArrayList<>();
    //Antiguo casilleros ataque:
//    protected ArrayList<TowerDefenceCerro.Coordenada> casillerosAtaque = new ArrayList<>();
    protected ArrayList<Casillero> casillerosAtaque = new ArrayList<>();
    protected ArrayList<Enemigo> listaAtaqueEnemigos = new ArrayList<>(); // Esta lista contiene a los enemigos a los que esta atacando la torre



//    public abstract ArrayList<TowerDefenceCerro.Enemigos.Enemigo> prioridadEnemigo(TowerDefenceCerro.Casillero casilleroEnemigo);
    /**
     * Realiza el proceso de ataque de la torre a los enemigos en los casilleros de ataque.
     * Si un enemigo es derrotado, se otorgan puntos de magia al jugador.
     *
     * @param nivelActual El nivel actual del juego.
     * @param casilleroActual El casillero actual donde se encuentra la torre.
     */
    public void atacar(Nivel nivelActual, Casillero casilleroActual) {
        // Esta flag sirve para que la torre ataque a 1 casillero solamente donde detecte enemigos.
        boolean haAtacado = false;

        for (Casillero casilleroAtaque : this.casillerosAtaque) {
            listaAtaqueEnemigos = prioridadEnemigo(casilleroAtaque);

            for (Enemigo enemigoActual : listaAtaqueEnemigos){
                enemigoActual.restarVida(this.danio);
                if (enemigoActual.getVida() <= 0){
                    // Si la torre mata al enemigo, sucede lo siguiente:
                    casilleroAtaque.eliminarEnemigo(enemigoActual);
                    nivelActual.aumentarPuntosMagia(enemigoActual.getRecompensaEnemigo());
                    casilleroAtaque.getEnemigosListosParaMoverse().remove(enemigoActual);
                    System.out.println("La torre " + this.toString() + " ha asesinado a " + enemigoActual.toString() + " | +" + enemigoActual.getRecompensaEnemigo() + " Ptos. Magia.");
                } else {
                    // Si la torre no mata al enemigo muestra el siguiente mensaje:
                    System.out.println(enemigoActual.toString() + " fue atacado por "+this.toString()+" y ahora tiene "+enemigoActual.getVida()+" de vida");
                }
                haAtacado = true; // Activar la bandera de ataque
                break;
            }
        }
    }

    /**
     * Determina la prioridad de ataque de la torre a los enemigos en un casillero de ataque.
     * Las clases concretas que heredan de "Torre" deben implementar este método para definir
     * su lógica de ataque específica.
     *
     * @param casilleroEnemigo El casillero donde se encuentran los enemigos.
     * @return Una lista de enemigos en orden de prioridad de ataque.
     */
    public ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo){
        // Dependiendo de la torre es la cantidad de enemigos que devuelve y a quien focusea

        ArrayList<Enemigo> listaEnemigosPorPrioridad = new ArrayList<>();
        //Este caso es para la torre comun, que focusea primero a los humanos
        for (ArrayList<Enemigo> listaEnemigos : casilleroEnemigo.getEnemigosCasillero().values()){
            if (!listaEnemigos.isEmpty()){
                listaEnemigosPorPrioridad.add(listaEnemigos.get(0));
            }
        }
        return listaEnemigosPorPrioridad;
    }


    // De acuerdo al alcanze de la torre y su posicion en el mapa, calcula a que coordenadas del mapa puede pegarle.
    // Devuelve coordenadas de ataque para cada torre. (construye la lista coordCasilleroAtaque)
    /**
     * Calcula las coordenadas de los casilleros que se encuentran dentro del alcance de ataque de la torre.
     * Estas coordenadas se almacenan en la lista "coordCasillerosAtaque".
     *
     * @param nivelActual El nivel actual del juego.
     */
    public void calcularCoordenadasCasillerosAtaque(Nivel nivelActual){

        String [][] arrayMapa = nivelActual.getMapaNivel().getMapaRefCoord();

        int posX = coordenadaTorre.getX();
        int posY = coordenadaTorre.getY();

        int newPosX = posX - this.alcanceAtaque;
        int newPosY = posY - this.alcanceAtaque;


        for (int i = newPosX; i <= posX + this.alcanceAtaque; i++) {
            for (int j = newPosY; j <= posY + this.alcanceAtaque; j++) {
                // Verificar que i y j estén dentro de los límites del mapa
                if (i >= 0 && i < arrayMapa.length && j >= 0 && j < arrayMapa[0].length) {
                    // Verificar si el casillero es diferente de la posición de la torre

                    if ((!arrayMapa[i][j].contains("C") && !arrayMapa[i][j].contains("E")) || (i == posX && j == posY)) {
                        continue;
                    }
                    // Agregar la coordenada a casillerosAtaque
                    this.coordCasillerosAtaque.add(0,new Coordenada(i, j));
                }
            }
        }

        agregarCasillerosAtaquePorCoordenada(nivelActual, this.coordCasillerosAtaque);
        Collections.sort(this.casillerosAtaque, new CasilleroIdComparator());
    }


    /** Segun la lista de coordCasillerosAtaque construye una lista de Casilleros a los cuales la TowerDefenceCerro.Torres.Torre puede atacar
    * Construye (casillerosAtaque)
     */
    public void agregarCasillerosAtaquePorCoordenada(Nivel nivelActual , ArrayList<Coordenada> coordCasillerosAtaque) {
        for (Coordenada coordCasillero : coordCasillerosAtaque) {
            this.casillerosAtaque.add(nivelActual.buscarCasilleroPorCoordenada(coordCasillero));
        }
    }
    /**
     * Obtiene el costo de la torre en puntos de magia.
     *
     * @return El costo de la torre.
     */
    public int getCosteTorre() {
        return costeTorre;
    }
    /**
     * Obtiene el daño de ataque de la torre.
     *
     * @return El daño de ataque de la torre.
     */
    public double getDanio() {
        return danio;
    }
    /**
     * Obtiene el alcance de ataque de la torre.
     *
     * @return El alcance de ataque de la torre.
     */
    public int getAlcanceAtaque() {
        return alcanceAtaque;
    }
    /**
     * Aumenta el daño de ataque de la torre en la cantidad especificada.
     *
     * @param aumentoDanio La cantidad de aumento de daño.
     */
    public void aumentarDanio(int aumentoDanio) {
        this.danio += aumentoDanio;
    }
    /**
     * Aumenta el alcance de ataque de la torre en una unidad.
     */
    public void aumentarAlcance() {
        this.alcanceAtaque++;
    }
    /**
     * Obtiene las coordenadas de la torre en el mapa.
     *
     * @return Las coordenadas de la torre.
     */
    public Coordenada getCoordenadaTorre() {
        return coordenadaTorre;
    }
}
