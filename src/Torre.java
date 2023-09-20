import java.util.ArrayList;
import java.util.Collections;

public abstract class Torre implements Ataque {
    protected int costeTorre; //cantidad de puntos de magia que necesitaremos para comprar la torre
    protected  double danio;
    protected int velocidadAtaque;
    protected int alcanceAtaque;
    protected int nivelTorre;
    protected int id;
    protected Coordenada coordenadaTorre;

    protected ArrayList<Coordenada> coordCasillerosAtaque = new ArrayList<>();
    //Antiguo casilleros ataque:
//    protected ArrayList<Coordenada> casillerosAtaque = new ArrayList<>();
    protected ArrayList<Casillero> casillerosAtaque = new ArrayList<>();
    protected ArrayList<Enemigo> listaAtaqueEnemigos = new ArrayList<>(); // Esta lista contiene a los enemigos a los que esta atacando la torre



//    public abstract ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo);

    public void atacar(Nivel nivelActual, Casillero casilleroActual) {
        // Esta flag sirve para que la torre ataque a 1 casillero solamente donde detecte enemigos.
        boolean haAtacado = false;

        for (Casillero casilleroAtaque : this.casillerosAtaque) {
            System.out.println("Estoy revisando para atacar a " + casilleroAtaque.toString());
            listaAtaqueEnemigos = prioridadEnemigo(casilleroAtaque);

            for (Enemigo enemigoActual : listaAtaqueEnemigos){
                enemigoActual.restarVida(this.danio);
                if (enemigoActual.getVida() <= 0){
                    // Si la torre mata al enemigo, sucede lo siguiente:
                    casilleroAtaque.eliminarEnemigo(enemigoActual);
                    nivelActual.aumentarPuntosMagia(enemigoActual.getRecompensaEnemigo());
                    casilleroAtaque.getEnemigosListosParaMoverse().remove(enemigoActual);
                    System.out.println("La torre " + this.toString() + " ha asesinado a " + enemigoActual.toString() + " | +" + enemigoActual.getRecompensaEnemigo() + " Ptos. Magia.");
                } else {
                    // Si la torre no mata al enemigo muestra el siguiente mensaje:
                    System.out.println(enemigoActual.toString() + " fue atacado por "+this.toString()+" y ahora tiene "+enemigoActual.getVida()+" de vida");
                }
                haAtacado = true; // Activar la bandera de ataque
                break;
            }
        }
    }


    public ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo){
        // Dependiendo de la torre es la cantidad de enemigos que devuelve y a quien focusea

        ArrayList<Enemigo> listaEnemigosPorPrioridad = new ArrayList<>();
        //Este caso es para la torre comun, que focusea primero a los humanos
        for (ArrayList<Enemigo> listaEnemigos : casilleroEnemigo.getEnemigosCasillero().values()){
            if (!listaEnemigos.isEmpty()){
                listaEnemigosPorPrioridad.add(listaEnemigos.get(0));
            }
        }
        return listaEnemigosPorPrioridad;
    }


    // De acuerdo al alcanze de la torre y su posicion en el mapa, calcula a que coordenadas del mapa puede pegarle.
    // Devuelve coordenadas de ataque para cada torre. (construye la lista coordCasilleroAtaque)
    public void calcularCoordenadasCasillerosAtaque(Nivel nivelActual){

        String [][] arrayMapa = nivelActual.getMapaNivel().getMapaRefCoord();

        int posX = coordenadaTorre.getX();
        int posY = coordenadaTorre.getY();

        int newPosX = posX - this.alcanceAtaque;
        int newPosY = posY - this.alcanceAtaque;


        for (int i = newPosX; i <= posX + this.alcanceAtaque; i++) {
            for (int j = newPosY; j <= posY + this.alcanceAtaque; j++) {
                // Verificar que i y j estén dentro de los límites del mapa
                if (i >= 0 && i < arrayMapa.length && j >= 0 && j < arrayMapa[0].length) {
                    // Verificar si el casillero es diferente de la posición de la torre

                    if ((!arrayMapa[i][j].contains("C") && !arrayMapa[i][j].contains("E")) || (i == posX && j == posY)) {
                        continue;
                    }
                    // Agregar la coordenada a casillerosAtaque
                    this.coordCasillerosAtaque.add(0,new Coordenada(i, j));
                }
            }
        }

        agregarCasillerosAtaquePorCoordenada(nivelActual, this.coordCasillerosAtaque);
        Collections.sort(this.casillerosAtaque, new CasilleroIdComparator());
    }


    // Segun la lista de coordCasillerosAtaque construye una lista de Casilleros a los cuales la Torre puede atacar
    // Construye (casillerosAtaque)
    public void agregarCasillerosAtaquePorCoordenada(Nivel nivelActual ,ArrayList<Coordenada> coordCasillerosAtaque) {
        for (Coordenada coordCasillero : coordCasillerosAtaque) {
            this.casillerosAtaque.add(nivelActual.buscarCasilleroPorCoordenada(coordCasillero));
        }
    }


    public void imprimirCasillerosAtaque() {
        for (Casillero casilleroAtaque : this.casillerosAtaque){
            System.out.println("---------Casilleros ataque " + this.toString() + " ---------");
            System.out.println(casillerosAtaque.toString() + " ");
            System.out.println("---------------------------------------");
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

    public Coordenada getCoordenadaTorre() {
        return coordenadaTorre;
    }
}
