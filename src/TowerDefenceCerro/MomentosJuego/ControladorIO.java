package TowerDefenceCerro.MomentosJuego;

import TowerDefenceCerro.Coordenada;
import TowerDefenceCerro.Torres.Torre;
import TowerDefenceCerro.Torres.TorreComun;
import TowerDefenceCerro.Torres.TorreFuego;
import TowerDefenceCerro.Torres.TorreHielo;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorIO {
    public void mostrarMenuNivel(Nivel nivelActual) {
        Scanner scanner = new Scanner(System.in);
        int opcionSeleccionada;
        int tipoTorre;
        boolean seguirEnMenuNivel = true;
        TorreComun torreComun = new TorreComun();
        TorreHielo torreHielo = new TorreHielo();
        TorreFuego torreFuego = new TorreFuego();

        do {
            try {
                boolean noComprar = false;
                System.out.println();
                System.out.println("--- MERCADO DE TORRES ---");
                System.out.println("Nivel: " + nivelActual.getNroNivel());
                System.out.println("Puntos de magia: " + nivelActual.getPuntosMagia());
                nivelActual.getMapaNivel().mostrarMapa();

                System.out.println("1 - Torre comun: " + torreComun.getCosteTorre() + " ptos. magia (Ataca un enemigo a la vez)");
                System.out.println("2 - Torre hielo: " + torreHielo.getCosteTorre() + " ptos. magia (Relentiza enemigos)");
                System.out.println("3 - Torre fuego: " + torreFuego.getCosteTorre() + " ptos. magia (Causa daño de área)");

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
                    System.out.println("Opcion NO válida.");
                } else {

                    if (validarSuficientesPuntosMagia(opcionSeleccionada, nivelActual.getPuntosMagia())) {
                        Coordenada coordTorre = ingresarYValidarCoordenadas("Torre", nivelActual);
                        nivelActual.colocarTorre(opcionSeleccionada, coordTorre);
                        nivelActual.getMapaNivel().mostrarMapa();
                    } else {
                        continue;
                    }

                    if (nivelActual.getPuntosMagia() >= torreComun.getCosteTorre()) {
                        System.out.print("Deseas comprar otra torre? (y/n): ");
                        String otraTorre = scanner.next();

                        if (otraTorre.equalsIgnoreCase("n")) {
                            seguirEnMenuNivel = false;
                        } else if (!otraTorre.equalsIgnoreCase("y")) {
                            System.out.println("Respuesta NO válida, se asumirá como 'n'.");
                            seguirEnMenuNivel = false;
                        }
                    } else {
                        System.out.println("No tienes suficientes puntos de magia para seguir comprando Torres.");
                        seguirEnMenuNivel = false;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar números enteros para la selección.");
                scanner.nextLine(); // Limpiar el búfer del scanner para evitar un bucle infinito
            }
        } while (seguirEnMenuNivel);
    }


    public void mostrarMenuOleada(Nivel nivelActual) {
        Scanner scanner = new Scanner(System.in);
        boolean seguirEnMenuOleada = true;
        int opcionSeleccionada;

        do {
            try {
                System.out.println(" --- MENU OLEADA ---");
                System.out.println("Oleada: " + nivelActual.getOleadaNivel().getNroOleada() + " Nivel: " + nivelActual.getNroNivel());
                System.out.println("Puntos de magia: " + nivelActual.getPuntosMagia());
                nivelActual.getMapaNivel().mostrarMapa();
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
                    System.out.println("Opción NO válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero.");
                scanner.nextLine(); // Limpiar el búfer del scanner para evitar un bucle infinito
            }
        } while (seguirEnMenuOleada);
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
            try {
                System.out.print("Ingrese el nombre de la torre que desea mejorar: ");
                String nombreTorre = scanner.next();

                if (listaNombreTorres.contains(nombreTorre)) {
                    nombreValido = true;
                    Torre torreAMejorar = nivelActual.buscarTorrePorNombre(nombreTorre);

                    System.out.println(" --- Mejorar Torre " + torreAMejorar.toString() + " ---");
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
                    } while (!opcionValida);
                } else {
                    System.out.println("Nombre NO valido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar números enteros para la selección.");
                scanner.nextLine(); // Limpiar el búfer del scanner para evitar un bucle infinito
            }
        } while (!nombreValido);
    }

    public boolean validarSuficientesPuntosMagia(int tipoTorre, int puntosMagia){

        TorreComun torreComun = new TorreComun();
        TorreHielo torreHielo = new TorreHielo();
        TorreFuego torreFuego = new TorreFuego();
        boolean dineroSuficiente = true;

        switch (tipoTorre) {
            case 1 : {
                if (puntosMagia < torreComun.getCosteTorre()) {
                    System.out.println("No te alcanza para la Torre Comun");
                    dineroSuficiente = false;
                }
                break;
            }
            case 2 : {
                if (puntosMagia < torreHielo.getCosteTorre()) {
                    System.out.println("No te alcanza para la Torre de Hielo");
                    dineroSuficiente = false;
                }
                break;
            }
            case 3 : {
                if (puntosMagia < torreFuego.getCosteTorre()) {
                    System.out.println("No te alcanza para la Torre de Fuego");
                    dineroSuficiente = false;
                }
                break;
            }
        }
        return dineroSuficiente;
    }



    public Coordenada ingresarYValidarCoordenadas(String tipoEstructura, Nivel nivelActual) {
        Coordenada coordenadaEstructura;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Ingrese coordenada X: ");
                int coordX = scanner.nextInt();
                System.out.print("Ingrese coordenada Y: ");
                int coordY = scanner.nextInt();
                coordenadaEstructura = new Coordenada(coordX, coordY);

                if (validarCoordenada(coordenadaEstructura, tipoEstructura, nivelActual)) {
                    break; // Salir del bucle si la coordenada es válida
                } else {
                    System.out.println("Coordenada no válida. Inténtelo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar números enteros para las coordenadas.");
                scanner.nextLine(); // Limpiar el búfer del scanner para evitar un bucle infinito
            }
        }

        return coordenadaEstructura;
    }

    public boolean validarCoordenada(Coordenada coordenada , String tipoEstructura, Nivel nivelActual){

        // Cambiar el 4 por las dimensiones del mapa
        if (( coordenada.getX() < 0 || coordenada.getX() > nivelActual.getMapaNivel().getMapaRefCoord().length - 1 ) ||
                (coordenada.getY() < 0 || coordenada.getY() > nivelActual.getMapaNivel().getMapaRefCoord()[0].length - 1)){
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

            if (nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].contains("Tc")) {
                System.out.println("No puedes colocar torre ya hay una Tc en el casillero.");
                return false;
            } else if (nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].contains("Th")) {
                System.out.println("No puedes colocartorre ya hay Th en el casillero.");
                return false;
            } else if (nivelActual.getMapaNivel().getMapaRefCoord()[coordX][coordY].contains("Tf")) {
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
