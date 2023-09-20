package TowerDefenceCerro.Enemigos;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que representa un enemigo tipo "Elfo" en el juego Tower Defense.
 * Los elfos son enemigos de largo alcance y daño alto.
 * @author  Aida Laricchia
 * @version 1.0
 */
public class Elfo extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
    /**
     * Constructor que inicializa un nuevo elfo con valores predeterminados.
     * Cada elfo tiene un identificador único generado automáticamente.
     */
    public Elfo() {
        this.id = contador.incrementAndGet();
        this.vida = 120;
        this.danio = 200;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 1; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.contadorMovimientosRestantes = 1;
        this.inmunidad = "None";
        this.recompensaEnemigo = 45;
    }
    /**
     * Devuelve una representación en cadena del elfo, que incluye su identificador único.
     *
     * @return Una cadena que representa el elfo.
     */
    public String toString() {
        return "Elf"+this.id;
    }

}
