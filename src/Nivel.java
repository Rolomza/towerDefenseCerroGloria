import java.util.ArrayList;

public class Nivel {
    private ArrayList<Coordenada> caminosEnemigos = new ArrayList<>();
    private ArrayList<Casillero> casillerosEnemigos = new ArrayList<>();
    private Mapa mapaNivel = new Mapa();

    public Nivel() {}

    public void generarCoordCaminoEnemigos() {
        //Objetivo: Crear aleatoriamente por nivel como nos dijo el profe

        // Validar que las coordenadas sean validas

        // Ahora probamos hardcodeando algunas coordenadas
        this.caminosEnemigos.add(new Coordenada(0, 0));
        this.caminosEnemigos.add(new Coordenada(0, 1));
        this.caminosEnemigos.add(new Coordenada(1, 1));
        this.caminosEnemigos.add(new Coordenada(2, 1));
        this.caminosEnemigos.add(new Coordenada(2, 2));

        mapaNivel.generarCoordMapaRef(this.caminosEnemigos);

        generarCasillerosEnemigos();

    }

    public void generarCasillerosEnemigos() {
        for (Coordenada coordenada : caminosEnemigos) {
            int x = coordenada.getX();
            int y = coordenada.getY();
            casillerosEnemigos.add(new Casillero());
        }
    }

    public void mostrarMapaNivel() {
        mapaNivel.mostrarMapa();
    }
}
