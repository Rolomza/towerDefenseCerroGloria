package TowerDefenceCerro.Torres;

import TowerDefenceCerro.Coordenada;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * Clase que hereda de torre y representa una torre común en el juego Tower Defense.
 * @author  Victor Ramirez
 * @version 1.0
 */
public class TorreComun extends Torre{

    private static final AtomicInteger contador = new AtomicInteger(0);
    public TorreComun(Coordenada coordenadaTorre){
        this.id = contador.incrementAndGet();
        this.danio = 40;
        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
        this.costeTorre = 400;
    }
    /**
     * Constructor que crea una nueva instancia de TorreComun con valores predeterminados.
     * Se utiliza cuando se necesita una instancia de torre común sin ubicación específica.
     */
    public TorreComun(){
        this.danio = 40;
        this.alcanceAtaque = 1;
        this.costeTorre = 400;
    }

    /**
     * Obtiene una representación en cadena de la torre común.
     *
     * @return Una cadena que representa la torre común.
     */
    public String toString() {
        return "Tc"+this.id;
    }

}
