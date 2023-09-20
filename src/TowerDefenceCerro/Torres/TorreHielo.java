package TowerDefenceCerro.Torres;

import TowerDefenceCerro.Casillero;
import TowerDefenceCerro.Coordenada;
import TowerDefenceCerro.Enemigos.Enemigo;
import TowerDefenceCerro.MomentosJuego.Nivel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Clase que hereda de torre y representa una torre de hielo en el juego Tower Defense.
 * @author  Victor Ramirez
 * @version 1.0
 */
public class TorreHielo extends Torre{

    private static final AtomicInteger contador = new AtomicInteger(0);
    /**
     * Constructor que crea una nueva instancia de TorreHielo en la ubicación especificada.
     *
     * @param coordenadaTorre La coordenada donde se coloca la torre en el mapa.
     */
    public TorreHielo(Coordenada coordenadaTorre){
        this.id = contador.incrementAndGet();
        this.danio = 15;
        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
        this.costeTorre = 500;
    }
    /**
     * Constructor que crea una nueva instancia de TorreHielo con valores predeterminados.
     * Se utiliza cuando se necesita una instancia de torre de hielo sin ubicación específica.
     */
    public TorreHielo(){
        this.danio = 25;
        this.alcanceAtaque = 1;
        this.costeTorre = 500;
    }
    /**
     * Realiza un ataque a los enemigos dentro del alcance de la torre de hielo.
     * Esta torre puede atacar a un máximo de 2 enemigos por casillero y ralentiza a los enemigos.
     *
     * @param nivelActual     El nivel actual del juego.
     * @param casilleroActual El casillero donde se encuentra la torre de hielo.
     */
    @Override
    public void atacar(Nivel nivelActual, Casillero casilleroActual) {
        // Esta flag sirve para que la torre ataque a 1 casillero solamente donde detecte enemigos.
        int enemigosAtacados = 0;

        for (Casillero casilleroAtaque : this.casillerosAtaque) {

            listaAtaqueEnemigos = prioridadEnemigo(casilleroAtaque);

            for (Enemigo enemigoActual : listaAtaqueEnemigos){
                enemigoActual.restarVida(this.danio);

                // Sobreescribo el metodo de TowerDefenceCerro.Torres.Torre para agregar relentizado y atacar solo 2 por casillero.

                if (!enemigoActual.isRelentizado()){
                    enemigoActual.aumentarContadorIteraciones();
                }


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
                enemigosAtacados++; // Activar la bandera de ataque

            }
            if (enemigosAtacados > 1) {
                break;
            }
        }
    }
    /**
     * Calcula la prioridad de ataque de la torre de hielo.
     * Prioriza a los enemigos por velocidad de movimiento.
     *
     * @param casilleroEnemigo El casillero que contiene a los enemigos.
     * @return Una lista de enemigos ordenados por prioridad de ataque.
     */
    @Override
    public ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo) {
        ArrayList<Enemigo> listaEnemigosPorPrioridad = new ArrayList<>();
        String[] tiposEnemigos = {"TowerDefenceCerro.Enemigos.Elfo", "TowerDefenceCerro.Enemigos.Humano", "TowerDefenceCerro.Enemigos.Hobbit", "TowerDefenceCerro.Enemigos.Enano"}; // Prioriza por velocidad

        for (String tipo : tiposEnemigos) {
            ArrayList<Enemigo> listaEnemigos = casilleroEnemigo.getEnemigosCasillero().get(tipo);
            if (!listaEnemigos.isEmpty()) {
                for (Enemigo enemigo : listaEnemigos) {
                    listaEnemigosPorPrioridad.add(enemigo);
                    if (listaEnemigosPorPrioridad.size() >= 2) {
                        return listaEnemigosPorPrioridad;
                    }
                }
            }
        }

        return listaEnemigosPorPrioridad;
    }

    /**
     * Obtiene una representación en cadena de la torre de hielo.
     *
     * @return Una cadena que representa la torre de hielo.
     */
    public String toString() {
        return "Th"+this.id;
    }

}
