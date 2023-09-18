import java.util.ArrayList;
import java.util.Random;

public class RandomPathInMatrix {
        public static void main(String[] args) {
            // Tamaño del mapa
            int rows = 5;
            int cols = 15;

            // Crear una matriz para el mapa
            String[][] map = new String[rows][cols];

            // Inicializar el mapa con puntos
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    map[i][j] = " . ";
                }
            }

            // Inicializar el generador de números aleatorios
            Random random = new Random();

            // Empezar el camino en la primera columna, fila aleatoria
            int currentRow = random.nextInt(rows);
            int currentCol = 0;

            // Marcar el inicio del camino con "E"
            map[currentRow][currentCol] = " E ";

            // Definir los movimientos posibles (derecha, arriba, abajo)
            int[] rowOffsets = { 0, -1, 1 };
            int[] colOffsets = { 1, 0, 0 };

            // Inicializar la lista de coordenadas
            ArrayList<Coordenada> caminosEnemigos = new ArrayList<>();

            // Agregar la primera coordenada del camino
            caminosEnemigos.add(new Coordenada(currentRow, currentCol));

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
                            && map[newRow][newCol].equals(" . ")) {
                        // Realizar dos movimientos en la dirección correspondiente
                        for (int j = 0; j < 2; j++) {
                            currentRow += rowOffsets[indexes[i]];
                            currentCol += colOffsets[indexes[i]];
                            map[currentRow][currentCol] = " C ";
                            caminosEnemigos.add(new Coordenada(currentRow, currentCol));
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

            // Imprimir el mapa de manera legible con referencias numéricas
            System.out.print("  ");
            for (int j = 0; j < cols; j++) {
                if (j < 11){
                System.out.print("  " +j + "  ");
                }else{
                    System.out.print(" " +j + "  ");
                }
            }
            System.out.println();
            for (int i = 0; i < rows; i++) {
                System.out.print(i + "  ");
                for (int j = 0; j < cols; j++) {
                    System.out.print( map[i][j] + "  ");
                }
                System.out.println();
            }

        }
    }

