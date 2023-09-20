package TowerDefenceCerro;
import TowerDefenceCerro.MomentosJuego.Nivel;

/**
 * Una interfaz que define el comportamiento de ataque para entidades que pueden atacar casilleros en un juego.
 * @author  Aida Laricchia
 * @version 1.0
 */
public interface IAtaque {
    /**
     * Realiza un ataque a un casillero específico en el nivel actual del juego.
     *
     * @param nivelActual El nivel del juego en el que se realiza el ataque.
     * @param casillero El casillero que se va a atacar.
     */
    void atacar(Nivel nivelActual, Casillero casillero);
}

