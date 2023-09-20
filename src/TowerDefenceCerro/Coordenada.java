package TowerDefenceCerro;
/**
 * Clase que representa una coordenada en un sistema de coordenadas bidimensional.
 * Cada coordenada tiene una posición en el eje X y en el eje Y.
 * @author  Victor Ramirez
 * @version 1.0
 */
public class Coordenada {
    private int x;
    private int y;

    /**
     * Constructor de la clase Coordenada que permite crear una instancia con valores iniciales para X e Y.
     *
     * @param x La coordenada en el eje X.
     * @param y La coordenada en el eje Y.
     */
    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Establece el valor de la coordenada en el eje Y.
     *
     * @param y El nuevo valor de la coordenada en el eje Y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Obtiene el valor de la coordenada en el eje X.
     *
     * @return El valor de la coordenada en el eje X.
     */
    public int getX() {
        return x;
    }
    /**
     * Obtiene el valor de la coordenada en el eje Y.
     *
     * @return El valor de la coordenada en el eje Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Retorna una representación legible de la coordenada en formato (X, Y).
     *
     * @return Una cadena que representa la coordenada en formato (X, Y).
     */
    public String mostrarCoordenada() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compara la coordenada actual con otra coordenada para determinar si son iguales.
     *
     * @param coordenada2 La segunda coordenada a comparar.
     * @return true si las coordenadas son iguales (tienen los mismos valores de X e Y), false en caso contrario.
     */
    public boolean compararConCoordenada(Coordenada coordenada2){
        return this.getX() == coordenada2.getX() && this.getY() == coordenada2.getY();
    }


}