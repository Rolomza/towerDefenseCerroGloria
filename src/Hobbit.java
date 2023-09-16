import java.util.concurrent.atomic.AtomicInteger;

public class Hobbit extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
    public Hobbit() {
        this.id = contador.incrementAndGet();
        this.vida = 50;
        this.danio = 50;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 3; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.contadorMovimientosRestantes = 3;
        this.inmunidad = "None";
        this.recompensaEnemigo = 30;
    }

    public String toString() {
        return "Ho"+this.id;
    }
}
