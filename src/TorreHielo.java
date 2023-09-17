import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TorreHielo extends Torre{

    private static final AtomicInteger contador = new AtomicInteger(0);
    public TorreHielo(Coordenada coordenadaTorre){
        this.id = contador.incrementAndGet();
        this.danio = 20;
        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
    }

    public void getCoordenadaTorre() {
        this.coordenadaTorre.mostrarCoordenada();
    }

    @Override
    public void atacar(Casillero casillero) {
        //
        listaAtaqueEnemigos = prioridadEnemigo(casillero);

        for (Enemigo enemigoActual : listaAtaqueEnemigos){
            enemigoActual.restarVida(this.danio);
            if (!enemigoActual.isRelentizado()){
                enemigoActual.aumentarContadorIteraciones();
            }
            if (enemigoActual.getVida() <= 0){
                casillero.eliminarEnemigo(enemigoActual);
                casillero.getEnemigosListosParaMoverse().remove(enemigoActual);
            }

            System.out.println(enemigoActual.toString() + " fue atacado por "+this.toString()+" y ahora tiene "+enemigoActual.getVida()+" de vida");
        }

    }

    public ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo) {
        ArrayList<Enemigo> listaEnemigosPorPrioridad = new ArrayList<>();
        String[] tiposEnemigos = {"Elfo", "Humano", "Hobbit", "Enano"};

        for (String tipo : tiposEnemigos) {
            ArrayList<Enemigo> listaEnemigos = casilleroEnemigo.getEnemigosCasillero().get(tipo);
            if (!listaEnemigos.isEmpty()) {
                for (Enemigo enemigo : listaEnemigos) {
                    listaEnemigosPorPrioridad.add(enemigo);
                    if (listaEnemigosPorPrioridad.size() >= 2) {
                        return listaEnemigosPorPrioridad;
                    }
                }
            }
        }

        return listaEnemigosPorPrioridad;
    }


    public String toString() {
        return "Th"+this.id;
    }


}
