package TowerDefenceCerro.Enemigos;

import java.util.concurrent.atomic.AtomicInteger;

public class Humano extends Enemigo {
    private static final AtomicInteger contador = new AtomicInteger(0);
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

    public String toString() {
        return "Hu"+this.id;
    }

    // Este metodo se va a llamar cuando se cambie de casillero

    public String getNameClass() {
        return "TowerDefenceCerro.Enemigos.Humano";
    }
}
