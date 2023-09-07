import java.util.ArrayList;

public class Mapa {
    // Listas de coordenadas de caminos de enemigos [CE1, CE2, CE3]
    private ArrayList<ArrayList<Coordenada>> caminosEnemigos = new ArrayList<>();
    private Casillero[][] casillerosMapa = new Casillero[3][3];

    // Generar casillero

    public void generarCasilleros() {
        // Generamos un mapa con 9 casilleros, cuyo camino de enemigos va en diagonal por el medio
        // Camino enemigo inicia en (0,0) y termina en el cerro (2,2)
        for (int i = 0; i < casillerosMapa.length; i++) {
            for (int j = 0; j < casillerosMapa[i].length; j++) {

                // Esta condicion debe depender de las listas de coordCaminos enemigos. Para asi determinar casilleros camino.
                if (i == j) {
                    casillerosMapa[i][j] = new Casillero(true);
                } else {
                    casillerosMapa[i][j] = new Casillero(false);
                }

            }
        }
    }

    public void generarCoordCamino(){
        ArrayList<Coordenada> camino1 = new ArrayList<>();
        camino1.add(new Coordenada(0, 0));
        camino1.add(new Coordenada(1, 1));
        camino1.add(new Coordenada(2, 2));
        caminosEnemigos.add(camino1);
    }



    public Casillero[][] getGetCasillerosMapa() {
        return this.casillerosMapa;
    }


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
