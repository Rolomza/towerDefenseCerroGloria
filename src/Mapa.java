import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class Mapa {
    // Listas de coordenadas de caminos de enemigos [CE1, CE2, CE3]
    private int[][] coordCaminoEnemigos = {{0,0},{1,1},{2,2},{3,3}};
    private Entidad[][] casillerosMapa = new Entidad[4][4];

    // Siendo por ej: CE1 = [(0,0),(0,1),(1,2)...]
    // Array de Arrays de Entidades

    public void mostrarMapa() {
        System.out.println("  A  B  C  D ");
        for (int i = 0; i < casillerosMapa.length; i++) {
            for (int j = 0; j < casillerosMapa[i].length; j++) {
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
