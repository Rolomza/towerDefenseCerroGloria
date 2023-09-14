import java.util.ArrayList;

public class Mapa {
    // Responsabilidad principal: Determinar referencias para generar coordenadas tanto para casilleros, torres y barreras.
    // Tambien para verificar a que casilleros alcanza cada torre y para mostrar por consola el desarrollo de las iteraciones
    protected ArrayList<Coordenada> caminosEnemigos = new ArrayList<>();
    protected String[][] mapaRefCoord = new String[3][3];


    public void generarCoordCaminoEnemigos() {
        //Objetivo: Crear aleatoriamente por nivel como nos dijo el profe

        // Validar que las coordenadas sean validas

        // Ahora probamos hardcodeando algunas coordenadas
        this.caminosEnemigos.add(new Coordenada(0, 0));
        this.caminosEnemigos.add(new Coordenada(0, 1));
        this.caminosEnemigos.add(new Coordenada(1, 1));
        this.caminosEnemigos.add(new Coordenada(2, 1));
        this.caminosEnemigos.add(new Coordenada(2, 2));

    }

    public void colocarReferenciasEnMapa() {

        for (Coordenada coordenada : this.caminosEnemigos) {
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

    public void colocarRefTorre(Coordenada coordenadaTorre){
        int posX = coordenadaTorre.getX();
        int posY = coordenadaTorre.getY();
        mapaRefCoord[posX][posY] = "T";
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

    public ArrayList<Coordenada> getCaminosEnemigos() {
        return caminosEnemigos;
    }

    public String[][] getMapaRefCoord() { return mapaRefCoord; }
}
