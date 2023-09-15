import java.util.ArrayList;
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

    public Nivel(int nroNivel) {
        this.nroNivel = nroNivel;
    }

    public void generarCasillerosEnemigos() {

        this.mapaNivel.generarCoordCaminoEnemigos();
        this.mapaNivel.colocarReferenciasEnMapa();

        for (Coordenada coordenada : this.mapaNivel.getCaminosEnemigos()) {
            int x = coordenada.getX();
            int y = coordenada.getY();
            casillerosEnemigos.add(new Casillero());
        }

        // Agrega al ultimo casillero el cerro gloria
        casillerosEnemigos.get(casillerosEnemigos.size() - 1).setCerroGloria(new Cerro());
    }

    public void iniciarOleadas() {
        oleadaNivel.generarEnemigos();
        oleadaNivel.cargarEnemigosCasilleroInicial(this.casillerosEnemigos);
    }

    public void iniciarNivel() {
        // Cada ciclo dura 1 oleada, donde se comprueba que:
        //                                 1 La listaEnemigos de oleada este vacia
        //                                 2 No hayan enemigos en ningun casillero
        //                                 3 El cerro siga con vida
        //
        iniciarOleadas();
        recorrerCasillleros();
    }

    public void moverEnemigos(Casillero casilleroActual, Casillero casilleroSiguiente) {
        // Verifica enemigos con contador de iteraciones listos para pasar al siguiente casillero
        ArrayList<Enemigo> enemigosListosParaMoverse = new ArrayList<>();

        enemigosListosParaMoverse.add(casilleroActual.getEnemigosCasillero().get("Humano").get(0));
        // Primer approach para elegir humano y moverlo
        // Elegir enemigo a mover: Prueba basica sin tener en cuenta iteraciones, solo mover.

        casilleroActual.eliminarEnemigo(enemigosListosParaMoverse.get(0));
        if (casilleroSiguiente.getId() == this.casillerosEnemigos.size() - 1) {
            // Enemigo ataque el cerro y se elimine
            System.out.println("Ultimo casillero");
        }

        casilleroSiguiente.agregarEnemigo(enemigosListosParaMoverse.get(0));
    }

    public void recorrerCasillleros() {
        // Primer approach: Recorro lista casillerosEnemigos
        //                  Si hay enemigos en dicho casillero, lo muevo al siguiente
        //                  (Por ahora evito tema de velocidades distintas de desplazamiento
        for (Casillero casillero: this.casillerosEnemigos) {
            if(casillero.tieneEnemigos()) {
                if (casillero.getId() < this.casillerosEnemigos.size()-1) {
                    Casillero casilleroSiguiente = this.casillerosEnemigos.get(casillero.getId()+1);
                    // Antes de mover
                    casillero.mostrarEntidadesCasillero();
                    casilleroSiguiente.mostrarEntidadesCasillero();
                    moverEnemigos(casillero, casilleroSiguiente);
                    // Despues de mover
                    casillero.mostrarEntidadesCasillero();
                    casilleroSiguiente.mostrarEntidadesCasillero();
                }
            }
        }
    }

    public void menuTorre() {
        // Despliega menu torres
        // El usuario puede elegir entre 3 torres
        // El usuario decide donde colocar la torre, el metodo valida, si es posible, la coloca en el mapa
        // Cada vez una torre es colocada, se muestra el mapa.

        // Mejorar la torres en este metodo? Luego de cada oleada

        int tipoTorre; // Numero de opcion
        boolean seguirComprando = true;

        System.out.println();
        System.out.println("--- Mercado de Torres ---");
        System.out.println("1 - Torre simple: 200 ptos. magia (Ataca un enemigo a la vez)");
        System.out.println("2 - Torre hielo: 500 ptos. magia (Relentiza enemigos)");
        System.out.println("3 - Torre fuego: 1000 ptos. magia (Causa daño de area)");

        while (seguirComprando) {
            System.out.print("Ingrese numero según desee: ");
            Scanner sc = new Scanner(System.in);

            tipoTorre = sc.nextInt();

            // Verificar si podemos comprar la torre

            // Si se puede comprar, se crea un objeto de esa clase
            // Se posiciona en mapa

            System.out.println("Ingrese posicion de las coordenadas torre");
            System.out.println("Ingrese la X");
            int posX = sc.nextInt();
            System.out.println("Ingrese la Y");
            int posY = sc.nextInt();

            switch (tipoTorre) {
                case 1:
                    TorreComun torreComun = new TorreComun(new Coordenada(posX,posY));
                    mapaNivel.colocarRefTorre(new Coordenada(posX, posY)); // Desarrollar input para las coordenadas del usuario
                    this.mostrarMapaNivel();
                    this.listaTorres.add(torreComun);
                    torreComun.calcularCasillerosAtaque(this.mapaNivel);
                    torreComun.imprimirCasillerosAtaque();

                    // torreComun.posicionarEnMapa(Mapa);
                    break;
                case 2:
                    // ....
                    break;
                default:
                    System.out.println("Ingrese un numero de torre válido por favor...");
            }

            System.out.println("¿Desea comprar otra torre? y/n");
            String respuesta = sc.next();
            if(respuesta.equals("n")) {
                seguirComprando = false;
            }
        }

    }

    public Mapa getMapaNivel() {
        return mapaNivel;
    }

    public void mostrarMapaNivel() {
        mapaNivel.mostrarMapa();
    }

    public int getNroNivel() {
        return nroNivel;
    }

    public void mostrarCasillero() {
        // Chau Aida
    }
}
