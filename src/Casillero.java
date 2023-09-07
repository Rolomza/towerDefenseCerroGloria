import java.util.HashMap;

public class Casillero {
    private boolean esCamino;
    private HashMap<String,Entidad> entidadesCasillero = new HashMap<>();

    public Casillero(boolean esCamino) {
        this.esCamino = esCamino;
    }


    public void agregarEnemigo(Enemigo enemigo) {
        if(enemigo.getClass()) {
            case "Humano": entidadesCasillero.put("H", enemigo);
        }

    }
    public void mostrarCasillero() {
        // Para posible print de la info de cada casillero
    }
}
