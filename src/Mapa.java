import java.util.ArrayList;

public class Mapa {
    // El objeto mapa generado tendra en su estado un array de String para verificar al posicionar torres
    // Tambien para verificar a que casilleros alcanza cada torre y para mostrar por consola el desarrollo de las iteraciones
    protected ArrayList<Coordenada> caminosEnemigos;
    protected String[][] mapaRefCoord = new String[3][3];


    public void generarCoordMapaRef(ArrayList<Coordenada> coordCaminoEnemigoNivel) {

        for (Coordenada coordenada : coordCaminoEnemigoNivel) {
            int x = coordenada.getX();
            int y = coordenada.getY();
            mapaRefCoord[x][y] = "C";
        }

        for (int i = 0; i < mapaRefCoord.length; i++) {
            for (int j = 0; j < mapaRefCoord[i].length; j++) {
                if (mapaRefCoord[i][j] == null) {
                    mapaRefCoord[i][j] = "X";
                }
            }
        }
    }
    public void generarCasillerosEnemigos() {
        System.out.println(" ");
    }


    public void mostrarMapa() {
        System.out.println("  A  B  C  ");
        for (int i = 0; i < mapaRefCoord.length; i++) {
            for (int j = 0; j < mapaRefCoord[i].length; j++) {
                if (j == 0) {
                    System.out.print(i+1);
                }
                System.out.print(" " + mapaRefCoord[i][j] + " ");
            }
            System.out.println();
        }
    }

}
