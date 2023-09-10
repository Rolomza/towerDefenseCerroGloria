import java.util.ArrayList;
import java.util.Random;

public class RandomPathInMatrix {
    public static void main(String[] args) {

        RandomPathInMatrix nuevoMapa = new RandomPathInMatrix();
        nuevoMapa.crearCaminoMapa();
    }

    public static void printMatrix(String[][] matrix) {
        for (String[] row : matrix) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public void crearCaminoMapa() {
        String[][] randomMap = new String[10][10];

        // Inicializa la matriz con "." en todos los lugares
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                randomMap[i][j] = ".";
            }
        }

        // Punto de inicio
        int startX = 5;
        int startY = 0;

        Random random = new Random();
        randomMap[5][0] = "C";
        int count = 0;
        int direccionPrev = -1; // Variable para almacenar la dirección previa
        int cantidadMovimientos;
        int distanciaMinima = 2; // Distancia mínima entre "C"

        while (count < 10) {
            int direccionRandom;

            // Verifica que si en la iteración anterior fue hacia arriba, ahora no vaya hacia abajo
            // También verifica que si fue hacia la derecha no vaya hacia la izquierda
            do {
                direccionRandom = random.nextInt(4);
            } while (direccionRandom == direccionPrev);

            switch (direccionRandom) {
                case 0: // Arriba
                    cantidadMovimientos = random.nextInt(3) + distanciaMinima;
                    if (validarCamino(randomMap, startX, startY, "Arriba", cantidadMovimientos)) {
                        startX -= cantidadMovimientos;
                        direccionPrev = 1;
                        count++;
                    }
                    break;
                case 1: // Abajo
                    cantidadMovimientos = random.nextInt(3) + distanciaMinima;
                    if (validarCamino(randomMap, startX, startY, "Abajo", cantidadMovimientos)) {
                        startX += cantidadMovimientos;
                        direccionPrev = 0;
                        count++;
                    }
                    break;
                case 2: // Izquierda
                    cantidadMovimientos = random.nextInt(3) + distanciaMinima;
                    if (validarCamino(randomMap, startX, startY, "Izquierda", cantidadMovimientos)) {
                        startY -= cantidadMovimientos;
                        direccionPrev = 3;
                        count++;
                    }
                    break;
                case 3: // Derecha
                    cantidadMovimientos = random.nextInt(3) + distanciaMinima;
                    if (validarCamino(randomMap, startX, startY, "Derecha", cantidadMovimientos)) {
                        startY += cantidadMovimientos;
                        direccionPrev = 2;
                        count++;
                    }
                    break;
            }
        }
        randomMap[5][0] = "S";
        randomMap[startX][startY] = "E";
        printMatrix(randomMap);
    }

    public Boolean validarCamino(String[][] randomMap, int posX, int posY, String direccion, int cantidadMovimientos) {

        switch (direccion) {
            case "Derecha":

                if (posY + cantidadMovimientos >= randomMap[0].length || randomMap[posX][posY + cantidadMovimientos].equals("C")) {
                    return false;
                }
                break;
            case "Izquierda":

                if (posY - cantidadMovimientos < 0 || randomMap[posX][posY - cantidadMovimientos].equals("C")) {
                    return false;
                }
                break;
            case "Abajo":
                if (posX + cantidadMovimientos >= randomMap.length || randomMap[posX + cantidadMovimientos][posY].equals("C")) {
                    return false;
                }

                break;
            case "Arriba":
                if (posX - cantidadMovimientos < 0 || randomMap[posX - cantidadMovimientos][posY].equals("C")) {
                    return false;
                }
                break;
        }

        // Actualiza los casilleros después de la validación
        switch (direccion) {
            case "Derecha":
                for (int i = 1; i <= cantidadMovimientos; i++) {
                    randomMap[posX][posY + i] = "C";
                }
                break;
            case "Izquierda":
                for (int i = 1; i <= cantidadMovimientos; i++) {
                    randomMap[posX][posY - i] = "C";
                }
                break;
            case "Abajo":
                for (int i = 1; i <= cantidadMovimientos; i++) {
                    randomMap[posX + i][posY] = "C";
                }
                break;
            case "Arriba":
                for (int i = 1; i <= cantidadMovimientos; i++) {
                    randomMap[posX - i][posY] = "C";
                }
                break;
        }

        return true;
    }

}
