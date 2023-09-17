import java.util.ArrayList;

public class Mapa {
    // Responsabilidad principal: Determinar referencias para generar coordenadas tanto para casilleros, torres y barreras.
    // Tambien para verificar a que casilleros alcanza cada torre y para mostrar por consola el desarrollo de las iteraciones
    protected ArrayList<Coordenada> caminosEnemigos = new ArrayList<>();
    protected String[][] mapaRefCoord = new String[5][5];


    public void generarCoordCaminoEnemigos() {
        //Objetivo: Crear aleatoriamente por nivel como nos dijo el profe

        // Validar que las coordenadas sean validas

        // Ahora probamos hardcodeando algunas coordenadas
        this.caminosEnemigos.add(new Coordenada(0, 0));
        this.caminosEnemigos.add(new Coordenada(0, 1));
        this.caminosEnemigos.add(new Coordenada(0, 2));


        this.caminosEnemigos.add(new Coordenada(1, 2));
        this.caminosEnemigos.add(new Coordenada(2, 2));
        this.caminosEnemigos.add(new Coordenada(3, 2));
        this.caminosEnemigos.add(new Coordenada(4, 2));

        this.caminosEnemigos.add(new Coordenada(4, 3));
        this.caminosEnemigos.add(new Coordenada(4, 4));


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

    public void colocarRefTorre(Coordenada coordenadaTorre , String tipoTorre){
        int posX = coordenadaTorre.getX();
        int posY = coordenadaTorre.getY();
        switch (tipoTorre) {
            case ("Comun") -> {
                mapaRefCoord[posX][posY] = "Tc";
            }
            case ("Hielo") -> {
                mapaRefCoord[posX][posY] = "Th";
            }
            case ("Fuego") -> {
                mapaRefCoord[posX][posY] = "Tf";
            }

        }
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
