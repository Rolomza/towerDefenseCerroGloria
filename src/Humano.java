public class Humano extends Enemigo {
    public Humano() {
        this.vida = 100;
        this.daño = 50;
        this.alcanceAtaque = 1;
        this.velocidadDesplazamiento = 1; // Por ejemplo si un ciclo de juego son 3 iteraciones, el humano se mueve 1 casillero por ciclo.
        this.inmunidad = "None";
        this.recompensaEnemigo = 30;
    }
}
