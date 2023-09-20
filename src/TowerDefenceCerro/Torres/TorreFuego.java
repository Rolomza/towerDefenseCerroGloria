package TowerDefenceCerro.Torres;

import TowerDefenceCerro.Casillero;
import TowerDefenceCerro.Coordenada;
import TowerDefenceCerro.Enemigos.Enemigo;
import TowerDefenceCerro.MomentosJuego.Nivel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Clase que hereda de torre y representa una torre de fuego en el juego Tower Defense.
 * @author  Victor Ramirez
 * @version 1.0
 */
public class TorreFuego extends Torre{

    private static final AtomicInteger contador = new AtomicInteger(0);

    /**
     * Constructor que crea una nueva instancia de TorreFuego en la ubicación especificada.
     *
     * @param coordenadaTorre La coordenada donde se coloca la torre en el mapa.
     */
    public TorreFuego(Coordenada coordenadaTorre){
        this.id = contador.incrementAndGet();
        this.danio = 50; // Mata a los elfos de 2 tiros
        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
        this.costeTorre = 1000;
    }
    /**
     * Constructor que crea una nueva instancia de TorreFuego con valores predeterminados.
     * Se utiliza cuando se necesita una instancia de torre de fuego sin ubicación específica.
     */
    public TorreFuego(){
        this.danio = 50;
        this.alcanceAtaque = 1;
        this.costeTorre = 1000;
    }


    /**
     * Realiza un ataque a los enemigos dentro del alcance de la torre de fuego.
     *
     * @param nivelActual     El nivel actual del juego.
     * @param casilleroActual El casillero donde se encuentra la torre de fuego.
     */
    public void atacar(Nivel nivelActual, Casillero casilleroActual) {
        // TowerDefenceCerro.Torres.Torre fuego ataca a todos los enemigos que ve en cada casillero
        for (Casillero casilleroAtaque : this.casillerosAtaque) {
            //System.out.println("Estoy revisando para atacar a " + casilleroAtaque.toString());
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

            }
        }
    }

    /**
     * Calcula la prioridad de ataque de la torre de fuego.
     * Prioriza a los enemigos por tipo de daño.
     *
     * @param casilleroEnemigo El casillero que contiene a los enemigos.
     * @return Una lista de enemigos ordenados por prioridad de ataque.
     */
    @Override
    public ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo) {
        ArrayList<Enemigo> listaEnemigosPorPrioridad = new ArrayList<>();
        String[] tiposEnemigos = {"TowerDefenceCerro.Enemigos.Elfo", "TowerDefenceCerro.Enemigos.Enano", "TowerDefenceCerro.Enemigos.Humano", "TowerDefenceCerro.Enemigos.Hobbit"}; // Prioriza por daño de enemigo

        for (String tipo : tiposEnemigos) {
            ArrayList<Enemigo> listaEnemigos = casilleroEnemigo.getEnemigosCasillero().get(tipo);
            if (!listaEnemigos.isEmpty()) {
                for (Enemigo enemigo : listaEnemigos) {
                    listaEnemigosPorPrioridad.add(enemigo);
                    return listaEnemigosPorPrioridad;

                }
            }
        }
        return listaEnemigosPorPrioridad;
    }
    /**
     * Obtiene una representación en cadena de la torre de fuego.
     *
     * @return Una cadena que representa la torre de fuego.
     */
    public String toString() {
        return "Tf"+this.id;
    }


}
