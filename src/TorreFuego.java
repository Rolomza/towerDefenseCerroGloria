import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TorreFuego extends Torre{

    private static final AtomicInteger contador = new AtomicInteger(0);
    public TorreFuego(Coordenada coordenadaTorre){
        this.id = contador.incrementAndGet();
        this.danio = 50; // Mata a los elfos de 2 tiros
        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
        this.costeTorre = 1000;
    }

    public void getCoordenadaTorre() {
        this.coordenadaTorre.mostrarCoordenada();
    }

    public void chequearCasillerosAtaque(ArrayList<Casillero> casillerosEnemigos, Nivel nivelActual) {

        for (Casillero casilleroEnemigo : casillerosEnemigos) {

            for (Coordenada coordenadaAtaqueTorre : casillerosAtaque) {
                if (casilleroEnemigo.getCoordenadaCasillero().compararConCoordenada(coordenadaAtaqueTorre)) {
                    if (casilleroEnemigo.tieneEnemigos()) {
                        this.atacar(casilleroEnemigo, nivelActual);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void atacar(Casillero casillero, Nivel nivelActual) {
        //
        listaAtaqueEnemigos = prioridadEnemigo(casillero);

        for (Enemigo enemigoActual : listaAtaqueEnemigos){
            enemigoActual.restarVida(this.danio);
            if (enemigoActual.getVida() <= 0){
                casillero.eliminarEnemigo(enemigoActual);
                nivelActual.aumentarPuntosMagia(enemigoActual.getRecompensaEnemigo());
                casillero.getEnemigosListosParaMoverse().remove(enemigoActual);
                System.out.println("La torre " + this.toString() + " ha asesinado a " + enemigoActual.toString() + " | +" + enemigoActual.getRecompensaEnemigo() + " PtosMagia.");
            } else {
                System.out.println(enemigoActual.toString() + " fue atacado por "+this.toString()+" y ahora tiene "+enemigoActual.getVida()+" de vida");
            }
        }

    }

    public ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo) {
        ArrayList<Enemigo> listaEnemigosPorPrioridad = new ArrayList<>();
        String[] tiposEnemigos = {"Elfo", "Enano", "Humano", "Hobbit"}; // Prioriza por daño de enemigo

        for (String tipo : tiposEnemigos) {
            ArrayList<Enemigo> listaEnemigos = casilleroEnemigo.getEnemigosCasillero().get(tipo);
            if (!listaEnemigos.isEmpty()) {
                for (Enemigo enemigo : listaEnemigos) {
                    listaEnemigosPorPrioridad.add(enemigo);
                    return listaEnemigosPorPrioridad;

                }
            }
        }

        return listaEnemigosPorPrioridad;
    }


    public String toString() {
        return "Tf"+this.id;
    }


}
