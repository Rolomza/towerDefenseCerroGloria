package TowerDefenceCerro;

import java.util.Comparator;
/**
 * Clase que implementa un comparador de Casillero basado en los IDs de los casilleros.
 * Permite ordenar los casilleros en orden descendente según sus IDs.
 * @author  Augusto Robles
 * @version 1.0
 */

public class CasilleroIdComparator implements Comparator<Casillero> {
    /**
     * Compara dos casilleros en función de sus IDs en orden descendente.
     *
     * @param casillero1 El primer casillero a comparar.
     * @param casillero2 El segundo casillero a comparar.
     * @return Un valor negativo si casillero1 tiene un ID mayor que casillero2,
     *         un valor positivo si casillero1 tiene un ID menor que casillero2,
     *         o cero si ambos casilleros tienen el mismo ID.
     */
    @Override
    public int compare(Casillero casillero1, Casillero casillero2) {
        // Compara los IDs en orden descendente (de mayor a menor)
        return casillero2.getId() - casillero1.getId();
    }
}