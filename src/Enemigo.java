import java.util.ArrayList;

public abstract class Enemigo implements IAtaque {
    protected int id; // Como lo generamos por cada tipo Enemigo
    protected double vida;
    protected double danio; // cantidad de daño que realiza a barreras o Cerro Gloria
    protected String inmunidad; // Puede ser hielo, fuego o ninguno, esto reducirá el ataque sufrido por la torre correspondiente
    protected int alcanceAtaque; // Define si un enemigo puede atacar mas alla de su propio casillero. (Borrar?)
    protected int velocidadDesplazamiento; // Determina cada cuantas iteraciones avanza un casillero
    protected int contadorMovimientosRestantes;
    protected boolean relentizado = false;

    protected int recompensaEnemigo; // Puntos de magia ganados al eliminar el enemigo
    protected ArrayList<Coordenada> camino = new ArrayList<>(); //lista de posiciones que debe recorrer cada enemigo por mapa

    public void obtenerCaminoNivel(Nivel nivel){
        //camino = nivel.getCaminoEnemigos();
    }


@Override
    public void atacar(Nivel nivelActual, Casillero casillero) {
        // Si el enemigo tiene alcance != 1, debe poder iterar mas alla de su propio casillero para atacar.
        // Si hay barrera, el enemigo resta daño a barrera

        if (casillero.tieneBarrera()){
            casillero.getBarrera().restarVida(this.danio);
            System.out.println(this.toString() + " inflinge daño " + this.danio + " a " + casillero.getBarrera().toString());
        }
        if (casillero.getCerroGloria() != null){
            // Si hay cerro Gloria, el enemigo resta daño a Cerro y desaparece.
            Cerro cerroGloria = casillero.getCerroGloria();
            cerroGloria.restarVida(this.getDanio());
            // Ver de borrar objeto enemigo cuando ataque Cerro.
            System.out.println("El "+this.toString()+" inflinge "+this.getDanio()+" de daño al cerro gloria.");

        }
    }


//    public void entregarPuntosMagia(Juego juego) {
//        // Cuando una torre elimina al enemigo, antes de ser eliminado el objeto, este devuelve al juego puntos de magia, para mejoras y/o compras.
//        juego.aumentarPuntosMagia(recompensaEnemigo);
//    }

    public int getID() {
        return id;
    }

    public int getContadorMovimientosRestantes() {
        return this.contadorMovimientosRestantes;
    }

    public void reiniciarContadorIteraciones(){
        this.contadorMovimientosRestantes = this.velocidadDesplazamiento;
    }

    //Este metodo reduce en 1 unidad el contador que determina despues de cuantas iteracion se debe mover cada enemigo
    // El contador depende de la velocidad de desplazamiento de cada enemigo

    public void reducirContadorIteraciones(){
        this.contadorMovimientosRestantes--;
        if (this.contadorMovimientosRestantes == 0){
            this.relentizado = false;
        }
    }
    public void aumentarContadorIteraciones(){
        this.relentizado = true;
        this.contadorMovimientosRestantes++;
    }

    public double getDanio() {
        return danio;
    }

    public int getRecompensaEnemigo() {
        return recompensaEnemigo;
    }

    public void restarVida(double danio){
        this.vida = this.vida - danio;
    }

    public double getVida() {
        return vida;
    }

    public boolean isRelentizado() {
        return this.relentizado;
    }
}
