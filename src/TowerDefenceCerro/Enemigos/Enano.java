package TowerDefenceCerro.Enemigos;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que representa un enemigo tipo "Enano" en el juego Tower Defense.
 * Los enanos son enemigos de corto alcance y alta resistencia.
 * @author  Aida Laricchia
 * @version 1.0
 */
public class Enano extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
    /**
     * Constructor que inicializa un nuevo enano con valores predeterminados.
     * Cada enano tiene un identificador único generado automáticamente.
     */
    public Enano() {
        this.id = contador.incrementAndGet();
        this.vida = 400;
        this.danio = 120;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 3; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.contadorMovimientosRestantes = 3;
        this.inmunidad = "None";
        this.recompensaEnemigo = 75;
    }
    /**
     * Devuelve una representación en cadena del enano, que incluye su identificador único.
     *
     * @return Una cadena que representa el enano.
     */
    public String toString() {
        return "En"+this.id;
    }
}
