package TowerDefenceCerro.MomentosJuego;

import TowerDefenceCerro.Coordenada;
import TowerDefenceCerro.Torres.Torre;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que se encarga de determinar referencias para generar coordenadas tanto para casilleros, torres y barreras.
 * También se utiliza para verificar a qué casilleros alcanza cada torre y mostrar por consola el desarrollo de las iteraciones.
 * @author Victor Ramirez, Aida Laricchia
 * @version 1.0
 */
public class Mapa {
    // Responsabilidad principal: Determinar referencias para generar coordenadas tanto para casilleros, torres y barreras.
    // Tambien para verificar a que casilleros alcanza cada torre y para mostrar por consola el desarrollo de las iteraciones
    protected ArrayList<Coordenada> caminosEnemigos = new ArrayList<>();
    protected String[][] map = new String[5][15];
    /**
     * Genera un mapa aleatorio, estableciendo caminos para que los enemigos sigan.
     * El camino se genera de manera aleatoria pero garantizando que llegue hasta el final.
     */
    public void generarMapaAleatorio() {

        int rows = this.map.length;
        int cols = this.map[0].length;

        // Inicializar el mapa con puntos
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.map[i][j] = "  . ";
            }
        }

        // Inicializar el generador de números aleatorios
        Random random = new Random();

        // Empezar el camino en la primera columna, fila aleatoria
        int currentRow = random.nextInt(rows);
        int currentCol = 0;

        // Marcar el inicio del camino con "E"
        this.map[currentRow][currentCol] = " E ";

        // Definir los movimientos posibles (derecha, arriba, abajo)
        int[] rowOffsets = { 0, -1, 1 };
        int[] colOffsets = { 1, 0, 0 };

        // Agregar la primera coordenada del camino
        this.caminosEnemigos.add(new Coordenada(currentRow, currentCol));

        // Contadores para los movimientos hacia arriba, abajo y derecha
        int upCount = 0;
        int downCount = 0;
        int rightCount = 0;

        // Generar el camino aleatorio
        while (currentCol < cols - 2) { // Cambiar la condición para permitir dos movimientos más hacia la derecha
            // Mezclar los índices de movimiento aleatoriamente
            int[] indexes = { 0, 1, 2 };
            for (int i = 2; i >= 0; i--) {
                int j = random.nextInt(i + 1);
                int temp = indexes[i];
                indexes[i] = indexes[j];
                indexes[j] = temp;
            }

            // Intentar moverse en las direcciones aleatorias
            for (int i = 0; i < 3; i++) {

                int newRow = currentRow + rowOffsets[indexes[i]] * 2; // Mover dos casilleros
                int newCol = currentCol + colOffsets[indexes[i]] * 2; // Mover dos casilleros

                // Verificar si el nuevo movimiento está dentro del mapa y no es una "C"
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && this.map[newRow][newCol].equals("  . ")) {
                    // Realizar dos movimientos en la dirección correspondiente
                    for (int j = 0; j < 2; j++) {
                        currentRow += rowOffsets[indexes[i]];
                        currentCol += colOffsets[indexes[i]];
                        this.map[currentRow][currentCol] = " C ";
                        this.caminosEnemigos.add(new Coordenada(currentRow, currentCol));
                    }

                    // Contar los movimientos hacia arriba, abajo y derecha
                    if (indexes[i] == -1) {
                        upCount += 2;
                    } else if (indexes[i] == 1) {
                        downCount += 2;
                    } else if (indexes[i] == 0) {
                        rightCount += 2;
                    }

                    // Verificar si se han hecho al menos 2 movimientos en cada dirección
                    if (upCount >= 2 && downCount >= 2 && rightCount >= 2) {
                        break; // Salir del bucle si se cumplen las condiciones
                    }

                    break; // Salir del bucle de movimientos
                }
            }
        }

        // Marcar el final del camino con "CG"
        int coordXCerroGloria = this.caminosEnemigos.get(caminosEnemigos.size()-1).getX();
        int coordYCerroGloria = this.caminosEnemigos.get(caminosEnemigos.size()-1).getY();
        this.map[coordXCerroGloria][coordYCerroGloria] = " CG";

    }
    /**
     * Muestra el mapa por consola, incluyendo las coordenadas de las celdas.
     */
    public void mostrarMapa() {
        System.out.print("  ");
        for (int j = 0; j < this.map[0].length; j++) {
            if (j < 10){
                System.out.print("   " +j + "  ");
            }else{
                System.out.print("  " +j + "  ");
            }
        }
        System.out.println();
        for (int i = 0; i < this.map.length; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j].length() == 3) {
                    System.out.print(" " + map[i][j] + "  ");
                } else {
                    System.out.print(map[i][j] + "  ");
                }

            }
            System.out.println();
            System.out.println();
        }
    }
    /**
     * Coloca una referencia de una torre en el mapa en la posición correspondiente.
     *
     * @param torre La torre que se coloca en el mapa.
     */
    public void colocarRefTorre(Torre torre){
        int posX = torre.getCoordenadaTorre().getX();
        int posY = torre.getCoordenadaTorre().getY();

        map[posX][posY] = torre.toString();
    }
    /**
     * Obtiene la lista de coordenadas que representan los caminos por donde se moverán los enemigos en el mapa.
     *
     * @return La lista de coordenadas de los caminos de enemigos.
     */
    public ArrayList<Coordenada> getCaminosEnemigos() {
        return caminosEnemigos;
    }
    /**
     * Obtiene una referencia a la matriz que representa el mapa con sus elementos.
     *
     * @return La matriz del mapa con referencias a sus elementos.
     */
    public String[][] getMapaRefCoord() { return map; }
}
