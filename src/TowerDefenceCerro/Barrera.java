package TowerDefenceCerro;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * La clase Barrera representa una barrera en el juego Tower Defense.
 * Cada barrera tiene un identificador único, vida y precio.
 * @author  Aida Laricchia
 * @version 1.0
 */
public class Barrera {
    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private double vida;
    private int precioBarrera;
    /**
     * Constructor para crear una nueva instancia de Barrera.
     * Inicializa el identificador único, la vida y el precio de la barrera.
     */
    public Barrera() {
        this.id = contador.incrementAndGet();
        this.vida = 650;
        this.precioBarrera = 100;
    }
    /**
     * Devuelve una representación en cadena de la barrera, incluyendo su identificador único.
     *
     * @return Representación en cadena de la barrera.
     */
    public String toString() {
        return "Barr"+this.id;
    }
    /**
     * Obtiene el precio de la barrera.
     *
     * @return El precio de la barrera en puntos de magia.
     */

    public int getPrecioBarrera() {
        return precioBarrera;
    }
    /**
     * Obtiene la cantidad de vida actual de la barrera.
     *
     * @return La cantidad de vida de la barrera.
     */
    public double getVida() {
        return vida;
    }
    /**
     * Resta la cantidad de daño especificada a la vida de la barrera.
     *
     * @param danio La cantidad de daño que se resta a la vida de la barrera.
     */
    public void restarVida(double danio){
        this.vida=this.vida -danio;
    }
}
