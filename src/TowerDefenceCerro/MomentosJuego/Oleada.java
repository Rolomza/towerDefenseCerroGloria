package TowerDefenceCerro.MomentosJuego;

import TowerDefenceCerro.Casillero;
import TowerDefenceCerro.Cerro;
import TowerDefenceCerro.Enemigos.*;
import TowerDefenceCerro.Torres.Torre;

import java.util.ArrayList;
import java.util.Random;

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

    public Oleada(int nroNivel) {
        this.nivelActual = nroNivel;
        this.nroOleada = 1;
    }

    public void iniciarOleada(Nivel nivelActual) {
        //Iteraciones TowerDefenceCerro.MomentosJuego.Juego
        int count = 0;

        int posicionCerro = nivelActual.getCasillerosEnemigos().size()-1;
        Cerro cerroGloria = nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria();
        nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria().mostrarVida();;
        generarEnemigos();

        // Corazon del juego
        while (cerroGloria.getVida() > 0){

            if (!this.listaEnemigosOleada.isEmpty()){
                // Mientras hay enemigos restantes para generar en la oleada
                cargarEnemigosCasilleroInicial(nivelActual.getCasillerosEnemigos());
            }

            if (!nivelActual.existenEnemigos()){
                break;
            }

            System.out.println("--- ITERACION : " + (count+1) + " ---");
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
    }


    // Revisar si funciona la nueva implementacion de torres atacan
    public void torresAtacan(Nivel nivelActual){
        // Aca iterariamos todas las torres y atacarian antes de que los enemigos se muevan
        for (Torre torreActual : nivelActual.getListaTorres()){
            torreActual.imprimirCasillerosAtaque();
            // Revisar casillero, esta puesto para evitar romper ataque enemigo
            Casillero casillero = new Casillero(99,99);
            torreActual.atacar(nivelActual, casillero);
        }
    }

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

    public void reducirContadoresEnemigos(Nivel nivelActual) {
        for (Casillero casillero: nivelActual.getCasillerosEnemigos()) {
            if(casillero.tieneEnemigos()) {
                casillero.reducirContadores();
                casillero.setEnemigosListosParaMoverse(); // Aca agregaba los enemigos que no era
            }
        }
    }

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

    public void crearNEnemigosDeTipoS(int n , String tipoEnemigo){

        for (int i=0 ; i < n; i++){
            switch (tipoEnemigo) {
                case ("TowerDefenceCerro.Enemigos.Hobbit") -> listaEnemigosOleada.add(new Hobbit());
                case ("TowerDefenceCerro.Enemigos.Humano") -> listaEnemigosOleada.add(new Humano());
                case ("TowerDefenceCerro.Enemigos.Elfo") -> listaEnemigosOleada.add(new Elfo());
                case ("TowerDefenceCerro.Enemigos.Enano") -> listaEnemigosOleada.add(new Enano());
            }
        }

    }

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
