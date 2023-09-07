import java.sql.SQLOutput;
import java.util.Scanner;

public class Juego {
    private int puntosMagia = 300;

    // ArrayList<Mapa> mapas = new ArrayList<Mapa>();


    public void iniciarJuego() {
        // Instanciar mapa
        Mapa mapa = new Mapa();
        System.out.println("Bienvenido a Cerro de la Gloria Defense!");

        // Iniciar menus para usuario
        System.out.println("Debe posicionar sus torres en el mapa");
        // Se debe mostrar cantidad de puntos de magia
        System.out.println("Puntos de magia disponibles: " + this.getPuntosMagia());

        Nivel nivel1=new Nivel();
        nivel1.generarCasilleros();
        // mostrar mapa
        //mapa.generarCasilleros();

        Humano h1 = new Humano();


        mapa.mostrarMapa();

        // Se debe mostrar tipos de torres disponibles para comprar y su precio y caracteristicas

        this.comprarTorres();

        // Posicionar torres
            // Dar inicio al juego si está seguro

    }

    public void comprarTorres() {
        int tipoTorre;
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

            switch (tipoTorre) {
                case 1:
                    // Torre torreComun = new TorreComun();
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

    // Getters and setter

    public int getPuntosMagia() {
        return puntosMagia;
    }

    public void aumentarPuntosMagia(int puntosMagia) {
        this.puntosMagia += puntosMagia;
    }

    public void restarPuntosMagia(int puntosMagia) {
        this.puntosMagia -= puntosMagia;
    }

}