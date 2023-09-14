import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Torre implements Ataque {
    protected int costeTorre; //cantidad de puntos de magia que necesitaremos para comprar la torre
    protected  double daño;
    protected int velocidadAtaque;
    protected int alcanceAtaque;
    protected int nivelTorre;
    protected Coordenada coordenadaTorre;

    protected ArrayList<Coordenada> casillerosAtaque = new ArrayList<>();

    @Override
    public void atacar(Mapa mapa) {
        //

    }

    // De acuerdo al alcanze de la torre y su posicion en el mapa, determinas a que casilleros
    // puede atacar en cada iteracion. Esos casilleros los guarda en casillerosAtaque.
    public void calcularCasillerosAtaque(Mapa mapaNivel){

        String [][] arrayMapa = mapaNivel.getMapaRefCoord();

        int posX = coordenadaTorre.getX();
        int posY = coordenadaTorre.getY();

        int newPosX = posX - this.alcanceAtaque;
        int newPosY = posY - this.alcanceAtaque;


        for (int i = newPosX; i <= posX + this.alcanceAtaque; i++) {
            for (int j = newPosY; j <= posY + this.alcanceAtaque; j++) {
                // Verificar que i y j estén dentro de los límites del mapa
                if (i >= 0 && i < arrayMapa.length && j >= 0 && j < arrayMapa[0].length) {
                    // Verificar si el casillero es diferente de la posición de la torre

                    if (!arrayMapa[i][j].equals("C") || (i == posX && j == posY)) {
                        continue;
                    }
                    // Agregar la coordenada a casillerosAtaque
                    this.casillerosAtaque.add(new Coordenada(i, j));
                }
            }
        }

    }

    public void imprimirCasillerosAtaque() {

        for (Coordenada coord : this.casillerosAtaque){
            coord.mostrarCoordenada();
        }


    }
}
