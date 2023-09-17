import java.util.concurrent.atomic.AtomicInteger;

public class Enano extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
    public Enano() {
        this.id = contador.incrementAndGet();
        this.vida = 200;
        this.danio = 100;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 4; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.contadorMovimientosRestantes = 4;
        this.inmunidad = "None";
        this.recompensaEnemigo = 40;
    }

    public String toString() {
        return "En"+this.id;
    }
}
