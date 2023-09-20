import java.util.Comparator;

public class CasilleroIdComparator implements Comparator<Casillero> {
    @Override
    public int compare(Casillero casillero1, Casillero casillero2) {
        // Compara los IDs en orden descendente (de mayor a menor)
        return casillero2.getId() - casillero1.getId();
    }
}