import java.util.ArrayList;

public class Mapa {
    // Listas de coordenadas de caminos de enemigos [CE1, CE2, CE3]
    protected ArrayList<Coordenada> caminosEnemigos = new ArrayList<>();
    protected Casillero[][] mapa = new Casillero[3][3];

    // Generar casillero



    public void mostrarMapa() {
        System.out.println("  A  B  C  D ");
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (j == 0) {
                    System.out.print(i+1);
                }

                if (i==j) {
                    System.out.print(" C ");
                } else {
                    System.out.print(" X ");
                }
            }
            System.out.println();
        }
    }


}
