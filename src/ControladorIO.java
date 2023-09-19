import java.util.ArrayList;
import java.util.Scanner;

public class ControladorIO {

    public void mostrarMenuNivel(Nivel nivelActual) {
        // Despliega menu torres
        // El usuario puede elegir entre 3 torres
        // El usuario decide donde colocar la torre, el metodo valida, si es posible, la coloca en el mapa
        // Cada vez una torre es colocada, se muestra el mapa.
        Scanner scanner = new Scanner(System.in);

        int opcionSeleccionada;
        int tipoTorre;
        boolean seguirEnMenuNivel = true;

        do {
            boolean noComprar = false;
            System.out.println();
            System.out.println("--- MERCADO DE TORRES ---");
            System.out.println("Nivel: " + nivelActual.getNroNivel());
            System.out.println("Puntos de magia: " + nivelActual.getPuntosMagia());
            nivelActual.getMapaNivel().mostrarMapa();

            System.out.println("1 - Torre comun: 200 ptos. magia (Ataca un enemigo a la vez)");
            System.out.println("2 - Torre hielo: 400 ptos. magia (Relentiza enemigos)");
            System.out.println("3 - Torre fuego: 1000 ptos. magia (Causa daño de area)");

            if (!nivelActual.getListaTorres().isEmpty()) {
                System.out.println("4 - No comprar más torres.");
                noComprar = true;
            }
            System.out.print("Seleccione una opción: ");
            opcionSeleccionada = scanner.nextInt();

            if (noComprar && opcionSeleccionada == 4) {
                break;
            }

            if (opcionSeleccionada < 1 || opcionSeleccionada > 3) {
                System.out.println("Opcion NO valida.");
            } else {

                if (validarSuficientesPuntosMagia(opcionSeleccionada, nivelActual.getPuntosMagia())) {
                    Coordenada coordTorre = ingresarYValidarCoordenadas("Torre", nivelActual);
                    nivelActual.colocarTorre(opcionSeleccionada, coordTorre);
                    nivelActual.getMapaNivel().mostrarMapa();
                } else {
                    continue;
                }

                if (nivelActual.getPuntosMagia() >= 200) {
                    System.out.print("Deseas comprar otra torre? (y/n): ");
                    String otraTorre = scanner.next();
                    if (otraTorre.equals("n")) {
                        seguirEnMenuNivel = false;
                    }
                } else {
                    System.out.println("No tiene suficientes puntos de magia para seguir comprando Torres.");
                    seguirEnMenuNivel = false;
                }
            }

        }while (seguirEnMenuNivel);
    }


    public void mostrarMenuOleada(Nivel nivelActual) {
        // El usuario debe poder elegir entre mejorar torres y/o colocar barreras (solo al finalizar cada oleada)
        Scanner scanner = new Scanner(System.in);
        boolean seguirEnMenuOleada = true;
        int opcionSeleccionada;

        do {
            System.out.println(" --- MENU OLEADA ---");
            System.out.println("Oleada: " + nivelActual.getOleadaNivel().getNroOleada());
            System.out.println("Puntos de magia: " + nivelActual.getPuntosMagia());
            System.out.println("1 - Mejorar Torres.");
            System.out.println("2 - Comprar y colocar barrera. (100 puntos de magia c/u)");
            System.out.println("3 - Iniciar Oleada.");
            System.out.print("Seleccione una opción: ");

            opcionSeleccionada = scanner.nextInt();
            if (opcionSeleccionada < 4 && opcionSeleccionada > 0) {
                if (opcionSeleccionada == 3) {
                    seguirEnMenuOleada = false;
                }

                switch (opcionSeleccionada) {
                    case 1:
                        mostrarMenuTorres(nivelActual);
                        break;
                    case 2:
                        if (nivelActual.getPuntosMagia() < 100) {
                            System.out.println("No tienes puntos de magia suficientes para comprar Barreras.");
                        } else {
                            System.out.println(" --- COLOCACION BARRERA ---");
                            System.out.println("Puntos de magia: " + nivelActual.getPuntosMagia());
                            nivelActual.colocarBarrera();
                        }
                        break;
                }
            } else {
                System.out.println("Opción NO valida.");
            }
        } while(seguirEnMenuOleada);
    }


    public void mostrarMenuTorres(Nivel nivelActual) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- MEJORAR TORRES ---");
        ArrayList<String> listaNombreTorres = new ArrayList<>();

        for (Torre torre : nivelActual.getListaTorres()) {
            listaNombreTorres.add(torre.toString());
            System.out.println("Torre: " + torre.toString() + " | Daño: " + torre.getDanio() + " | Alcance: " + torre.getAlcanceAtaque());
        }

        boolean nombreValido = false;
        do {
            System.out.print("Ingrese el nombre de la torre que desea mejorar: ");
            String nombreTorre = scanner.next();

            if (listaNombreTorres.contains(nombreTorre)) {
                nombreValido = true;
                Torre torreAMejorar = nivelActual.buscarTorrePorNombre(nombreTorre);

                System.out.println(" --- Mejorar Torre "+ torreAMejorar.toString() + " ---");
                boolean opcionValida = false;
                do {
                    System.out.println("1 - +50 Daño | 500 Ptos Magia.");
                    System.out.println("2 - +1 Alcance | 1000 Ptos Magia");
                    System.out.println("3 - Volver a menu Oleada.");
                    System.out.print("Ingrese número segun mejora deseada: ");
                    int tipoMejora = scanner.nextInt();

                    switch (tipoMejora) {
                        case 1:
                            if (nivelActual.getPuntosMagia() >= 500) {
                                nivelActual.mejorarTorre(torreAMejorar, tipoMejora);
                                opcionValida = true;
                            } else {
                                System.out.println("Necesitas al menos 500 Ptos Magia para realizar esta mejora.");
                            }
                            break;
                        case 2:
                            if (nivelActual.getPuntosMagia() >= 1000) {
                                nivelActual.mejorarTorre(torreAMejorar, tipoMejora);
                                opcionValida = true;
                            } else {
                                System.out.println("Necesitas al menos 1000 Ptos Magia para realizar esta mejora.");
                            }
                            break;
                        case 3:
                            System.out.println("Volviendo a Menu oleada...");
                            opcionValida = true;
                            break;
                        default:
                            System.out.println("Opcion NO valida.");
                    }
                } while(!opcionValida);
            } else {
                System.out.println("Nombre NO valido.");
            }
        } while(!nombreValido);
    }

    public boolean validarSuficientesPuntosMagia(int tipoTorre, int puntosMagia){

        boolean dineroSuficiente = true;

        switch (tipoTorre) {
            case 1 -> {
                if (puntosMagia < 200) {
                    System.out.println("No te alcanza para la Torre Comun");
                    dineroSuficiente = false;
                }
            }
            case 2 -> {
                if (puntosMagia < 400) {
                    System.out.println("No te alcanza para la Torre de Hielo");
                    dineroSuficiente = false;
                }
            }
            case 3 -> {
                if (puntosMagia < 1000) {
                    System.out.println("No te alcanza para la Torre de Fuego");
                    dineroSuficiente = false;
                }
            }
        }
        return dineroSuficiente;
    }


    public Coordenada ingresarYValidarCoordenadas(String tipoEstructura, Nivel nivelActual) {
        Coordenada coordenadaEstructura;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese coordenada X: ");
            int coordX = scanner.nextInt();
            System.out.print("Ingrese coordenada Y: ");
            int coordY = scanner.nextInt();
            coordenadaEstructura = new Coordenada(coordX , coordY);
        } while (!validarCoordenada(coordenadaEstructura, tipoEstructura, nivelActual));
        return coordenadaEstructura;
    }

    public boolean validarCoordenada(Coordenada coordenada , String tipoEstructura, Nivel nivelActual){

        // Cambiar el 4 por las dimensiones del mapa
        if (( coordenada.getX() < 0 || coordenada.getX() > nivelActual.getMapaNivel().getMapaRefCoord().length) ||
                (coordenada.getY() < 0 || coordenada.getY() > nivelActual.getMapaNivel().getMapaRefCoord()[0].length)){
            System.out.println("Coordenada NO válida (fuera de rango del mapa).");
            return false;
        }

        // Lista de casilleros servirá para determinar si en determinado casillero existe o no barrera/torre para evitar tener 2 estructuras en el mismo casillero

        if (tipoEstructura.equals("Barrera")){
            int ultimaCoordenada = nivelActual.getMapaNivel().getCaminosEnemigos().size()-1;
            Coordenada coordenadaCerro = nivelActual.getMapaNivel().getCaminosEnemigos().get(ultimaCoordenada);

            if (!coordenada.compararConCoordenada(coordenadaCerro)) {
                for (Coordenada coordMapa : nivelActual.getMapaNivel().getCaminosEnemigos()) {
                    if (coordMapa.compararConCoordenada(coordenada)) {
                        if (!nivelActual.buscarCasilleroPorCoordenada(coordenada).tieneBarrera()) {
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

            if (nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].equals("Tc ")) {
                System.out.println("No puedes colocar torre ya hay una Tc en el casillero.");
                return false;
            } else if (nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].equals("Th ")) {
                System.out.println("No puedes colocartorre ya hay Th en el casillero.");
                return false;
            } else if (nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].equals("Tf ")) {
                System.out.println("No puedes colocar ya hay Tf en el casillero.");
                return false;
            }


            if (nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].contains("C") ||
                    nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].contains("E") ||
                    nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].contains("CG")) {
                System.out.println("No puedes colocar la torre en el camino de enemigos.");
                return false;
            }
        }
        return true;
    }
}
