package TowerDefenceCerro.Torres;

import TowerDefenceCerro.Casillero;
import TowerDefenceCerro.Coordenada;
import TowerDefenceCerro.Enemigos.Enemigo;
import TowerDefenceCerro.MomentosJuego.Nivel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TorreHielo extends Torre{

    private static final AtomicInteger contador = new AtomicInteger(0);
    public TorreHielo(Coordenada coordenadaTorre){
        this.id = contador.incrementAndGet();
        this.danio = 15;
        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
        this.costeTorre = 500;
    }

    public TorreHielo(){
        this.danio = 25;
        this.alcanceAtaque = 1;
        this.costeTorre = 500;
    }

    @Override
    public void atacar(Nivel nivelActual, Casillero casilleroActual) {
        // Esta flag sirve para que la torre ataque a 1 casillero solamente donde detecte enemigos.
        int enemigosAtacados = 0;

        for (Casillero casilleroAtaque : this.casillerosAtaque) {

            listaAtaqueEnemigos = prioridadEnemigo(casilleroAtaque);

            for (Enemigo enemigoActual : listaAtaqueEnemigos){
                enemigoActual.restarVida(this.danio);

                // Sobreescribo el metodo de TowerDefenceCerro.Torres.Torre para agregar relentizado y atacar solo 2 por casillero.

                if (!enemigoActual.isRelentizado()){
                    enemigoActual.aumentarContadorIteraciones();
                }


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
                enemigosAtacados++; // Activar la bandera de ataque

            }
            if (enemigosAtacados > 1) {
                break;
            }
        }
    }

    @Override
    public ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo) {
        ArrayList<Enemigo> listaEnemigosPorPrioridad = new ArrayList<>();
        String[] tiposEnemigos = {"TowerDefenceCerro.Enemigos.Elfo", "TowerDefenceCerro.Enemigos.Humano", "TowerDefenceCerro.Enemigos.Hobbit", "TowerDefenceCerro.Enemigos.Enano"}; // Prioriza por velocidad

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
