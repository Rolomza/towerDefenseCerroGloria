package TowerDefenceCerro.Enemigos;

import TowerDefenceCerro.*;

import java.util.ArrayList;
import TowerDefenceCerro.MomentosJuego.Nivel;
/**
 * Clase abstracta que representa a un enemigo en el juego Tower Defense.
 * @author  Aida Laricchia
 * @version 1.0
 */
public abstract class Enemigo implements IAtaque {
    protected int id; // Como lo generamos por cada tipo TowerDefenceCerro.Enemigos.Enemigo
    protected double vida;
    protected double danio; // cantidad de daño que realiza a barreras o TowerDefenceCerro.Cerro Gloria
    protected String inmunidad; // Puede ser hielo, fuego o ninguno, esto reducirá el ataque sufrido por la torre correspondiente
    protected int alcanceAtaque; // Define si un enemigo puede atacar mas alla de su propio casillero. (Borrar?)
    protected int velocidadDesplazamiento; // Determina cada cuantas iteraciones avanza un casillero
    protected int contadorMovimientosRestantes;
    protected boolean relentizado = false;

    protected int recompensaEnemigo; // Puntos de magia ganados al eliminar el enemigo
    protected ArrayList<Coordenada> camino = new ArrayList<>(); //lista de posiciones que debe recorrer cada enemigo por mapa


    /**
     *
     * @param nivelActual El nivel actual del juego.
     * @param casillero   El casillero en el que se encuentra el enemigo.
     */
@Override
    public void atacar(Nivel nivelActual, Casillero casillero) {
        // Si el enemigo tiene alcance != 1, debe poder iterar mas alla de su propio casillero para atacar.
        // Si hay barrera, el enemigo resta daño a barrera

        if (casillero.tieneBarrera()){
            casillero.getBarrera().restarVida(this.danio);
            System.out.println(this.toString() + " inflinge daño " + this.danio + " a " + casillero.getBarrera().toString());
        }
        if (casillero.getCerroGloria() != null){
            // Si hay cerro Gloria, el enemigo resta daño a TowerDefenceCerro.Cerro y desaparece.
            Cerro cerroGloria = casillero.getCerroGloria();
            cerroGloria.restarVida(this.getDanio());
            // Ver de borrar objeto enemigo cuando ataque TowerDefenceCerro.Cerro.
            System.out.println("El "+this.toString()+" inflinge "+this.getDanio()+" de daño al cerro gloria.");

        }
    }


    /**
     * Obtiene el identificador único del enemigo.
     *
     * @return El identificador único del enemigo.
     */
    public int getID() {
        return id;
    }

    /**
     * Obtiene el contador de movimientos restantes antes de que el enemigo se mueva.
     *
     * @return El contador de movimientos restantes.
     */
    public int getContadorMovimientosRestantes() {
        return this.contadorMovimientosRestantes;
    }

    /**
     * Reinicia el contador de iteraciones para el movimiento del enemigo.
     */
    public void reiniciarContadorIteraciones(){
        this.contadorMovimientosRestantes = this.velocidadDesplazamiento;
    }
/**
    *Este metodo reduce en 1 unidad el contador que determina despues de cuantas iteracion se debe mover cada enemigo
    * El contador depende de la velocidad de desplazamiento de cada enemigo
    */

    public void reducirContadorIteraciones(){
        this.contadorMovimientosRestantes--;
        if (this.contadorMovimientosRestantes == 0){
            this.relentizado = false;
        }
    }
    /**
     * Aumenta el contador de iteraciones restantes para el movimiento del enemigo en 1 unidad
     * cuando el enemigo está relentizado.
     */
    public void aumentarContadorIteraciones(){
        this.relentizado = true;
        this.contadorMovimientosRestantes++;
    }
    /**
     * Obtiene la cantidad de daño que el enemigo puede infligir.
     *
     * @return La cantidad de daño del enemigo.
     */
    public double getDanio() {
        return danio;
    }
    /**
     * Obtiene la recompensa en puntos de magia otorgada al eliminar al enemigo.
     *
     * @return La recompensa en puntos de magia.
     */
    public int getRecompensaEnemigo() {
        return recompensaEnemigo;
    }
    /**
     * Reduce los puntos de vida del enemigo en función del daño recibido.
     *
     * @param danio La cantidad de daño que el enemigo recibe.
     */
    public void restarVida(double danio){
        this.vida = this.vida - danio;
    }
    /**
     * Obtiene la cantidad de puntos de vida del enemigo.
     *
     * @return La cantidad de puntos de vida del enemigo.
     */
    public double getVida() {
        return vida;
    }

    public boolean isRelentizado() {
        return this.relentizado;
    }
}
