import java.util.ArrayList;
import java.util.Scanner;

public class Nivel {
    private int nroNivel;
    private ArrayList<Coordenada> caminosEnemigos = new ArrayList<>();
    private ArrayList<Casillero> casillerosEnemigos = new ArrayList<>();
    private ArrayList<Torre> listaTorres = new ArrayList<>();
    private Mapa mapaNivel = new Mapa();

    public Nivel(int nroNivel) {
        this.nroNivel = nroNivel;
    }

    public void generarCoordCaminoEnemigos() {
        //Objetivo: Crear aleatoriamente por nivel como nos dijo el profe

        // Validar que las coordenadas sean validas

        // Ahora probamos hardcodeando algunas coordenadas
        this.caminosEnemigos.add(new Coordenada(0, 0));
        this.caminosEnemigos.add(new Coordenada(0, 1));
        this.caminosEnemigos.add(new Coordenada(1, 1));
        this.caminosEnemigos.add(new Coordenada(2, 1));
        this.caminosEnemigos.add(new Coordenada(2, 2));

        mapaNivel.generarCoordMapaRef(this.caminosEnemigos);

        this.generarCasillerosEnemigos();

    }
    public void generarCasillerosEnemigos() {
        for (Coordenada coordenada : caminosEnemigos) {
            int x = coordenada.getX();
            int y = coordenada.getY();
            casillerosEnemigos.add(new Casillero());
        }
    }

    public void menuTorre() {
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
                    torreComun.calcularCasillerosAtaque(mapaNivel.mapaRefCoord , caminosEnemigos);
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

    public void cargarEnemigosCasillero() {
        Oleada oleada1 = new Oleada(getNroNivel());
        oleada1.generarEnemigos();
        ArrayList<Enemigo> listaEnemigosOleada = oleada1.getListaEnemigosOleada();
        // Ver implementacion de cola FIFO para ir cargando enemigos en el primer casillero.
        // Porque el unico lugar donde se carga es el primer casillero de CasilleroEnemigos
        // Luego desde el primer casillero se van desplaznado por el mapa.
        Casillero c1 = casillerosEnemigos.get(0);  //.agregarEnemigo(new Humano());
        c1.agregarEnemigo(listaEnemigosOleada.get(0));
        c1.mostrarListaHumanos();
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
