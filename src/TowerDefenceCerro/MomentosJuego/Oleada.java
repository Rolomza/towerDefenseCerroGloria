package TowerDefenceCerro.MomentosJuego;

import TowerDefenceCerro.Casillero;
import TowerDefenceCerro.Cerro;
import TowerDefenceCerro.Enemigos.*;
import TowerDefenceCerro.Torres.Torre;

import java.util.ArrayList;
import java.util.Random;
/**
 * Clase que representa una oleada de enemigos en el juego.
 * Se encarga de generar los enemigos, controlar las iteraciones y el movimiento de las unidades, así como gestionar las interacciones con las torres y el Cerro de la Gloria.
 *
 * @author Augustos Robles, Victor Ramirez, Aida Laricchia
 * @version 1.0
 */
public class Oleada {
    // Esta Clase TowerDefenceCerro.MomentosJuego.Juego.Oleada se encarga de:
    // 1) Generar los enemigos de cada oleada
    // 2) Controlar las iteraciones, posiblemente con la flecha '->' para avanzar en el desarrollo de la partida
    // 3) En cada iteracion: TowerDefenceCerro.Enemigos son cargados en casilleros arranque
    //                       TowerDefenceCerro.Enemigos se desplazan a traves de los casilleros
    //                       Torres detectan y atacan enemigos en sus casilleros de ataque
    //                       Se muestra el mapa, posiblemente una tabla/log
    // 4) La tabla/Log indica que enemigos hay en cada casillero, que torres estan atacando a quienes, enemigos eliminados...
    private ArrayList<Enemigo> listaEnemigosOleada = new ArrayList<>();
    private int nivelActual;
    private int nroOleada;
    /**
     * Constructor de la clase Oleada.
     *
     * @param nroNivel El número del nivel actual.
     */
    public Oleada(int nroNivel) {
        this.nivelActual = nroNivel;
        this.nroOleada = 1;
    }
    /**
     * Inicia una oleada de enemigos en el juego.
     *
     * @param nivelActual El nivel actual en el juego.
     */
    public void iniciarOleada(Nivel nivelActual) {
        //Iteraciones TowerDefenceCerro.MomentosJuego.Juego


        int count = 0;

        int posicionCerro = nivelActual.getCasillerosEnemigos().size()-1;
        Cerro cerroGloria = nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria();
        nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria().mostrarVida();;
        generarEnemigos();

        // Corazon del juego
        while (cerroGloria.getVida() > 0){
            try {
                Thread.sleep(2250); // Agrega un retraso de 3 segundos (3000 milisegundos)
            } catch (InterruptedException e) {
                // Maneja la excepción si es necesario
                e.printStackTrace();
            }
            if (!this.listaEnemigosOleada.isEmpty()){
                // Mientras hay enemigos restantes para generar en la oleada
                cargarEnemigosCasilleroInicial(nivelActual.getCasillerosEnemigos());
            }

            if (!nivelActual.existenEnemigos()){
                break;
            }

            System.out.println("--- ITERACION : " + (count+1) + " ---");
            System.out.println("Puntos de Magia: " + nivelActual.getPuntosMagia());
            nivelActual.getMapaNivel().mostrarMapa();
            nivelActual.mostrarCasillerosConEnemigos();

            // Funciones sobre los casilleros
            torresAtacan(nivelActual);
            reducirContadoresEnemigos(nivelActual); // El error estaba aqui
            enemigosAtacan(nivelActual);
            moverEnemigosListos(nivelActual); //Aca muevo a los enemigos, si estan en el penultimo casillero atacan y mueren

            cerroGloria.mostrarVida();

            count++;
        }
        if (nivelActual.getNroNivel()==3 && this.nroOleada==3 && cerroGloria.getVida()>0){
            System.out.println("FELICIDADES, GANASTE");
        }
    }

    /**
     * Realiza los ataques de las torres a los enemigos en sus casilleros de ataque.
     *
     * @param nivelActual El nivel actual en el juego.
     */
    // Revisar si funciona la nueva implementacion de torres atacan
    public void torresAtacan(Nivel nivelActual){
        // Aca iterariamos todas las torres y atacarian antes de que los enemigos se muevan
        for (Torre torreActual : nivelActual.getListaTorres()){
            //torreActual.imprimirCasillerosAtaque();
            // Revisar casillero, esta puesto para evitar romper ataque enemigo
            Casillero casillero = new Casillero(99,99);
            torreActual.atacar(nivelActual, casillero);
        }
    }
    /**
     * Realiza los ataques de los enemigos a las barreras en sus casilleros.
     *
     * @param nivelActual El nivel actual en el juego.
     */
    public void enemigosAtacan(Nivel nivelActual){
        for (Casillero casillero: nivelActual.getCasillerosEnemigos()) {
            if(casillero.tieneEnemigos() && casillero.tieneBarrera()) {
                for (ArrayList<Enemigo> listaEnemigos : casillero.getEnemigosCasillero().values()) {
                    if (casillero.getBarrera() != null) {
                        for (Enemigo enemigo : listaEnemigos) {
                            if (casillero.getBarrera().getVida() > 0) {
                                enemigo.atacar(nivelActual, casillero);
                                if (casillero.getBarrera().getVida() < 0){
                                    System.out.println("La barrera ha sido eliminada");
                                }
                            } else {
                                casillero.eliminarBarrera();
                                break;
                            }
                        }
                    }else{
                        break;
                    }
                }
            }
        }
    }
    /**
     * Reduce los contadores de los enemigos en los casilleros y los mueve si es necesario.
     *
     * @param nivelActual El nivel actual en el juego.
     */
    public void reducirContadoresEnemigos(Nivel nivelActual) {
        for (Casillero casillero: nivelActual.getCasillerosEnemigos()) {
            if(casillero.tieneEnemigos()) {
                casillero.reducirContadores();
                casillero.setEnemigosListosParaMoverse(); // Aca agregaba los enemigos que no era
            }
        }
    }
    /**
     * Realiza el movimiento de los enemigos en los casilleros.
     *
     * @param nivelActual El nivel actual en el juego.
     */
    public void moverEnemigosListos(Nivel nivelActual) {
        for (Casillero casillero: nivelActual.getCasillerosEnemigos()) {
            if(casillero.tieneEnemigos()) {
                if (casillero.getId() < nivelActual.getCasillerosEnemigos().size()-1) {
                    Casillero casilleroSiguiente = nivelActual.getCasillerosEnemigos().get(casillero.getId()+1);
                    moverEnemigos(casillero, casilleroSiguiente, nivelActual);
                }
            }
        }

    }
    /**
     * Mueve los enemigos de un casillero a otro.
     *
     * @param casilleroActual    El casillero actual de los enemigos.
     * @param casilleroSiguiente El casillero al que se moverán los enemigos.
     * @param nivelActual        El nivel actual en el juego.
     */
    public void moverEnemigos(Casillero casilleroActual, Casillero casilleroSiguiente, Nivel nivelActual) {
        ArrayList<Enemigo> listaEnemigosParaMoverse = casilleroActual.getEnemigosListosParaMoverse();
        boolean esPenultimo = false;
        if (!casilleroActual.tieneBarrera()){
            //si tiene barrera los enemigos listos para moverse no tienen que moverse

            if (!listaEnemigosParaMoverse.isEmpty()) {

                if (casilleroSiguiente.getId() == nivelActual.getCasillerosEnemigos().size() - 1) {
                    esPenultimo = true;
                }
                Enemigo enemigo;


                if (esPenultimo) {
                    while (!listaEnemigosParaMoverse.isEmpty()) {
                        enemigo = listaEnemigosParaMoverse.remove(0);
                        // Revisar implementacion Ataque para enemigos
                        enemigo.atacar(nivelActual, casilleroSiguiente);
                        casilleroActual.eliminarEnemigo(enemigo);
                    }

                } else {
                    while (!listaEnemigosParaMoverse.isEmpty()) {
                        enemigo = listaEnemigosParaMoverse.remove(0);
                        casilleroSiguiente.agregarEnemigo(enemigo);
                        casilleroActual.eliminarEnemigo(enemigo);
                        enemigo.reiniciarContadorIteraciones();
                    }
                }
            }
        }
    }


    public void generarEnemigos() {
        // Segun nivel actual y nro oleada debe generar cierta cantidad y tipo de enemigos de forma random

        if (this.nivelActual == 1){
            switch (nroOleada){
                case 1:
                    crearNEnemigosDeTipoS(15 , "TowerDefenceCerro.Enemigos.Hobbit");
                    break;
                case 2:
                    crearNEnemigosDeTipoS(10 , "TowerDefenceCerro.Enemigos.Hobbit");
                    crearNEnemigosDeTipoS(10 , "TowerDefenceCerro.Enemigos.Humano");
                    break;
                case 3:
                    crearNEnemigosDeTipoS(10 , "TowerDefenceCerro.Enemigos.Hobbit");
                    crearNEnemigosDeTipoS(15 , "TowerDefenceCerro.Enemigos.Humano");
                    break;

            }
        } else if (this.nivelActual == 2){
            switch (nroOleada){
                case 1:
                    crearNEnemigosDeTipoS(30 , "TowerDefenceCerro.Enemigos.Hobbit");
                    crearNEnemigosDeTipoS(35 , "TowerDefenceCerro.Enemigos.Humano");
                    crearNEnemigosDeTipoS(15 , "TowerDefenceCerro.Enemigos.Elfo");
                    break;
                case 2:
                    crearNEnemigosDeTipoS(40 , "TowerDefenceCerro.Enemigos.Hobbit");
                    crearNEnemigosDeTipoS(45 , "TowerDefenceCerro.Enemigos.Humano");
                    crearNEnemigosDeTipoS(50 , "TowerDefenceCerro.Enemigos.Elfo");
                    break;
                case 3:
                    crearNEnemigosDeTipoS(50 , "TowerDefenceCerro.Enemigos.Hobbit");
                    crearNEnemigosDeTipoS(60 , "TowerDefenceCerro.Enemigos.Humano");
                    crearNEnemigosDeTipoS(65 , "TowerDefenceCerro.Enemigos.Elfo");
                    break;

            }
        } else if (this.nivelActual == 3){
            switch (nroOleada){
                case 1:
                    crearNEnemigosDeTipoS(30 , "TowerDefenceCerro.Enemigos.Hobbit");
                    crearNEnemigosDeTipoS(40 , "TowerDefenceCerro.Enemigos.Humano");
                    crearNEnemigosDeTipoS(60 , "TowerDefenceCerro.Enemigos.Elfo");
                    crearNEnemigosDeTipoS(10 , "TowerDefenceCerro.Enemigos.Enano");
                    break;
                case 2:
                    crearNEnemigosDeTipoS(40 , "TowerDefenceCerro.Enemigos.Humano");
                    crearNEnemigosDeTipoS(70 , "TowerDefenceCerro.Enemigos.Elfo");
                    crearNEnemigosDeTipoS(30 , "TowerDefenceCerro.Enemigos.Enano");
                    break;
                case 3:
                    crearNEnemigosDeTipoS(50 , "TowerDefenceCerro.Enemigos.Humano");
                    crearNEnemigosDeTipoS(80 , "TowerDefenceCerro.Enemigos.Elfo");
                    crearNEnemigosDeTipoS(45 , "TowerDefenceCerro.Enemigos.Enano");
                    break;
            }
        }
    }
    /**
     * Genera una cantidad específica de enemigos de un tipo dado.
     *
     * @param n          La cantidad de enemigos a generar.
     * @param tipoEnemigo El tipo de enemigo a generar.
     */
    public void crearNEnemigosDeTipoS(int n , String tipoEnemigo){

        for (int i=0 ; i < n; i++){
            switch (tipoEnemigo) {
                case ("TowerDefenceCerro.Enemigos.Hobbit") :{ listaEnemigosOleada.add(new Hobbit()); break;}
                case ("TowerDefenceCerro.Enemigos.Humano") :{ listaEnemigosOleada.add(new Humano()); break;}
                case ("TowerDefenceCerro.Enemigos.Elfo") :{ listaEnemigosOleada.add(new Elfo()); break;}
                case ("TowerDefenceCerro.Enemigos.Enano") :{ listaEnemigosOleada.add(new Enano()); break;}
            }
        }

    }
    /**
     * Carga enemigos en el casillero inicial de la oleada.
     *
     * @param casillerosEnemigos Lista de casilleros del nivel donde se cargarán los enemigos.
     */
    public void cargarEnemigosCasilleroInicial(ArrayList<Casillero> casillerosEnemigos) {
        // Se cargan una determinada cantidad de enemigos segun el nivel y la oleada
        Casillero c1 = casillerosEnemigos.get(0);
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
    }
    /**
     * Obtiene el número de la oleada actual.
     *
     * @return El número de la oleada actual.
     */
    public int getNroOleada() {
        return nroOleada;
    }
    /**
     * Obtiene la lista de enemigos de la oleada.
     *
     * @return La lista de enemigos de la oleada.
     */
    public ArrayList<Enemigo> getListaEnemigosOleada() {
        return listaEnemigosOleada;
    }
    /**
     * Reinicia el número de la oleada a 1.
     */
    public void reiniciarNroOleada() {
        this.nroOleada = 1;
    }
    /**
     * Aumenta el número de la oleada en 1.
     */
    public void aumentarOleada(){
        this.nroOleada++;
    }
}
