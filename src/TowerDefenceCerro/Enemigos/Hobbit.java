package TowerDefenceCerro.Enemigos;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * Clase que representa un enemigo tipo "Hobbit" en el juego Tower Defense.
 * Los hobbits son enemigos de corto alcance y daño bajo, pero se mueven rápidamente.
 * @author  Aida Laricchia
 * @version 1.0
 */

public class Hobbit extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
    /**
     * Constructor que inicializa un nuevo hobbit con valores predeterminados.
     * Cada hobbit tiene un identificador único generado automáticamente.
     */
    public Hobbit() {
        this.id = contador.incrementAndGet();
        this.vida = 90;
        this.danio = 15;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 3; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.contadorMovimientosRestantes = 3;
        this.inmunidad = "None";
        this.recompensaEnemigo = 30;
    }
    /**
     * Devuelve una representación en cadena del hobbit, que incluye su identificador único.
     *
     * @return Una cadena que representa el hobbit.
     */
    public String toString() {
        return "Ho"+this.id;
    }
}
