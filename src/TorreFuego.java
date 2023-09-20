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
    public TorreFuego(){
        this.danio = 50;
        this.alcanceAtaque = 1;
        this.costeTorre = 1000;
    }


    // Revisar implementacion chequear

    public void chequearCasillerosAtaque(ArrayList<Casillero> casillerosEnemigos, Nivel nivelActual) {

        for (Casillero casilleroEnemigo : casillerosEnemigos) {

            for (Coordenada coordenadaAtaqueTorre : coordCasillerosAtaque) {
                if (casilleroEnemigo.getCoordenadaCasillero().compararConCoordenada(coordenadaAtaqueTorre)) {
                    if (casilleroEnemigo.tieneEnemigos()) {
                        this.atacar(nivelActual, casilleroEnemigo);
                        break;
                    }
                }
            }
        }
    }


    public void atacar(Nivel nivelActual, Casillero casilleroActual) {
        // Torre fuego ataca a todos los enemigos que ve en cada casillero
        for (Casillero casilleroAtaque : this.casillerosAtaque) {
            System.out.println("Estoy revisando para atacar a " + casilleroAtaque.toString());
            listaAtaqueEnemigos = prioridadEnemigo(casilleroAtaque);

            for (Enemigo enemigoActual : listaAtaqueEnemigos){
                enemigoActual.restarVida(this.danio);

                if (enemigoActual.getVida() <= 0){
                    // Si la torre mata al enemigo, sucede lo siguiente:
                    casilleroAtaque.eliminarEnemigo(enemigoActual);
                    nivelActual.aumentarPuntosMagia(enemigoActual.getRecompensaEnemigo());
                    casilleroAtaque.getEnemigosListosParaMoverse().remove(enemigoActual);
                    System.out.println("La torre " + this.toString() + " ha asesinado a " + enemigoActual.toString() + " | +" + enemigoActual.getRecompensaEnemigo() + " Ptos. Magia.");
                } else {
                    // Si la torre no mata al enemigo muestra el siguiente mensaje:
                    System.out.println(enemigoActual.toString() + " fue atacado por "+this.toString()+" y ahora tiene "+enemigoActual.getVida()+" de vida");
                }

            }
        }
    }


    @Override
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
