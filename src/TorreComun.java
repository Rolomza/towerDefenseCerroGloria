public class TorreComun extends Torre{

    public TorreComun(Coordenada coordenadaTorre){

        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
    }

    public void getCoordenadaTorre() {
        this.coordenadaTorre.mostrarCoordenada();
    }




}
