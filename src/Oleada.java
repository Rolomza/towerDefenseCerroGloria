import java.util.ArrayList;
import java.util.Random;

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

        if (this.nivelActual == 1){
            switch (nroOleada){
                case 1:
                    crearNEnemigosDeTipoS(15 , "Hobbit");
                    break;
                case 2:
                    crearNEnemigosDeTipoS(10 , "Hobbit");
                    crearNEnemigosDeTipoS(10 , "Humano");
                    break;
                case 3:
                    crearNEnemigosDeTipoS(10 , "Hobbit");
                    crearNEnemigosDeTipoS(15 , "Humano");
                    break;

            }
        } else if (this.nivelActual == 2){
            switch (nroOleada){
                case 1:
                    crearNEnemigosDeTipoS(30 , "Hobbit");
                    crearNEnemigosDeTipoS(35 , "Humano");
                    crearNEnemigosDeTipoS(15 , "Elfo");
                    break;
                case 2:
                    crearNEnemigosDeTipoS(40 , "Hobbit");
                    crearNEnemigosDeTipoS(45 , "Humano");
                    crearNEnemigosDeTipoS(50 , "Elfo");
                    break;
                case 3:
                    crearNEnemigosDeTipoS(50 , "Hobbit");
                    crearNEnemigosDeTipoS(60 , "Humano");
                    crearNEnemigosDeTipoS(65 , "Elfo");
                    break;

            }
        } else if (this.nivelActual == 3){
            switch (nroOleada){
                case 1:
                    crearNEnemigosDeTipoS(30 , "Hobbit");
                    crearNEnemigosDeTipoS(40 , "Humano");
                    crearNEnemigosDeTipoS(60 , "Elfo");
                    crearNEnemigosDeTipoS(10 , "Enano");
                    break;
                case 2:
                    crearNEnemigosDeTipoS(40 , "Humano");
                    crearNEnemigosDeTipoS(70 , "Elfo");
                    crearNEnemigosDeTipoS(30 , "Enano");
                    break;
                case 3:
                    crearNEnemigosDeTipoS(50 , "Humano");
                    crearNEnemigosDeTipoS(80 , "Elfo");
                    crearNEnemigosDeTipoS(45 , "Enano");
                    break;
            }
        }
    }

    public void crearNEnemigosDeTipoS(int n , String tipoEnemigo){

        for (int i=0 ; i < n; i++){
            switch (tipoEnemigo) {
                case ("Hobbit") -> listaEnemigosOleada.add(new Hobbit());
                case ("Humano") -> listaEnemigosOleada.add(new Humano());
                case ("Elfo") -> listaEnemigosOleada.add(new Elfo());
                case ("Enano") -> listaEnemigosOleada.add(new Enano());
            }
        }

    }

    public void cargarEnemigosCasilleroInicial(ArrayList<Casillero> casillerosEnemigos) {
        // Se cargan una determinada cantidad de enemigos segun el nivel y la oleada
        Casillero c1 = casillerosEnemigos.get(0);
        //int cantidadEnemigosCarga = 8;
        int cantidadEnemigosCarga = 0;
            switch (nivelActual) {
                case 1: cantidadEnemigosCarga = 3;
                        break;
                case 2: cantidadEnemigosCarga = 5;
                        break;
                case 3: cantidadEnemigosCarga = 7;
                        break;
            }
        Random random = new Random();
        int numeroRandomEnemigo;
        for (int i = 0; i < cantidadEnemigosCarga; i++) {

            if (!this.listaEnemigosOleada.isEmpty()) {
                numeroRandomEnemigo = random.nextInt(listaEnemigosOleada.size());
                c1.agregarEnemigo(this.listaEnemigosOleada.remove(numeroRandomEnemigo));
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

    public void reiniciarNroOleada() {
        this.nroOleada = 1;
    }
    public void aumentarOleada(){
        this.nroOleada++;
    }
}
