import java.util.ArrayList;

public abstract class Torre implements Ataque {
    protected int costeTorre; //cantidad de puntos de magia que necesitaremos para comprar la torre
    protected  double danio;
    protected int velocidadAtaque;
    protected int alcanceAtaque;
    protected int nivelTorre;
    protected int id;
    protected Coordenada coordenadaTorre;

    protected ArrayList<Coordenada> casillerosAtaque = new ArrayList<>();
    protected ArrayList<Enemigo> listaAtaqueEnemigos = new ArrayList<>(); // Esta lista contiene a los enemigos a los que esta atacando la torre

    public abstract ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo);


    public void chequearCasillerosAtaque(ArrayList<Casillero> casillerosEnemigos, Nivel nivelActual) {
        boolean haAtacado = false;

        for (Casillero casilleroEnemigo : casillerosEnemigos) {
            if (haAtacado) {
                // Si ya ha atacado en otro casillero, salir del bucle
                break;
            }

            for (Coordenada coordenadaAtaqueTorre : casillerosAtaque) {
                if (casilleroEnemigo.getCoordenadaCasillero().compararConCoordenada(coordenadaAtaqueTorre)) {
                    if (casilleroEnemigo.tieneEnemigos()) {
                        // En el casillero actual devuelvo los enemigos a los que le voy a pegar
                        this.atacar(casilleroEnemigo, nivelActual);
                        haAtacado = true; // Activar la bandera de ataque
                        break;
                    }
                }
            }
        }
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

                    if (!arrayMapa[i][j].contains("C") || (i == posX && j == posY)) {
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
            System.out.println(coord.mostrarCoordenada());
        }
    }

    public int getCosteTorre() {
        return costeTorre;
    }

    public double getDanio() {
        return danio;
    }

    public int getAlcanceAtaque() {
        return alcanceAtaque;
    }

    public void aumentarDanio(int aumentoDanio) {
        this.danio += aumentoDanio;
    }

    public void aumentarAlcance() {
        this.alcanceAtaque++;
    }
}
