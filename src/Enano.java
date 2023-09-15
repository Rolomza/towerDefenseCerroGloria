import java.util.concurrent.atomic.AtomicInteger;

public class Enano extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
    public Enano() {
        this.id = contador.incrementAndGet();
        this.vida = 200;
        this.daño = 50;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 2; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.inmunidad = "None";
        this.recompensaEnemigo = 30;
    }

    public String toString() {
        return "En"+this.id;
    }
}
