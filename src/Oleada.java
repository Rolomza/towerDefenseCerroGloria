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
            nivelActual.torresAtacan();

            System.out.println("Vida del Cerro antes del ataque de los Enemigos:");
            nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria().mostarVida();
            nivelActual.reducirContadoresEnemigos(); // El error estaba aqui
            nivelActual.enemigosAtacan();
            nivelActual.moverEnemigosListos(); //Aca muevo a los enemigos, si estan en el penultimo casillero atacan y mueren
            System.out.println("Casilleros despues de mover enemigos:");
            nivelActual.mostrarCasillerosConEnemigos();
            System.out.println("Vida Post-Ataque");
            nivelActual.getCasillerosEnemigos().get(posicionCerro).getCerroGloria().mostarVida();

            count++;
            // Nota: Cuando destruyo la barrera, los enemigos no se mueven hasta la siguiente iteracion
            // porque el que chequea que la barrera este destruida es enemigosAtacan
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
                    crearNEnemigosDeTipoS(15 , "Hobbit");
                    crearNEnemigosDeTipoS(15 , "Humano");
                    break;

            }
        } else if (this.nivelActual == 2){
            switch (nroOleada){
                case 1:
                    crearNEnemigosDeTipoS(15 , "Hobbit");
                    crearNEnemigosDeTipoS(15 , "Humano");
                    crearNEnemigosDeTipoS(5 , "Elfo");
                    break;
                case 2:
                    crearNEnemigosDeTipoS(20 , "Hobbit");
                    crearNEnemigosDeTipoS(15 , "Humano");
                    crearNEnemigosDeTipoS(10 , "Elfo");
                    break;
                case 3:
                    crearNEnemigosDeTipoS(15 , "Hobbit");
                    crearNEnemigosDeTipoS(20 , "Humano");
                    crearNEnemigosDeTipoS(15 , "Elfo");
                    break;

            }
        } else if (this.nivelActual == 3){
            switch (nroOleada){
                case 1:
                    crearNEnemigosDeTipoS(15 , "Hobbit");
                    crearNEnemigosDeTipoS(15 , "Humano");
                    crearNEnemigosDeTipoS(15 , "Elfo");
                    crearNEnemigosDeTipoS(5 , "Enano");
                    break;
                case 2:
                    crearNEnemigosDeTipoS(20 , "Humano");
                    crearNEnemigosDeTipoS(15 , "Elfo");
                    crearNEnemigosDeTipoS(15 , "Enano");
                    break;
                case 3:
                    crearNEnemigosDeTipoS(20 , "Humano");
                    crearNEnemigosDeTipoS(20 , "Elfo");
                    crearNEnemigosDeTipoS(20 , "Enano");
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
                case 1: cantidadEnemigosCarga = 2;
                        break;
                case 2: cantidadEnemigosCarga = 4;
                        break;
                case 3: cantidadEnemigosCarga = 5;
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
