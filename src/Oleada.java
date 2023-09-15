import java.util.ArrayList;

public class Oleada {
    // Esta Clase Oleada se encarga de:
    // 1) Generar los enemigos de cada oleada
    // 2) Controlar las iteraciones, posiblemente con la flecha '->' para avanzar en el desarrollo de la partida
    // 3) En cada iteracion: Enemigos son cargados en casilleros arranque
    //                       Enemigos se desplazan a traves de los casilleros
    //                       Torres detectan y atacan enemigos en sus casilleros de ataque
    //                       Se muestra el mapa, posiblemente una tabla/log
    // 4) La tabla/Log indica que enemigos hay en cada casillero, que torres estan atacando a quienes, enemigos eliminados...
    private ArrayList<Enemigo> listaEnemigosOleada = new ArrayList<>();
    private int nivelActual;
    private int nroOleada;

    public Oleada(int nroNivel) {
        this.nivelActual = nroNivel;
        this.nroOleada = 1;
    }

    public void generarEnemigos() {
        // Segun nivel actual y nro oleada debe generar cierta cantidad y tipo de enemigos de forma random

        // Pureba inicial:
        listaEnemigosOleada.add(new Humano());
//        listaEnemigosOleada.add(new Elfo());
//        listaEnemigosOleada.add(new Enano());
//        listaEnemigosOleada.add(new Hobbit());
//        listaEnemigosOleada.add(new Humano());
//        listaEnemigosOleada.add(new Elfo());
//        listaEnemigosOleada.add(new Enano());
//        listaEnemigosOleada.add(new Hobbit());
        //listaEnemigosOleada.add(new Humano());
//        listaEnemigosOleada.add(new Elfo());
//        listaEnemigosOleada.add(new Enano());
    }

    public void cargarEnemigosCasilleroInicial(ArrayList<Casillero> casillerosEnemigos) {
        // Se cargan una determinada cantidad de enemigos segun el nivel y la oleada
        Casillero c1 = casillerosEnemigos.get(0);
        int cantidadEnemigosCarga = 3;

//        if (nivelActual == 1) {
//            switch (nroOleada) {
//                case 1: cantidadEnemigosCarga = 3;
//                        break;
//                case 2: cantidadEnemigosCarga = 4;
//                        break;
//                case 3: cantidadEnemigosCarga = 5;
//                        break;
//            }
//        }

        for (int i = 0; i < cantidadEnemigosCarga; i++) {
            if (!this.listaEnemigosOleada.isEmpty()) {
                c1.agregarEnemigo(this.listaEnemigosOleada.remove(listaEnemigosOleada.size() - 1));
            }

        }

        //c1.mostrarEntidadesCasillero();
    }

    public int getNroOleada() {
        return nroOleada;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNroOleada(int nroOleada) {
        this.nroOleada = nroOleada;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public ArrayList<Enemigo> getListaEnemigosOleada() {
        return listaEnemigosOleada;
    }
}
