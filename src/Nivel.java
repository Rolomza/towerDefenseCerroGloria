import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Nivel {
    // Cada nivel tiene la responsabilidad de:
    // 1) Generar un mapa
    // 2) A partir del mapa generar estructura casillerosEnemigos
    // 3) Mostrar menus de interaccion para comprar torres, ponerlas, mejorarlas, e iniciar oleadas
    // 4) Genera oleadas (3) por nivel.

    private int nroNivel;
    private ArrayList<Casillero> casillerosEnemigos = new ArrayList<>();
    private ArrayList<Torre> listaTorres = new ArrayList<>();
    private Mapa mapaNivel = new Mapa();

    private Oleada oleadaNivel = new Oleada(1);
    private int puntosMagiaNivel;
    private int recompensaNivel;
    public Nivel(int nroNivel, int puntosNivel) {
        this.nroNivel = nroNivel;
        this.recompensaNivel = 0;
        this.puntosMagiaNivel = puntosNivel;
    }

    public void generarCasillerosEnemigos() {

        this.mapaNivel.generarCoordCaminoEnemigos();
        this.mapaNivel.colocarReferenciasEnMapa();
        int count =0; //sirve para saber la posicion de la lista de tuplas caminos enemigos

        for (Coordenada coordenada : this.mapaNivel.getCaminosEnemigos()) {
            int x = coordenada.getX();
            int y = coordenada.getY();
            if (count!=this.mapaNivel.getCaminosEnemigos().size()-1) {
                casillerosEnemigos.add(new Casillero(x, y));
            }else {
                casillerosEnemigos.add(new Casillero(x , y ,new Cerro() ));
            }
            count++;
        }
    }



    public void iniciarNivel() {
        menuNivel(); // Aca agrego la Torre comprada a listaTorres
        int posicionCerro = casillerosEnemigos.size()-1;
        while (oleadaNivel.getNroOleada()<=3 && casillerosEnemigos.get(posicionCerro).getCerroGloria().getVida() > 0){
            iniciarOleada();
        }

    }
    public void iniciarOleada() {

        // Colocacion de torres solo al principio de nivel.

        // Mejora de torres y colocacion de barreras solo al principio de cada oleada.

        menuOleada();

        //Iteraciones Juego
        int count = 0;
        System.out.println("Iteracion: "+count);

        int posicionCerro = casillerosEnemigos.size()-1;
        casillerosEnemigos.get(posicionCerro).getCerroGloria().mostarVida();;
        oleadaNivel.generarEnemigos();


        // Revisar el orden en el que mostramos las cosas
        while (casillerosEnemigos.get(posicionCerro).getCerroGloria().getVida() > 0){
            //si hay enemigos en mi lista enemigos por iteracion mandamos los enemigos que correspondan a la oleada
            if (!oleadaNivel.getListaEnemigosOleada().isEmpty()){
                oleadaNivel.cargarEnemigosCasilleroInicial(this.casillerosEnemigos);
            }
            if (count ==0){
                System.out.println("Iteracion: "+ count);
                mostrarTodosLosCasilleros();}
            if (!existenEnemigos()){
                break;
            }
            System.out.println("Iteracion: " + (count+1));
            torresAtacan();

            System.out.println("Vida del Cerro antes del ataque de los Enemigos:");
            casillerosEnemigos.get(posicionCerro).getCerroGloria().mostarVida();
            reducirContadoresEnemigos(); // El error estaba aqui
            enemigosAtacan();
            moverEnemigosListos(); //Aca muevo a los enemigos, si estan en el penultimo casillero atacan y mueren
            System.out.println("Casilleros despues de mover enemigos:");
            mostrarTodosLosCasilleros();
            System.out.println("Vida Post-Ataque");
            casillerosEnemigos.get(posicionCerro).getCerroGloria().mostarVida();

            count++;
            // Nota: Cuando destruyo la barrera, los enemigos no se mueven hasta la siguiente iteracion
            // porque el que chequea que la barrera este destruida es enemigosAtacan
        }
        oleadaNivel.aumentarOleada();
    }

    public void torresAtacan(){
        System.out.println("Atacan las Torres");
        // Aca iterariamos todas las torres y atacarian antes de que los enemigos se muevan
        for (Torre torreActual : listaTorres){

            if (torreActual instanceof TorreComun){
                TorreComun torreComun = (TorreComun) torreActual;
                torreComun.chequearCasillerosAtaque(this.casillerosEnemigos);
            } else if (torreActual instanceof TorreHielo) {
                TorreHielo torreHielo = (TorreHielo) torreActual;
                torreHielo.chequearCasillerosAtaque(this.casillerosEnemigos);
            } else if (torreActual instanceof TorreFuego){
                TorreFuego torreFuego = (TorreFuego) torreActual;
                torreFuego.chequearCasillerosAtaque(this.casillerosEnemigos);
            }


        }
    }

    public void reducirContadoresEnemigos() {
        for (Casillero casillero: this.casillerosEnemigos) {
            if(casillero.tieneEnemigos()) {
                casillero.reducirContadores();
                casillero.setEnemigosListosParaMoverse(); // Aca agregaba los enemigos que no era
            }
        }
    }

    public void moverEnemigosListos() {
        for (Casillero casillero: this.casillerosEnemigos) {
            if(casillero.tieneEnemigos()) {
                if (casillero.getId() < this.casillerosEnemigos.size()-1) {
                    Casillero casilleroSiguiente = this.casillerosEnemigos.get(casillero.getId()+1);
                    moverEnemigos(casillero, casilleroSiguiente);
                }
            }
        }

    }

    public void enemigosAtacan(){
        for (Casillero casillero: this.casillerosEnemigos) {
            if(casillero.tieneEnemigos() && casillero.tieneBarrera()) {
                for (ArrayList<Enemigo> listaEnemigos : casillero.getEnemigosCasillero().values()) {
                    if (casillero.getBarrera() != null) {
                        for (Enemigo enemigo : listaEnemigos) {
                            if (casillero.getBarrera().getVida() > 0) {
                                enemigo.atacar(casillero);
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


    public void moverEnemigos(Casillero casilleroActual, Casillero casilleroSiguiente) {
        ArrayList<Enemigo> listaEnemigosParaMoverse = casilleroActual.getEnemigosListosParaMoverse();
        boolean esPenultimo = false;
        if (!casilleroActual.tieneBarrera()){
            //si tiene barrera los enemigos listos para moverse no tienen que moverse

            if (!listaEnemigosParaMoverse.isEmpty()) {

                if (casilleroSiguiente.getId() == this.casillerosEnemigos.size() - 1) {
                    esPenultimo = true;
                }
                Enemigo enemigo;


                if (esPenultimo) {
                    while (!listaEnemigosParaMoverse.isEmpty()) {
                        enemigo = listaEnemigosParaMoverse.remove(0);
                        enemigo.atacar(casilleroSiguiente);
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
        }else{
            //atacar barrera

        }
    }


    public void mostrarTodosLosCasilleros(){
        for (Casillero casillero : this.casillerosEnemigos){
            casillero.mostrarEntidadesCasillero();
        }
    }

    public void menuOleada(){
         //añadir barreras solo si tenemos puntos de magia
        colocarBarreraEnMapa();
        //Mejorar torres,

    }
    public void mejorarTorre(){
            //preguntar si deseamos mejorar alguna torre
            //pasar coordenadas
            //validar que exista esa torre comparando su coordenda



    }
    public void menuNivel() {
        // Despliega menu torres
        // El usuario puede elegir entre 3 torres
        // El usuario decide donde colocar la torre, el metodo valida, si es posible, la coloca en el mapa
        // Cada vez una torre es colocada, se muestra el mapa.

        // Mejorar la torres en este metodo? Luego de cada oleada
        //mejora 1: daño
        //mejora 2: alcance que sea muuuuuuy caro

        int tipoTorre; // Numero de opcion
        boolean seguirComprando = true;

        while (seguirComprando) {

            Scanner sc = new Scanner(System.in);

            do {
                System.out.println();
                System.out.println("--- Mercado de Torres ---");
                System.out.println("1 - Torre simple: 200 ptos. magia (Ataca un enemigo a la vez)");
                System.out.println("2 - Torre hielo: 500 ptos. magia (Relentiza enemigos)");
                System.out.println("3 - Torre fuego: 1000 ptos. magia (Causa daño de area)");

                System.out.print("Ingrese numero según desee: ");
                tipoTorre = sc.nextInt();
                if (tipoTorre < 0 || tipoTorre>3){
                    System.out.println("Número inválido , ingrese un número entre 1 y 3");
                }

            }while (tipoTorre < 0 || tipoTorre>3);

            Coordenada coordTorre = ingresarYValidarCoordenadas("Torre");

            switch (tipoTorre) {
                case 1 -> {
                    TorreComun torreComun = new TorreComun(coordTorre);
                    mapaNivel.colocarRefTorre(coordTorre, "Comun"); // Desarrollar input para las coordenadas del usuario
                    this.mostrarMapaNivel();
                    this.listaTorres.add(torreComun);
                    torreComun.calcularCasillerosAtaque(this.mapaNivel);
                    //torreComun.imprimirCasillerosAtaque();
                }

                case 2 -> {
                    // Torre de Hielo
                    TorreHielo torreHielo = new TorreHielo(coordTorre);
                    mapaNivel.colocarRefTorre(coordTorre , "Hielo"); // Desarrollar input para las coordenadas del usuario
                    this.mostrarMapaNivel();
                    this.listaTorres.add(torreHielo);
                    torreHielo.calcularCasillerosAtaque(this.mapaNivel);
                    //torreHielo.imprimirCasillerosAtaque();
                }
                case 3 -> {
                    TorreFuego torreFuego = new TorreFuego(coordTorre);
                    mapaNivel.colocarRefTorre(coordTorre , "Fuego");
                    this.mostrarMapaNivel();
                    this.listaTorres.add(torreFuego);
                    torreFuego.calcularCasillerosAtaque(this.mapaNivel);
                    //torreFuego.imprimirCasillerosAtaque();
                }

            }

            System.out.println("¿Desea comprar otra torre? y/n");
            String respuesta = sc.next();
            if(respuesta.equals("n")) {
                seguirComprando = false;
            }
        }
    }

    public Boolean existenEnemigos(){

        for (Casillero casilleroActual : this.casillerosEnemigos){

            for (ArrayList<Enemigo> listaEnemigos : casilleroActual.getEnemigosCasillero().values()){
                if (!listaEnemigos.isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }

    public void colocarBarreraEnMapa(){
        if (puntosMagiaNivel >=100){

            Scanner scanner = new Scanner(System.in);
            String respuesta;
            do {
                System.out.println("¿Desea colocar una barrera? (y/n)");
                respuesta=scanner.nextLine().trim().toLowerCase();
            }while (!respuesta.equals("y") && !respuesta.equals("n"));

            if (!respuesta.equals("n")){

                System.out.println("Donde desea colocar su barrera");
                Coordenada coordBarrera = ingresarYValidarCoordenadas("Barrera");

                for (Casillero casillero : casillerosEnemigos) {
                    if (casillero.getCoordenadaCasillero().compararConCoordenada(coordBarrera)) {
                        casillero.agregarBarrera();
                        System.out.println("Se agrego Barrera: " + casillero.getBarrera().toString() + " en el casillero " + casillero.getCoordenadaCasillero().mostrarCoordenada());
                    }
                }
            }
        }
    }

    public Coordenada ingresarYValidarCoordenadas(String tipoEstructura) {
        Coordenada coordenadaEstructura;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese coordenada X: ");
            int coordX = scanner.nextInt();
            System.out.print("Ingrese coordenada Y: ");
            int coordY = scanner.nextInt();
            coordenadaEstructura = new Coordenada(coordX , coordY);
        } while (!validarCoordenada(coordenadaEstructura, tipoEstructura));
        return coordenadaEstructura;
    }

    public boolean validarCoordenada(Coordenada coordenada , String tipoEstructura){

        // Cambiar el 4 por las dimensiones del mapa
        if (( coordenada.getX() < 0 || coordenada.getX() > 4) || (coordenada.getY() < 0 || coordenada.getY() > 4)){
            System.out.println("Coordenada NO válida (fuera de rango del mapa).");
            return false;
        }

        // Lista de casilleros servirá para determinar si en determinado casillero existe o no barrera/torre para evitar tener 2 estructuras en el mismo casillero

        if (tipoEstructura.equals("Barrera")){
            int ultimaCoordenada = this.mapaNivel.getCaminosEnemigos().size()-1;
            Coordenada coordenadaCerro = this.mapaNivel.getCaminosEnemigos().get(ultimaCoordenada);

            if (!coordenada.compararConCoordenada(coordenadaCerro)) {
                for (Coordenada coordMapa : this.mapaNivel.getCaminosEnemigos()) {
                    if (coordMapa.compararConCoordenada(coordenada)) {
                        if (!buscarCasilleroPorCoordenada(coordenada).tieneBarrera()) {
                            return true;
                        } else {
                            System.out.println("No puedes poner mas de 1 barrera por casillero.");
                            return false;
                        }
                    }
                }
                System.out.println("No puedes poner barrera fuera del camino de enemigos.");
                return false;
            } else {
                System.out.println("No puedes colocar barrera en el mismo casillero del Cerro.");
                return false;
            }
        }

        if (tipoEstructura.equals("Torre")){
            int coordX = coordenada.getX();
            int coordY = coordenada.getY();

            if (this.mapaNivel.getMapaRefCoord()[coordX][coordY].equals("Tc")) {
                System.out.println("No puedes colocar torre ya hay una Tc en el casillero.");
                return false;
            } else if (this.mapaNivel.getMapaRefCoord()[coordX][coordY].equals("Th")) {
                System.out.println("No puedes colocartorre ya hay Th en el casillero.");
                return false;
            } else if (this.mapaNivel.getMapaRefCoord()[coordX][coordY].equals("Tf")) {
                System.out.println("No puedes colocar ya hay Tf en el casillero.");
                return false;
            }


            if (this.mapaNivel.getMapaRefCoord()[coordX][coordY].equals("C")) {
                System.out.println("No puedes colocar la torre en el camino de enemigos.");
                return false;
            }
        }
        return true;
    }

    public Mapa getMapaNivel() {
        return mapaNivel;
    }

    public void mostrarMapaNivel() {
        mapaNivel.mostrarMapa();
    }

    public int getRecompensaNivel() {
        return recompensaNivel;
    }

    public int getNroNivel() {
        return nroNivel;
    }

    public Casillero buscarCasilleroPorCoordenada(Coordenada coordenada) {
        for (Casillero casillero : this.casillerosEnemigos) {
            if (casillero.getCoordenadaCasillero().compararConCoordenada(coordenada)) {
                return casillero;
            }
        }
        return null;
    }
}
