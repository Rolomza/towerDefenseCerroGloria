import java.util.ArrayList;

public class Nivel extends Mapa {


    public void generarCasilleros() {
        //Esto en el futuro se creara aleatoriamente por nivel como nos dijo el profe
        caminosEnemigos.add(new Coordenada(0, 0));
        caminosEnemigos.add(new Coordenada(0, 1));
        caminosEnemigos.add(new Coordenada(1, 1));
        caminosEnemigos.add(new Coordenada(2, 1));
        caminosEnemigos.add(new Coordenada(2, 2));


// Llena la lista de coordenadas con objetos Coordenada

// Supongamos que tienes una matriz de tamaño n x m
        int n = mapa.length;      // Número de filas
        int m = mapa[0].length;   // Número de columnas

// Itera sobre la lista de coordenadas
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mapa[i][j] = new Casillero(false);
            }
        }
        for (Coordenada coordenada : caminosEnemigos) {
            int x = coordenada.getX();
            int y = coordenada.getY();

            // Verifica si la coordenada coincide con una posición en la matriz
            if (x >= 0 && x < n && y >= 0 && y < m) {
                mapa[x][y] = new Casillero(true);
                // Realiza acciones con el valor en la matriz si es necesario
            }
        }
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                System.out.print(mapa[i][j].getEsCamino());
            }
            System.out.println(" ");
        }


    }







}
