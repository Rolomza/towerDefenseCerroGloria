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

    public void iniciarOleada(Nivel nivelActual) {
        //Iteraciones Juego
        int count = 0;
        System.out.println("Iteracion: "+count);

        int posicionCerro = nivelActual.getCasillerosEnemigos().size()-1;
        nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria().mostarVida();;
        generarEnemigos();


        // Revisar el orden en el que mostramos las cosas
        while (nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria().getVida() > 0){
            //si hay enemigos en mi lista enemigos por iteracion mandamos los enemigos que correspondan a la oleada
            if (!this.listaEnemigosOleada.isEmpty()){
                cargarEnemigosCasilleroInicial(nivelActual.getCasillerosEnemigos());
            }
            if (count ==0){
                System.out.println("Iteracion: "+ count);
                nivelActual.mostrarCasillerosConEnemigos();}
            if (!nivelActual.existenEnemigos()){
                break;
            }
            System.out.println("Iteracion: " + (count+1));
            torresAtacan(nivelActual);

            System.out.println("Vida del Cerro antes del ataque de los Enemigos:");
            nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria().mostarVida();
            reducirContadoresEnemigos(nivelActual); // El error estaba aqui
            enemigosAtacan(nivelActual);
            moverEnemigosListos(nivelActual); //Aca muevo a los enemigos, si estan en el penultimo casillero atacan y mueren
            System.out.println("Casilleros despues de mover enemigos:");
            nivelActual.mostrarCasillerosConEnemigos();
            System.out.println("Vida Post-Ataque");
            nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria().mostarVida();

            count++;
            // Nota: Cuando destruyo la barrera, los enemigos no se mueven hasta la siguiente iteracion
            // porque el que chequea que la barrera este destruida es enemigosAtacan
        }
    }

    public void torresAtacan(Nivel nivelActual){
        System.out.println("Atacan las Torres");
        // Aca iterariamos todas las torres y atacarian antes de que los enemigos se muevan
        for (Torre torreActual : nivelActual.getListaTorres()){

            if (torreActual instanceof TorreComun){
                TorreComun torreComun = (TorreComun) torreActual;
                torreComun.chequearCasillerosAtaque(nivelActual);
            } else if (torreActual instanceof TorreHielo) {
                TorreHielo torreHielo = (TorreHielo) torreActual;
                torreHielo.chequearCasillerosAtaque(nivelActual);
            } else if (torreActual instanceof TorreFuego){
                TorreFuego torreFuego = (TorreFuego) torreActual;
                torreFuego.chequearCasillerosAtaque(nivelActual);
            }
        }
    }

    public void enemigosAtacan(Nivel nivelActual){
        for (Casillero casillero: nivelActual.getCasillerosEnemigos()) {
            if(casillero.tieneEnemigos() && casillero.tieneBarrera()) {
                for (ArrayList<Enemigo> listaEnemigos : casillero.getEnemigosCasillero().values()) {
                    if (casillero.getBarrera() != null) {
                        for (Enemigo enemigo : listaEnemigos) {
                            if (casillero.getBarrera().getVida() > 0) {
                                enemigo.atacar(casillero, nivelActual);
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
                        enemigo.atacar(casilleroSiguiente, nivelActual);
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
