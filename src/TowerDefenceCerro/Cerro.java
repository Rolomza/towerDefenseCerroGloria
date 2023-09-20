package TowerDefenceCerro;

/**
 * Clase que representa el Cerro Gloria en el juego Tower Defense.
 * El Cerro Gloria tiene una cantidad de vida que puede reducirse por el daño recibido.
 * @author  Aida Laricchia
 * @version 1.0
 */

public class Cerro {
    private double vida = 1500;
    /**
     * Obtiene la cantidad actual de vida del Cerro Gloria.
     *
     * @return La cantidad de vida actual del Cerro Gloria.
     */
    public double getVida() {
        return vida;
    }

    /**
     * Reduce la vida del Cerro Gloria en la cantidad especificada por el parámetro de daño.
     *
     * @param danio La cantidad de daño que se resta a la vida del Cerro Gloria.
     */
    public void restarVida(double danio) {
        this.vida = vida - danio;
    }

    /**
     * Muestra por consola la vida actual del Cerro Gloria.
     */
    public void mostrarVida(){
        System.out.println("La vida actual del Cerro Gloria es: "+vida);
    }
}
