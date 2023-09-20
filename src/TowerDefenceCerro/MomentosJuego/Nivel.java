package TowerDefenceCerro.MomentosJuego;

import TowerDefenceCerro.Barrera;
import TowerDefenceCerro.Casillero;
import TowerDefenceCerro.Cerro;
import TowerDefenceCerro.Coordenada;
import TowerDefenceCerro.Enemigos.Enemigo;
import TowerDefenceCerro.Torres.Torre;
import TowerDefenceCerro.Torres.TorreComun;
import TowerDefenceCerro.Torres.TorreFuego;
import TowerDefenceCerro.Torres.TorreHielo;

import java.util.ArrayList;

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
    private ControladorIO entradaSalidaUsuario = new ControladorIO();
    private Oleada oleadaNivel = new Oleada(1);
    private int puntosMagia;
    private boolean derrotado = false;

    public Nivel(int nroNivel) {
        this.nroNivel = nroNivel;
        this.puntosMagia = 1000;
    }

    public void generarCasillerosEnemigos() {

        this.mapaNivel.generarMapaAleatorio();
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

        colocarIdsCasillerosMapa();
    }


    public void colocarIdsCasillerosMapa() {
        ArrayList<Coordenada> coordenadasCasilleros = this.mapaNivel.getCaminosEnemigos();
        for (Coordenada coordenada : coordenadasCasilleros) {
            Casillero casillero = buscarCasilleroPorCoordenada(coordenada);
            int coordXCasillero = coordenada.getX();
            int coordYCasillero = coordenada.getY();
            if (casillero.getId() < 10) {
                this.mapaNivel.getMapaRefCoord()[coordXCasillero][coordYCasillero] = " "+casillero.toString() + " ";
            } else {
                this.mapaNivel.getMapaRefCoord()[coordXCasillero][coordYCasillero] = casillero.toString() + " ";
            }

        }
    }

    public void iniciarNivel() {
        int posicionCerro = casillerosEnemigos.size()-1;
        Cerro cerro = casillerosEnemigos.get(posicionCerro).getCerroGloria();

        if (this.nroNivel!=1){
            reacomodarTorres();
        }
        entradaSalidaUsuario.mostrarMenuNivel(this); // Aca agrego la TowerDefenceCerro.Torres.Torre comprada a listaTorres
        oleadaNivel.reiniciarNroOleada();

        while (oleadaNivel.getNroOleada()<=3){
            if (cerro.getVida() > 0) {
                entradaSalidaUsuario.mostrarMenuOleada(this);
                oleadaNivel.iniciarOleada(this);
                oleadaNivel.aumentarOleada();

            } else {
                System.out.println("Llegaste hasta el Nivel: " + nroNivel + "| Oleada: " + oleadaNivel.getNroOleada());
                this.derrotado = true;
                break;
            }
        }
    }

    public void reacomodarTorres(){
        for (Torre torreActual: this.listaTorres){
            this.puntosMagia= torreActual.getCosteTorre() + this.puntosMagia;
            int posX=torreActual.getCoordenadaTorre().getX();
            int posY=torreActual.getCoordenadaTorre().getY();
            this.mapaNivel.getMapaRefCoord()[posX][posY]= " . ";
        }
        listaTorres.clear();

    }

    public void mostrarCasillerosConEnemigos() {
        for (Casillero casillero : this.casillerosEnemigos) {
            if (casillero.tieneEnemigos()) {
                casillero.mostrarEntidadesCasillero();
            }
        }
    }

    public void mejorarTorre(Torre torreAMejorar, int tipoMejora){
        switch (tipoMejora) {
            case 1:
                torreAMejorar.aumentarDanio(50);
                restarPuntosMagia(500);
                System.out.println("Mejora de +50 Daño a " + torreAMejorar.toString() + " aplicado.");
                break;
            case 2:
                torreAMejorar.aumentarAlcance();
                torreAMejorar.calcularCoordenadasCasillerosAtaque(this);
                restarPuntosMagia(1000);
                System.out.println("Mejora de +1 Alcance de ataque a " + torreAMejorar.toString() + " aplicado.");
                break;
        }
    }

    public Torre buscarTorrePorNombre(String nombreTorre) {
        for (Torre torre: this.listaTorres) {
            if (torre.toString().equals(nombreTorre)) {
                return torre;
            }
        }
        return null;
    }

    public void colocarTorre(int nroTorre, Coordenada coordTorre) {
        Torre torre = null;
        String tipoTorre = null;

        switch (nroTorre) {
            case 1:
                torre = new TorreComun(coordTorre);
                tipoTorre = "Comun";
                break;
            case 2:
                torre = new TorreHielo(coordTorre);
                tipoTorre = "Hielo";
                break;
            case 3:
                torre = new TorreFuego(coordTorre);
                tipoTorre = "Fuego";
                break;
        }

        restarPuntosMagia(torre.getCosteTorre());
        mapaNivel.colocarRefTorre(torre);
        listaTorres.add(torre);
        torre.calcularCoordenadasCasillerosAtaque(this);
        System.out.println("Se colocó Torre: " + torre.toString() + " en la posición " + coordTorre.mostrarCoordenada());
    }

    public void colocarBarrera() {
        Coordenada coordBarrera = entradaSalidaUsuario.ingresarYValidarCoordenadas("Barrera", this);
        Casillero casillero = buscarCasilleroPorCoordenada(coordBarrera);
        casillero.agregarBarrera();
        Barrera barrera = casillero.getBarrera();
        restarPuntosMagia(barrera.getPrecioBarrera());
        System.out.println("Se agregó Barrera: " + barrera.toString() + " en el casillero " + casillero.getCoordenadaCasillero().mostrarCoordenada());
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
    public Mapa getMapaNivel() {
        return mapaNivel;
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


    public int getPuntosMagia() {
        return puntosMagia;
    }

    public void aumentarPuntosMagia(int puntosMagia) {
        this.puntosMagia += puntosMagia;
    }

    public void restarPuntosMagia(int puntosMagia) {
        this.puntosMagia -= puntosMagia;
    }

    public ArrayList<Torre> getListaTorres() {
        return listaTorres;
    }

    public Oleada getOleadaNivel() {
        return oleadaNivel;
    }

    public void aumentarNivel() {
        this.nroNivel++;
    }

    public boolean getDerrotado() {
        return derrotado;
    }

    public ArrayList<Casillero> getCasillerosEnemigos() {
        return casillerosEnemigos;
    }
}
