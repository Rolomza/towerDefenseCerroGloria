import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    public void mostrarMenuNivel(Nivel nivelActual) {
        // Despliega menu torres
        // El usuario puede elegir entre 3 torres
        // El usuario decide donde colocar la torre, el metodo valida, si es posible, la coloca en el mapa
        // Cada vez una torre es colocada, se muestra el mapa.
        Scanner scanner = new Scanner(System.in);

        int opcionSeleccionada;
        int tipoTorre;
        boolean seguirEnMenuNivel = true;
        TorreComun torreComun = new TorreComun();
        TorreHielo torreHielo = new TorreHielo();
        TorreFuego torreFuego = new TorreFuego();
        do {
            boolean noComprar = false;
            System.out.println();
            System.out.println("--- MERCADO DE TORRES ---");
            System.out.println("Nivel: " + nivelActual.getNroNivel());
            System.out.println("Puntos de magia: " + nivelActual.getPuntosMagia());
            nivelActual.getMapaNivel().mostrarMapa();

            System.out.println("1 - Torre comun: " +  torreComun.getCosteTorre() + " ptos. magia (Ataca un enemigo a la vez)");
            System.out.println("2 - Torre hielo: " +  torreHielo.getCosteTorre() + " ptos. magia (Relentiza enemigos)");
            System.out.println("3 - Torre fuego: " +  torreFuego.getCosteTorre() + " ptos. magia (Causa daño de area)");

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
                    Coordenada coordTorre = nivelActual.ingresarYValidarCoordenadas("Torre");
                    nivelActual.colocarTorre(opcionSeleccionada, coordTorre);
                    nivelActual.getMapaNivel().mostrarMapa();
                } else {
                    continue;
                }

                if (nivelActual.getPuntosMagia() >= torreComun.getCosteTorre()) {
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
            System.out.println("Oleada: " + nivelActual.getOleadaNivel().getNroOleada() + " Nivel: " + nivelActual.getNroNivel());
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

        TorreComun torreComun = new TorreComun();
        TorreHielo torreHielo = new TorreHielo();
        TorreFuego torreFuego = new TorreFuego();
        boolean dineroSuficiente = true;

        switch (tipoTorre) {
            case 1 -> {
                if (puntosMagia < torreComun.getCosteTorre()) {
                    System.out.println("No te alcanza para la Torre Comun");
                    dineroSuficiente = false;
                }
            }
            case 2 -> {
                if (puntosMagia < torreHielo.getCosteTorre()) {
                    System.out.println("No te alcanza para la Torre de Hielo");
                    dineroSuficiente = false;
                }
            }
            case 3 -> {
                if (puntosMagia < torreFuego.getCosteTorre()) {
                    System.out.println("No te alcanza para la Torre de Fuego");
                    dineroSuficiente = false;
                }
            }
        }
        return dineroSuficiente;
    }
}
