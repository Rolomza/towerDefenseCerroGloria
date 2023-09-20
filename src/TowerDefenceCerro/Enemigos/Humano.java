package TowerDefenceCerro.Enemigos;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * Clase que representa un enemigo tipo "Humano" en el juego Tower Defense.
 * Los humanos son enemigos de daño moderado y velocidad de movimiento intermedia.
 * @author  Aida Laricchia
 * @version 1.0
 */
public class Humano extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
    /**
     * Constructor que inicializa un nuevo humano con valores predeterminados.
     * Cada humano tiene un identificador único generado automáticamente.
     */
    public Humano() {
        this.id = contador.incrementAndGet();
        this.vida = 170;
        this.danio = 35;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 2; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.contadorMovimientosRestantes = 2;
        this.inmunidad = "None";
        this.recompensaEnemigo = 40;
    }
    /**
     * Devuelve una representación en cadena del humano, que incluye su identificador único.
     *
     * @return Una cadena que representa el humano.
     */
    public String toString() {
        return "Hu"+this.id;
    }

}
