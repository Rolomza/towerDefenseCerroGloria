import java.util.concurrent.atomic.AtomicInteger;

public class Elfo extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
    public Elfo() {
        this.id = contador.incrementAndGet();
        this.vida = 75;
        this.danio = 175;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 1; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.contadorMovimientosRestantes = 1;
        this.inmunidad = "None";
        this.recompensaEnemigo = 30;
    }

    public String toString() {
        return "Elf"+this.id;
    }

}
